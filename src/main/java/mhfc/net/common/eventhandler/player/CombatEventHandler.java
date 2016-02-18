package mhfc.net.common.eventhandler.player;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mhfc.net.common.eventhandler.MHFCGuiHandler;
import mhfc.net.common.weapon.iWeaponCluster;
import mhfc.net.common.weapon.melee.PerceptionHelper;
import mhfc.net.common.weapon.melee.iWeaponReach;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;

@SideOnly(Side.CLIENT)
public class CombatEventHandler {
	
	public static final CombatEventHandler instance = new CombatEventHandler();
	
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent e)
	{
		if (!e.player.worldObj.isRemote)
		{
			return;
		}
		if (e.phase == TickEvent.Phase.END)
		{
			if (e.player != null && e.player.swingProgressInt == 1) // Just swung
			{
				ItemStack itemstack = e.player.getCurrentEquippedItem();
				iWeaponReach ieri;
				if (itemstack != null)
				{
					if (itemstack.getItem() instanceof iWeaponReach)
					{
						ieri = (iWeaponReach) itemstack.getItem();
					} else if (itemstack.getItem() instanceof iWeaponCluster && ((iWeaponCluster) itemstack.getItem()).getMeleeComponent() instanceof iWeaponReach)
					{
						ieri = (iWeaponReach) ((iWeaponCluster) itemstack.getItem()).getMeleeComponent();
					} else
					{
						ieri = null;
					}

					if (ieri != null)
					{
						float reach = ieri.getExtendedReach(e.player.worldObj, e.player, itemstack);
						MovingObjectPosition mov = PerceptionHelper.getMouseOver(0, reach);

						if (mov != null && mov.entityHit != null && mov.entityHit != e.player && mov.entityHit.hurtResistantTime == 0)
						{
							FMLClientHandler.instance().getClient().playerController.attackEntity(e.player, mov.entityHit);
						}
					}
				}
			}
		}	}	
	
		
	

}
