package mhfc.net.common.world.exploration;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import com.google.common.collect.Table;
import com.google.gson.JsonElement;
import com.mojang.authlib.GameProfile;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCDimensionRegistry;
import mhfc.net.common.quests.world.QuestFlair;
import mhfc.net.common.util.BiMultiMap;
import mhfc.net.common.world.AreaTeleportation;
import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IAreaType;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;

public abstract class ExplorationAdapter implements IExplorationManager {

	protected EntityPlayerMP player;
	protected GameProfile playerprofile;

	private CompletionStage<IActiveArea> futureArea;

	public ExplorationAdapter(GameProfile managedPlayer) {
		playerprofile = Objects.requireNonNull(managedPlayer);
		futureArea = null;
	}

	protected EntityPlayerMP getPlayer() {
	    return FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(playerprofile.getId());
	}

	@Override
	public void onPlayerAdded() {}

	@Override
	public void onPlayerJoined() {}

	@Override
	public void onPlayerRemove() {
		removeFromCurrentArea();
	}

	@Override
	public final IActiveArea getActiveAreaOf() {
		return getInhabitants().reverse().get(player);
	}

	@Override
	public void respawn(JsonElement saveData) throws IllegalArgumentException {
		// Ignores element
		loadFromSaveData(saveData);
		IActiveArea activeAreaOf = getActiveAreaOf();
		if (activeAreaOf == null) {
			respawnWithoutInstance();
		} else {
			respawnInInstance(activeAreaOf);
		}
	}

	@Override
	public CompletionStage<IActiveArea> transferPlayerInto(IAreaType type, QuestFlair flair) {
		if (futureArea != null) {
			CompletionStage<IActiveArea> waitingFor = futureArea;
			waitingFor.toCompletableFuture().cancel(true);
			futureArea = null;
		}

		return transferInto(type, flair);
	}

	// ==== abstract methods for sub classes to override
	protected abstract void loadFromSaveData(JsonElement saveData);

	/**
	 * Returns the instances managed by this adapter. instances may be shared with another manager. returned table must
	 * be mutable.
	 *
	 * @return
	 */
	protected abstract Table<IAreaType, QuestFlair, Collection<IActiveArea>> getManagedInstances();

	/**
	 * Returns the inhabitants managed by this adapter. inhabitants may be shared with another manager. returned map
	 * must be mutable.
	 *
	 * @return
	 */
	protected abstract BiMultiMap<IActiveArea, GameProfile> getInhabitants();

	protected abstract void respawnWithoutInstance();

	protected abstract void respawnInInstance(IActiveArea instance);

	// ==== implementation

	protected Set<GameProfile> getInhabitants(IActiveArea activeArea) {
		return getInhabitants().get(activeArea);
	}

	protected Collection<IActiveArea> getAreasOfType(IAreaType type, QuestFlair flair) {
		return getManagedInstances().row(type).computeIfAbsent(flair, p -> new HashSet<>());
	}

	protected boolean canTransferIntoArea(IActiveArea area) {
		return true;
	}

	protected CompletionStage<IActiveArea> transferInto(IAreaType type, QuestFlair flair) {
		Optional<IActiveArea> activeAreaOption = getAreasOfType(type, flair).stream().filter(this::canTransferIntoArea)
				.findFirst();
		if (activeAreaOption.isPresent()) {
			MHFCMain.logger().debug("Transfering player into existing quest area instance");
			IActiveArea area = activeAreaOption.get();
			transferIntoExistingInstance(area);
			return CompletableFuture.completedFuture(area);
		}
		MHFCMain.logger().debug("Transfering player into new quest area instance");
		return transferIntoNewInstance(type, flair);
	}

	protected CompletionStage<IActiveArea> transferIntoNewInstance(IAreaType type, QuestFlair flair) {
		player.sendMessage(new TextComponentString("Teleporting to instance when the area is ready"));
		Objects.requireNonNull(type);
		Objects.requireNonNull(flair);
		CompletionStage<IActiveArea> unusedInstance = MHFCDimensionRegistry.getUnusedInstance(type, flair);
		futureArea = unusedInstance.whenComplete((area, ex) -> {
			try {
				if (ex != null) {
					return;
				}
				assert area != null;
				transferIntoExistingInstance(area);
			} finally {
				futureArea = null;
			}
		});
		return futureArea;
	}

	protected void transferIntoExistingInstance(IActiveArea area) {
		Objects.requireNonNull(area);
		IActiveArea oldArea = getActiveAreaOf();

		if (!Objects.equals(area, oldArea)) {
			removeFromCurrentArea();

			Set<this.playerprofile> inhabitantSet = getInhabitants(area);
			if (inhabitantSet.isEmpty()) {
				addInstance(area);
			}
			inhabitantSet.add(this.getPlayer());

			onPlayerMovedFrom(oldArea, area);
		}
		AreaTeleportation.movePlayerToArea(player, area.getArea());
	}

	protected void onPlayerMovedFrom(IActiveArea oldArea, IActiveArea currentArea) {
		CompletionStage<IActiveArea> stagedFuture = futureArea;
		if (stagedFuture != null) {
			stagedFuture.toCompletableFuture().cancel(true);
		}
	}

	protected void addInstance(IActiveArea activeArea) {
		Objects.requireNonNull(activeArea);
		MHFCMain.logger().debug(
				"Adding active area instance {} of type {} to exploration manager",
				activeArea,
				activeArea.getType().getUnlocalizedName());
		activeArea.engage();
		getAreasOfType(activeArea.getType(), activeArea.getFlair()).add(activeArea);
	}

	protected void removeInstance(IActiveArea activeArea) {
		Objects.requireNonNull(activeArea);
		IAreaType areaType = activeArea.getType();
		QuestFlair areaFlair = activeArea.getFlair();

		MHFCMain.logger()
				.debug("Removing active area instance {} of type {} from exploration manager", activeArea, areaType);
		Collection<IActiveArea> areasForType = getAreasOfType(areaType, areaFlair);
		boolean removed = areasForType.remove(activeArea);
		assert removed : "Expected area to remove to be added";

		activeArea.close();
	}

	/**
	 * Removes the player from the current area. If instead you want to move the player to another area, use
	 * {@link #transferIntoExistingInstance(IActiveArea)} instead.
	 */
	protected void removeFromCurrentArea() {
		IActiveArea currentInstance = getActiveAreaOf();
		if (currentInstance == null) {
			return;
		}
		Set<EntityPlayerMP> inhabitants = getInhabitants().get(currentInstance);
		boolean removed = inhabitants.remove(player);
		assert removed;
		if (inhabitants.isEmpty()) {
			removeInstance(currentInstance);
		}
	}

}
