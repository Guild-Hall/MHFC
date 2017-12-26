package mhfc.net.common.core.registry;

import com.mojang.authlib.GameProfile;

import mhfc.net.common.core.capability.CapabilityPlayerHunterProvider;
import mhfc.net.common.core.data.PlayerProperties;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MHFCPlayerPropertiesRegistry {

	public static final ResourceLocation propertyKey = new ResourceLocation("mhfc:playerdata");

	private static PlayerLoadHandler playerLoadHandler = new PlayerLoadHandler();

	public static class PlayerLoadHandler {
		private boolean isValidPlayerEntity(Entity entity) {
			return entity instanceof EntityPlayer && !((EntityPlayer) entity).world.isRemote;
		}

		@SubscribeEvent
		public void onClonePlayer(PlayerEvent.Clone cloning) {
			if (!isValidPlayerEntity(cloning.getEntityPlayer())) {
				return;
			}
			if (cloning.isWasDeath()) {
				EntityPlayer original = cloning.getOriginal();
				EntityPlayer target = cloning.getEntityPlayer();
				PlayerProperties originalProperties = MHFCPlayerPropertiesRegistry.getPlayerProperties(original);
				PlayerProperties targetProperties = MHFCPlayerPropertiesRegistry.getPlayerProperties(target);
				targetProperties.cloneProperties(originalProperties);
			}
		}

		@SubscribeEvent
		public void onCreatePlayerEntity(AttachCapabilitiesEvent<Entity> constructing) {
			Entity attachTarget = constructing.getObject();
			if (!isValidPlayerEntity(attachTarget)) {
				return;
			}
			EntityPlayer player = (EntityPlayer) attachTarget;
			constructing.addCapability(propertyKey, new CapabilityPlayerHunterProvider(player));
		}
	}

	public static void init() {
		MinecraftForge.EVENT_BUS.register(playerLoadHandler);
		CapabilityPlayerHunterProvider.registerCapabilities(CapabilityManager.INSTANCE);
	}

	public static PlayerProperties getPlayerProperties(GameProfile playerProfile) {
		EntityPlayer player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList()
				.getPlayerByUUID(playerProfile.getId());
		return getPlayerProperties(player);
	}

	public static PlayerProperties getPlayerProperties(EntityPlayer player) {
		return player.getCapability(CapabilityPlayerHunterProvider.HUNTER_CAPABILITY, null);
	}
}
