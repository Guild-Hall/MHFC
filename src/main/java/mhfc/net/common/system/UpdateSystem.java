package mhfc.net.common.system;

import mhfc.net.MHFCMain;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.common.ForgeVersion.CheckResult;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.versioning.ComparableVersion;

public class UpdateSystem {

	public static void init() {
		/* Noop, used as static-init */}

	public static void onServerStarting(FMLServerStartingEvent sse) {
		if (sse.getSide().isServer()) {
			sendUpdateAsync(sse.getServer());
		}
	}

	public static void sendUpdateAsync(final ICommandSender finalConsole) {
		try {
			notifyOfUpdate(finalConsole, ForgeVersion.getResult(MHFCMain.getModContainer()));
		} catch (Exception e) {
			MHFCMain.logger().error("Failed to display update notification", e);
		}
	}

	private static void notifyOfUpdate(ICommandSender console, CheckResult info) {
		console.sendMessage(new TextComponentString(makeUpdateNotification(console, info)));
	}

	private static String makeUpdateNotification(ICommandSender console, CheckResult info) {
		ComparableVersion currentVersion = new ComparableVersion(MHFCMain.getModContainer().getVersion());

		switch (info.status) {
		case OUTDATED:
		case BETA_OUTDATED:
			return ColorSystem.ENUMAQUA + "[MHFC] Hi there " + console.getName() + ", a new version "
					+ ColorSystem.ENUMGREEN + "(" + info.target
					+ ")" + ColorSystem.ENUMAQUA + " of Monster Hunter Frontier Craft is out!"
					+ " Check out the facebook page or the mod thread.";
		case UP_TO_DATE:
		case BETA:
		case AHEAD:
			return ColorSystem.ENUMAQUA + "[MHFC] Hi there " + console.getName()
					+ ", you're up to date, have fun hunting !!";
		case PENDING:
		case FAILED:
			return ColorSystem.ENUMAQUA + "[MHFC] Failed to retrieve update info. Current version: " + currentVersion
					+ ".";
		default: // Should not happen?
			return ColorSystem.ENUMAQUA + "[MHFC] Internal problem while retrieving update info. " + "Current version: "
					+ currentVersion + ". Reason for failure: " + info.status;
		}

	}
}
