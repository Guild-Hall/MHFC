package mhfc.net.common.core.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import javax.annotation.Nonnull;

import com.google.gson.JsonElement;
import com.mojang.authlib.GameProfile;

import mhfc.net.common.core.data.KeyToInstanceRegistryData;
import mhfc.net.common.quests.world.QuestFlair;
import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IAreaType;
import mhfc.net.common.world.exploration.ExplorationProperties;
import mhfc.net.common.world.exploration.IExplorationManager;
import mhfc.net.common.world.exploration.MHFCExploration;
import mhfc.net.common.world.exploration.OverworldManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

public class MHFCExplorationRegistry {

	public static final ResourceLocation NAME_OVERWORLD = new ResourceLocation("mhfc:overworld");
	public static final ResourceLocation NAME_MHFC_EXPLORATION = new ResourceLocation("mhfcExploration");

	private static KeyToInstanceRegistryData<ResourceLocation, Class<? extends IExplorationManager>> explorationManagers = new KeyToInstanceRegistryData<>();
	private static Map<ResourceLocation, Function<GameProfile, ? extends IExplorationManager>> factories = new HashMap<>();

	public static class RespawnListener {
		@SubscribeEvent
		public void onInitialSpawn(PlayerLoggedInEvent loggedIn) {
			if (loggedIn.player.world.isRemote) {
				return;
			}
			EntityPlayerMP player = (EntityPlayerMP) loggedIn.player;
			IExplorationManager manager = getExplorationManagerFor(player);
			manager.onPlayerJoined();
			manager.onPlayerAdded();
		}

		@SubscribeEvent
		public void onRespawn(PlayerRespawnEvent spawn) {
			if (spawn.player.world.isRemote) {
				return;
			}
			EntityPlayerMP player = (EntityPlayerMP) spawn.player;
			respawnPlayer(player, null);
		}
	}

	public static void init() {
		RespawnListener listener = new RespawnListener();
		MinecraftForge.EVENT_BUS.register(listener);
		registerPersistantExplorationManager(NAME_OVERWORLD, OverworldManager.class, OverworldManager::new);
		registerPersistantExplorationManager(NAME_MHFC_EXPLORATION, MHFCExploration.class, MHFCExploration::new);
	}

	public static <T extends IExplorationManager> boolean registerPersistantExplorationManager(
			ResourceLocation key,
			Class<? extends T> clazz,
			Function<GameProfile, ? extends T> factory) {
		Objects.requireNonNull(clazz);
		Objects.requireNonNull(factory);
		boolean success = explorationManagers.offerMapping(key, clazz);
		if (success) {
			factories.put(key, factory);
		}
		return success;
	}

	public static Function<GameProfile, ? extends IExplorationManager> getExplorationManagerByName(
			ResourceLocation key) {
		return factories.get(key);
	}

	public static ResourceLocation getExplorationManagerName(IExplorationManager manager) {
		return manager == null ? null : explorationManagers.getKey(manager.getClass());
	}

	public static IExplorationManager bindPlayer(IExplorationManager manager, GameProfile player) {
		Objects.requireNonNull(player);
		return getExplorationProperties(player).replaceManager(manager);
	}

	public static IExplorationManager releasePlayer(GameProfile player) {
		Objects.requireNonNull(player);
		return bindPlayer(new OverworldManager(player), player);
	}

	public static IExplorationManager releasePlayer(EntityPlayer player) {
		return releasePlayer(player.getGameProfile());
	}

	public static CompletionStage<IActiveArea> transferPlayer(GameProfile player, IAreaType area, QuestFlair flair) {
		IExplorationManager manager = getExplorationManagerFor(player);
		CompletionStage<IActiveArea> areaStage = manager.transferPlayerInto(area, flair);
		return areaStage;
	}

	public static CompletionStage<IActiveArea> transferPlayer(EntityPlayer player, IAreaType area, QuestFlair flair) {
		return transferPlayer(player.getGameProfile(), area, flair);
	}

	@Nonnull
	public static IExplorationManager getExplorationManagerFor(GameProfile player) {
		Objects.requireNonNull(player);
		ExplorationProperties explorationProperties = getExplorationProperties(player);

		return setupExplorationProperties(player, explorationProperties);
	}

	@Nonnull
	public static IExplorationManager getExplorationManagerFor(EntityPlayer player) {
		Objects.requireNonNull(player);
		ExplorationProperties explorationProperties = getExplorationProperties(player);

		return setupExplorationProperties(player.getGameProfile(), explorationProperties);
	}

	private static IExplorationManager setupExplorationProperties(
			GameProfile playerProfile,
			ExplorationProperties explorationProperties) {
		IExplorationManager manager = explorationProperties.getManager();
		if (manager == null) {
			manager = new OverworldManager(playerProfile);
			explorationProperties.replaceManager(manager);
		}
		return manager;
	}

	public static ExplorationProperties getExplorationProperties(GameProfile player) {
		return MHFCPlayerPropertiesRegistry.getPlayerProperties(player).getExploration();
	}

	public static ExplorationProperties getExplorationProperties(EntityPlayer player) {
		return MHFCPlayerPropertiesRegistry.getPlayerProperties(player).getExploration();
	}

	public static void respawnPlayer(GameProfile player, JsonElement saveData) {
		getExplorationManagerFor(player).respawn(saveData);
	}

	public static void respawnPlayer(EntityPlayer player, JsonElement saveData) {
		getExplorationManagerFor(player).respawn(saveData);
	}
}
