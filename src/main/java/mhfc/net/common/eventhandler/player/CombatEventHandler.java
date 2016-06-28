package mhfc.net.common.eventhandler.player;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import mhfc.net.common.util.Attributes;
import mhfc.net.common.weapon.melee.PerceptionHelper;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;

public class CombatEventHandler {

	public static final CombatEventHandler instance = new CombatEventHandler();

	@SubscribeEvent
	public void onPlayerCreation(EntityConstructing e) {
		if (e.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) e.entity;
			player.getAttributeMap().registerAttribute(Attributes.ATTACK_COOLDOWN);
			player.getAttributeMap().registerAttribute(Attributes.RELOAD_TIME);
			player.getAttributeMap().registerAttribute(Attributes.WEAPON_REACH);
		}
	}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent e) {
		if (!e.player.worldObj.isRemote) {
			return;
		}
		if (e.phase != TickEvent.Phase.END) {
			return;
		}

		if (e.player == null || e.player.swingProgressInt != 1) {
			return;
		}

		IAttributeInstance attribute = e.player.getEntityAttribute(Attributes.WEAPON_REACH);
		double reach = attribute.getAttributeValue();
		MovingObjectPosition mov = PerceptionHelper.getMouseOver(0, (float) reach);

		if (mov != null && mov.entityHit != null && mov.entityHit != e.player && mov.entityHit.hurtResistantTime == 0) {
			FMLClientHandler.instance().getClient().playerController.attackEntity(e.player, mov.entityHit);
		}
	}
}
