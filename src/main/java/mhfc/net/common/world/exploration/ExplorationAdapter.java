package mhfc.net.common.world.exploration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCDimensionRegistry;
import mhfc.net.common.core.registry.MHFCExplorationRegistry;
import mhfc.net.common.quests.world.QuestFlair;
import mhfc.net.common.world.AreaTeleportation;
import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IAreaType;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;

public abstract class ExplorationAdapter implements IExplorationManager {

	protected final EntityPlayerMP player;
	private IAreaType targetArea;
	private Optional<IActiveArea> area;
	private CompletionStage<IActiveArea> futureArea;

	public ExplorationAdapter(EntityPlayerMP managedPlayer) {
		player = Objects.requireNonNull(managedPlayer);
		targetArea = null;
		area = null;
		futureArea = null;
	}

	/**
	 * Returns the instances managed by this adapter. instances may be shared with another manager. returned map must be
	 * mutable.
	 *
	 * @return
	 */
	protected abstract Map<IAreaType, List<IActiveArea>> getManagedInstances();

	/**
	 * Returns the inhabitants managed by this adapter. inhabitants may be shared with another manager. returned map
	 * must be mutable.
	 *
	 * @return
	 */
	protected abstract Map<IActiveArea, Set<EntityPlayerMP>> getInhabitants();

	protected Set<EntityPlayerMP> getInhabitants(IActiveArea activeArea) {
		return getInhabitants().putIfAbsent(activeArea, new HashSet<>());
	}

	protected List<IActiveArea> getAreasOfType(IAreaType type) {
		return getManagedInstances().putIfAbsent(type, new ArrayList<>());
	}

	protected CompletionStage<IActiveArea> transferIntoInstance(IAreaType type, QuestFlair flair) {
		Optional<IActiveArea> activeAreaOption = getAreasOfType(type).stream().findFirst();
		if (activeAreaOption.isPresent()) {
			MHFCMain.logger().debug("Transfering player into existing quest area instance");
			IActiveArea area = activeAreaOption.get();
			transferIntoInstance(area, flair);
			return CompletableFuture.completedFuture(area);
		} else {
			MHFCMain.logger().debug("Transfering player into new quest area instance");
			return transferIntoNewInstance(type, flair);
		}
	}

	@Override
	public CompletionStage<IActiveArea> transferPlayerInto(IAreaType type, QuestFlair flair) {
		targetArea = type;
		if (futureArea != null) {
			return transferTeleportingPlayer(type, flair);
		} else {
			return transferIntoInstance(type, flair);
		}
	}

	protected CompletionStage<IActiveArea> transferTeleportingPlayer(IAreaType type, QuestFlair flair) {
		Objects.requireNonNull(player);
		CompletionStage<IActiveArea> waitingFor = futureArea;
		waitingFor.toCompletableFuture().cancel(true);
		futureArea = null;
		return transferPlayerInto(type, flair);
	}

	protected CompletionStage<IActiveArea> transferIntoNewInstance(IAreaType type, QuestFlair flair) {
		player.sendMessage(new TextComponentString("Teleporting to instance when the area is ready"));
		Objects.requireNonNull(player);
		Objects.requireNonNull(type);
		CompletionStage<IActiveArea> unusedInstance = MHFCDimensionRegistry.getUnusedInstance(type, flair);
		futureArea = unusedInstance;
		unusedInstance.whenComplete((area, ex) -> {
			try {
				if (area != null) {
					addInstance(area);
					transferIntoInstance(area, flair);
				} else {
					MHFCMain.logger().debug("Canceled teleport to area due to cancellation of area");
				}
			} catch (Exception exception) {
				MHFCMain.logger()
						.error("Error during transfer into {}, releasing player from exploration manager", area);
				MHFCExplorationRegistry.releasePlayer(player);
				if (area != null) {
					removeInstance(area);
					area = null;
				}
			} finally {
				futureArea = null;
			}
		});
		return unusedInstance;
	}

	protected void removePlayerFromInstance() {
		IActiveArea currentInstance = getActiveAreaOf();
		Set<EntityPlayerMP> inhabitantSet = getInhabitants(currentInstance);
		inhabitantSet.remove(player);
		CompletionStage<IActiveArea> stagedFuture = futureArea;
		if (stagedFuture != null) {
			stagedFuture.toCompletableFuture().cancel(true);
		}
		if (currentInstance == null) {
			return;
		}
		if (inhabitantSet.isEmpty()) {
			removeInstance(currentInstance);
		}
	}

	protected void transferIntoInstance(IActiveArea area, QuestFlair flair) {
		Objects.requireNonNull(area);
		removePlayerFromInstance();
		this.area = Optional.of(area);
		Set<EntityPlayerMP> inhabitantSet = getInhabitants(area);
		inhabitantSet.add(player);
		AreaTeleportation.movePlayerToArea(player, area.getArea());
	}

	protected void addInstance(IActiveArea activeArea) {
		Objects.requireNonNull(activeArea);
		MHFCMain.logger().debug(
				"Adding active area instance {} of type {} to exploration manager",
				activeArea,
				activeArea.getType().getUnlocalizedName());
		getInhabitants().put(activeArea, new HashSet<>());
		getAreasOfType(activeArea.getType()).add(activeArea);
	}

	protected void removeInstance(IActiveArea activeArea) {
		Objects.requireNonNull(activeArea);
		MHFCMain.logger().debug(
				"Removing active area instance {} of type {} from exploration manager",
				activeArea,
				activeArea.getType());
		getManagedInstances().remove(activeArea);
		getAreasOfType(activeArea.getType()).remove(activeArea);
		activeArea.close();
	}

	@Override
	public IActiveArea getActiveAreaOf() {
		return area == null ? null : area.orElse(null);
	}

	@Override
	public IAreaType getTargetAreaOf() {
		return targetArea;
	}

	protected abstract void respawnWithoutInstance();

	protected abstract void respawnInInstance(IActiveArea instance);

	@Override
	public void respawn() throws IllegalArgumentException {
		if (area != null) {
			transferPlayerInto(initialAreaType(), initialFlair());
			return;
		}
		IActiveArea activeAreaOf = getActiveAreaOf();
		if (activeAreaOf == null) {
			respawnWithoutInstance();
		} else {
			respawnInInstance(activeAreaOf);
		}
	}

	protected abstract IAreaType initialAreaType();

	protected abstract QuestFlair initialFlair();

	@Override
	public void onPlayerRemove() {
		removePlayerFromInstance();
	}

	@Override
	public void onPlayerAdded() {}

	@Override
	public void onPlayerJoined() {}

}
