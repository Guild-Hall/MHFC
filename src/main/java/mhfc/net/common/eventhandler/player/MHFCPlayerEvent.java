package mhfc.net.common.eventhandler.player;

import mhfc.net.common.system.ColorSystem;
import mhfc.net.common.system.UpdateSystem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class MHFCPlayerEvent {
	boolean hasSeen;
	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent e) {
		EntityPlayer player = (EntityPlayer) e.player;
		if(!player.worldObj.isRemote) {
			
			if(UpdateSystem.isUpdateAvailable() == UpdateSystem.newUpdate)
			if(!hasSeen){
				player.addChatMessage(new ChatComponentText(ColorSystem.GOLD + "Hunter " + player.getDisplayName() + " a new version of Monster Hunter Frontier Craft is up check out the facebook page or the mod thread."));
				player.addChatMessage(new ChatComponentText("Make sure you check out our facebook page for updates !!"));
				hasSeen = true;
			}
			
			if(UpdateSystem.isUpdateAvailable() == UpdateSystem.noUpdate){
			if(!hasSeen){
				player.addChatMessage(new ChatComponentText(ColorSystem.GOLD + "Welcome Hunter " + player.getDisplayName() + " you're up to date, have fun hunting !!"));
				player.addChatMessage(new ChatComponentText("Make sure you check out our facebook page for updates !!"));
				hasSeen = true;
			}
			
			if(UpdateSystem.isUpdateAvailable() == UpdateSystem.offline){
			if(!hasSeen){
				player.addChatMessage(new ChatComponentText(ColorSystem.GOLD + "Hunter " + player.getDisplayName() + " you're in offline mode."));
				player.addChatMessage(new ChatComponentText("Make sure you check out our facebook page for updates !!"));
				hasSeen = true;
			}
			}
			}
		}
	}

}
