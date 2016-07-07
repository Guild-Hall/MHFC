package mhfc.net.common.world.exploration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletionStage;
import java.util.function.Consumer;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCExplorationRegistry;
import mhfc.net.common.quests.world.GlobalAreaManager;
import mhfc.net.common.quests.world.QuestFlair;
import mhfc.net.common.world.AreaTeleportation;
import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IAreaType;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;

public abstract class ExplorationAdapter implements IExplorationManager {

	protected Set<EntityPlayerMP> playerSet;
	protected Map<EntityPlayerMP, IActiveArea> playerToArea;
	private Map<IAreaType, List<IActiveArea>> areaInstances;
	private Map<IActiveArea, Set<EntityPlayerMP>> inhabitants;
	protected Map<EntityPlayerMP, CompletionStage<IActiveArea>> waitingOnTeleport;

	public ExplorationAdapter() {
		playerSet = new HashSet<>();
		playerToArea = new HashMap<>();
		areaInstances = new HashMap<>();
		inhabitants = new IdentityHashMap<>();
		waitingOnTeleport = new HashMap<>();
	}

	protected abstract QuestFlair getFlairFor(IAreaType type);

	protected Set<EntityPlayerMP> getInhabitants(IActiveArea activeArea) {
		inhabitants.putIfAbsent(activeArea, new HashSet<>());
		return inhabitants.get(activeArea);
	}

	protected List<IActiveArea> getAreasOfType(IAreaType type) {
		return areaInstances.getOrDefault(type, new ArrayList<>());
	}

	protected abstract void transferIntoInstance(EntityPlayerMP player, IAreaType type, Consumer<IActiveArea> callback);

	@Override
	public void transferPlayerInto(EntityPlayerMP player, IAreaType type, Consumer<IActiveArea> callback) {
		if (waitingOnTeleport.containsKey(player)) {
			playerAlreadyTeleporting(player, type, callback);
		} else {
			transferIntoInstance(player, type, callback);
		}
	}

	protected void playerAlreadyTeleporting(EntityPlayerMP player, IAreaType type, Consumer<IActiveArea> callback) {
		Objects.requireNonNull(player);
		CompletionStage<IActiveArea> waitingFor = waitingOnTeleport.get(player);
		waitingFor.toCompletableFuture().cancel(true);
		waitingOnTeleport.remove(player);
		transferPlayerInto(player, type, callback);
	}

	protected void transferIntoNewInstance(EntityPlayerMP player, IAreaType type, Consumer<IActiveArea> callback) {
		player.addChatMessage(new ChatComponentText("Teleporting to instance when the area is ready"));
		Objects.requireNonNull(player);
		Objects.requireNonNull(type);
		CompletionStage<IActiveArea> unusedInstance = GlobalAreaManager.getInstance()
				.getUnusedInstance(type, getFlairFor(type));
		waitingOnTeleport.put(player, unusedInstance);
		unusedInstance.handle((area, ex) -> {
			try {
				if (area != null) {
					addInstance(area);
					transferIntoInstance(player, area);
				} else {
					MHFCMain.logger().debug("Canceled teleport to area due to cancellation of area");
				}
			} catch (Exception exception) {
				MHFCMain.logger().error("Error during transfer into {}, releasing player from exploration manager", area);
				MHFCExplorationRegistry.releasePlayer(player);
				if (area != null) {
					removeInstance(area);
					area = null;
				}
			} finally {
				waitingOnTeleport.remove(player);
				callback.accept(area);
			}
			return area;
		});
	}

	protected void removePlayerFromInstance(EntityPlayerMP player) {
		IActiveArea currentInstance = getActiveAreaOf(player);
		Set<EntityPlayerMP> inhabitantSet = getInhabitants(currentInstance);
		inhabitantSet.remove(player);
		CompletionStage<IActiveArea> stagedFuture = waitingOnTeleport.get(player);
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

	protected void transferIntoInstance(EntityPlayerMP player, IActiveArea area) {
		Objects.requireNonNull(player);
		Objects.requireNonNull(area);
		removePlayerFromInstance(player);
		playerToArea.put(player, area);
		Set<EntityPlayerMP> inhabitantSet = getInhabitants(area);
		inhabitantSet.add(player);
		AreaTeleportation.movePlayerToArea(player, area.getArea());
	}

	protected void addInstance(IActiveArea activeArea) {
		Objects.requireNonNull(activeArea);
		MHFCMain.logger().debug(
				"Adding active area instance {} of type {} to exploration manager",
				activeArea,
				activeArea.getType());
		inhabitants.put(activeArea, new HashSet<>());
		getAreasOfType(activeArea.getType()).add(activeArea);
	}

	protected void removeInstance(IActiveArea activeArea) {
		Objects.requireNonNull(activeArea);
		MHFCMain.logger().debug(
				"Removing active area instance {} of type {} from exploration manager",
				activeArea,
				activeArea.getType());
		inhabitants.remove(activeArea);
		getAreasOfType(activeArea.getType()).remove(activeArea);
		activeArea.close();
	}

	@Override
	public IActiveArea getActiveAreaOf(EntityPlayerMP player) {
		Objects.requireNonNull(player);
		return playerToArea.get(player);
	}

	protected abstract void respawnWithoutInstance(EntityPlayerMP player);

	protected abstract void respawnInInstance(EntityPlayerMP player, IActiveArea instance);

	protected void throwOnIllegalPlayer(EntityPlayerMP player) throws IllegalArgumentException {
		Objects.requireNonNull(player);
		if (!playerSet.contains(player)) {
			throw new IllegalArgumentException("Player is not managed by exploration manager " + this.toString());
		}
	}

	@Override
	public void respawn(EntityPlayerMP player) throws IllegalArgumentException {
		Objects.requireNonNull(player);
		throwOnIllegalPlayer(player);
		if (!playerToArea.containsKey(player)) {
			transferPlayerInto(player, initialAreaType(player), (t) -> {});
			return;
		}
		IActiveArea activeAreaOf = getActiveAreaOf(player);
		if (activeAreaOf == null) {
			respawnWithoutInstance(player);
		} else {
			respawnInInstance(player, activeAreaOf);
		}
	}

	protected abstract IAreaType initialAreaType(EntityPlayerMP player);

	@Override
	public void onPlayerRemove(EntityPlayerMP player) {
		Objects.requireNonNull(player);
		playerSet.remove(player);
		removePlayerFromInstance(player);
	}

	@Override
	public void onPlayerAdded(EntityPlayerMP player) {
		Objects.requireNonNull(player);
		playerSet.add(player);
	}

	@Override
	public void initialAddPlayer(EntityPlayerMP player) throws IllegalArgumentException {
		throwOnIllegalPlayer(player);
	}

}
