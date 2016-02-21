package mhfc.net.common.core.registry;

import java.util.Objects;
import java.util.function.Consumer;

import javax.annotation.Nonnull;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mhfc.net.MHFCMain;
import mhfc.net.common.core.data.KeyToInstanceRegistryData;
import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IAreaType;
import mhfc.net.common.world.exploration.ExplorationProperties;
import mhfc.net.common.world.exploration.IExplorationManager;
import mhfc.net.common.world.exploration.MHFCExploration;
import mhfc.net.common.world.exploration.OverworldManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

public class MHFCExplorationRegistry {

	public static final String NAME_OVERWORLD = "overworld";
	public static final String NAME_MHFC_EXPLORATION = "mhfcExploration";

	private static KeyToInstanceRegistryData<String, IExplorationManager> explorationManagers = new KeyToInstanceRegistryData<>();

	public static class RespawnListener {
		@SubscribeEvent
		public void onRespawn(LivingSpawnEvent.CheckSpawn spawn) {
			if (spawn.entity.worldObj.isRemote)
				return;
			if (!(spawn.entityLiving instanceof EntityPlayerMP))
				return;
			EntityPlayerMP player = (EntityPlayerMP) spawn.entityLiving;
			// FIXME Quest should register its own exploration handler that respawn correctly
			if (MHFCQuestRegistry.getQuestForPlayer(player) != null)
				return;
			spawn.setResult(Result.ALLOW);
			getExplorationManagerFor(player).respawn(player);
		}
	}

	public static void init() {
		registerExplorationManager(NAME_OVERWORLD, OverworldManager.instance);
		registerExplorationManager(NAME_MHFC_EXPLORATION, MHFCExploration.instance);
	}

	public static boolean registerExplorationManager(String key, IExplorationManager data) {
		return explorationManagers.offerMapping(key, data);
	}

	public static IExplorationManager getExplorationManagerByName(String key) {
		return explorationManagers.getData(key);
	}

	public static String getExplorationManagerName(IExplorationManager exploration) {
		return explorationManagers.getKey(exploration);
	}

	public static boolean bindPlayer(IExplorationManager manager, EntityPlayerMP player) {
		Objects.requireNonNull(manager);
		Objects.requireNonNull(player);
		IExplorationManager current = getExplorationManagerFor(player);
		MHFCMain.logger.debug("Moving player from exploration manager {} to {}", current, manager);
		if (current == manager)
			return false;
		current.onPlayerRemove(player);
		getExplorationProperties(player).setManager(manager);
		manager.onPlayerAdded(player);
		return true;
	}

	public static void releasePlayer(EntityPlayerMP player) {
		Objects.requireNonNull(player);
		bindPlayer(OverworldManager.instance, player);
	}

	public static void transferPlayer(EntityPlayerMP player, IAreaType area, Consumer<IActiveArea> callback) {
		IExplorationManager manager = getExplorationManagerFor(player);
		manager.transferPlayerInto(player, area, callback);
		getExplorationProperties(player).setAreaType(area);
	}

	@Nonnull
	public static IExplorationManager getExplorationManagerFor(EntityPlayerMP player) {
		Objects.requireNonNull(player);
		IExplorationManager manager = getExplorationProperties(player).getManager();
		if (manager == null) {
			MHFCMain.logger.debug("Defaulted exploration manager", player);
			manager = OverworldManager.instance;
			getExplorationProperties(player).setManager(OverworldManager.instance);
		}
		return manager;
	}

	public static ExplorationProperties getExplorationProperties(EntityPlayer player) {
		return MHFCPlayerPropertiesRegistry.getPlayerProperties(player).getExploration();
	}
}
