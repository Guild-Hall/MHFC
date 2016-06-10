package mhfc.net.common.eventhandler.player;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import mhfc.net.common.weapon.IWeaponCluster;
import mhfc.net.common.weapon.melee.IWeaponReach;
import mhfc.net.common.weapon.melee.PerceptionHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;

public class CombatEventHandler {

	public static final CombatEventHandler instance = new CombatEventHandler();

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
		ItemStack itemstack = e.player.getCurrentEquippedItem();
		IWeaponReach ieri = getWeaponReach(itemstack);
		if (ieri == null) {
			return;
		}
		float reach = ieri.getExtendedReach(e.player.worldObj, e.player, itemstack);
		MovingObjectPosition mov = PerceptionHelper.getMouseOver(0, reach);

		if (mov != null && mov.entityHit != null && mov.entityHit != e.player && mov.entityHit.hurtResistantTime == 0) {
			FMLClientHandler.instance().getClient().playerController.attackEntity(e.player, mov.entityHit);
		}
	}

	private static IWeaponReach getWeaponReach(ItemStack itemstack) {
		if (itemstack == null) {
			return null;
		}
		if (itemstack.getItem() instanceof IWeaponReach) {
			return (IWeaponReach) itemstack.getItem();
		}
		if (itemstack.getItem() instanceof IWeaponCluster
				&& ((IWeaponCluster) itemstack.getItem()).getMeleeComponent() instanceof IWeaponReach) {
			return (IWeaponReach) ((IWeaponCluster) itemstack.getItem()).getMeleeComponent();
		}
		return null;
	}

}
