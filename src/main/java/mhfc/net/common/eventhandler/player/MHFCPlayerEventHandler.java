package mhfc.net.common.eventhandler.player;

import mhfc.net.common.system.ColorSystem;
import mhfc.net.common.system.UpdateSystem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class MHFCPlayerEventHandler {
	public static final MHFCPlayerEventHandler instance = new MHFCPlayerEventHandler();

	private boolean hasSeen = false;
	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent e) {
		EntityPlayer player = e.player;
		if (player.worldObj.isRemote || hasSeen)
			return;

		if (UpdateSystem.isUpdateAvailable() == UpdateSystem.newUpdate ) {
			player.addChatMessage(new ChatComponentText(
					ColorSystem.ENUMGOLD
							+ "Hunter "
							+ player.getDisplayName()
							+ ", a new version of Monster Hunter Frontier Craft is out!"
							+ " Check out the facebook page or the mod thread."));
		}

		if (UpdateSystem.isUpdateAvailable() == UpdateSystem.noUpdate ) {
			player.addChatMessage(new ChatComponentText(ColorSystem.ENUMGOLD
					+ "Welcome Hunter " + player.getDisplayName()
					+ ", you're up to date, have fun hunting !!"));
		}

		if (UpdateSystem.isUpdateAvailable() == UpdateSystem.offline) {
			player.addChatMessage(new ChatComponentText(ColorSystem.ENUMGOLD
					+ "Hunter " + player.getDisplayName()
					+ ", you're in offline mode."));
			player.addChatMessage(new ChatComponentText(
					"Make sure you frequently check for updates on our facebook site or Minecraft forum thread!!"));
			
		}
		hasSeen = true;
	}

}
