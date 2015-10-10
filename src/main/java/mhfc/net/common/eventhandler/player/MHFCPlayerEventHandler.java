package mhfc.net.common.eventhandler.player;

import mhfc.net.common.system.ColorSystem;
import mhfc.net.common.system.UpdateSystem;
import mhfc.net.common.system.UpdateSystem.UpdateInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class MHFCPlayerEventHandler {
	public static final MHFCPlayerEventHandler instance = new MHFCPlayerEventHandler();

	@SuppressWarnings("static-method")
	// Event Handlers can't be static
	@SubscribeEvent
	private void onServerStart(FMLServerStartedEvent sse) {
		ICommandSender console = null;
		if (sse.getSide().isClient()) {
			console = Minecraft.getMinecraft().thePlayer;
		} else {
			console = MinecraftServer.getServer();
		}
		// console.addChatMessage(null);
		final ICommandSender finalConsole = console;
		new Thread(new Runnable() {
			@Override
			public void run() {
				UpdateInfo info = UpdateSystem.getUpdateInfo(); // Blocks
				notifyOfUpdate(finalConsole, info);
			}
		}).start();
	}

	private static void notifyOfUpdate(ICommandSender console, UpdateInfo info) {
		switch (info.status) {
			case NEWUPDATE :
				console.addChatMessage(new ChatComponentText(
						ColorSystem.ENUMGOLD
								+ "Hunter "
								+ console.getCommandSenderName()
								+ ", a new version ("
								+ info.version
								+ ") of Monster Hunter Frontier Craft is out!"
								+ " Check out the facebook page or the mod thread."));
				break;
			case NOUPDATE :
				console.addChatMessage(new ChatComponentText(
						ColorSystem.ENUMGOLD + "Welcome Hunter "
								+ console.getCommandSenderName()
								+ ", you're up to date, have fun hunting !!"));
				break;
			case OFFLINE :
				console.addChatMessage(new ChatComponentText(
						ColorSystem.ENUMGOLD + "Hunter "
								+ console.getCommandSenderName()
								+ ", unable to check for updates automatically"));
				console.addChatMessage(new ChatComponentText(
						"Make sure you frequently stop by on our facebook site or Minecraft forum thread!!"));
				break;
			default : // Should not happen?
				console.addChatMessage(new ChatComponentText(
						ColorSystem.ENUMRED
								+ "MHFC: Unknown UpdateStatus, pls report with a logfile."
								+ " Version info: " + info.version));
		}
	}
}
