package mhfc.net.common.eventhandler.player;

import mhfc.net.common.util.Attributes;
import mhfc.net.common.weapon.melee.PerceptionHelper;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class CombatEventHandler {

	public static final CombatEventHandler instance = new CombatEventHandler();

	@SubscribeEvent
	public void onPlayerCreation(EntityConstructing e) {
		if (e.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) e.getEntity();
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
		RayTraceResult mov = PerceptionHelper.getMouseOver(0, (float) reach);

		if (mov != null && mov.entityHit != null && mov.entityHit != e.player && mov.entityHit.hurtResistantTime == 0) {
			FMLClientHandler.instance().getClient().playerController.attackEntity(e.player, mov.entityHit);
		}
	}
}
