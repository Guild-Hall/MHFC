package mhfc.net.common.core.registry;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mhfc.net.common.core.data.PlayerProperties;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class MHFCPlayerPropertiesRegistry {

	public static final String propertyKey = "mhfcData";

	private static PlayerLoadHandler playerLoadHandler = new PlayerLoadHandler();

	public static class PlayerLoadHandler {
		private boolean isValidPlayerEntity(Entity entity) {
			return entity instanceof EntityPlayer && !entity.worldObj.isRemote;
		}

		@SubscribeEvent
		public void onClonePlayer(PlayerEvent.Clone cloning) {
			if (!isValidPlayerEntity(cloning.entityPlayer))
				return;
			if (cloning.wasDeath) {
				EntityPlayer original = cloning.original;
				EntityPlayer target = cloning.entityPlayer;
				PlayerProperties originalProperties = MHFCPlayerPropertiesRegistry.getPlayerProperties(original);
				PlayerProperties targetProperties = MHFCPlayerPropertiesRegistry.getPlayerProperties(target);
				targetProperties.cloneProperties(originalProperties);
			}
		}

		@SubscribeEvent
		public void onCreatePlayerEntity(EntityConstructing constructing) {
			if (!isValidPlayerEntity(constructing.entity))
				return;
			EntityPlayer player = (EntityPlayer) constructing.entity;
			player.registerExtendedProperties(propertyKey, new PlayerProperties());
		}
	}

	public static void init() {
		MinecraftForge.EVENT_BUS.register(playerLoadHandler);
	}

	public static PlayerProperties getPlayerProperties(EntityPlayer player) {
		return (PlayerProperties) player.getExtendedProperties(propertyKey);
	}

}
