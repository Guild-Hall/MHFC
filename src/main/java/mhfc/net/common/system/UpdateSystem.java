package mhfc.net.common.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mhfc.net.common.index.ResourceInterface;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class UpdateSystem {

	public static enum UpdateStatus {
		NEWUPDATE,
		NOUPDATE,
		OFFLINE,
		FAILED;
	}

	public static class UpdateInfo {
		public final UpdateStatus status;
		public final String version;

		public UpdateInfo(UpdateStatus status, String version) {
			this.status = Objects.requireNonNull(status);
			this.version = Objects.requireNonNull(version);
		}
	}

	private static final String urlString = "https://raw.githubusercontent.com/Guild-Hall/MHFC/master/build.properties";
	private static Future<UpdateInfo> updateInfo;

	static {
		RunnableFuture<UpdateInfo> infoGetter = new FutureTask<>(() -> pollUpdate());
		new Thread(infoGetter, "MHFC Updater").start();
		updateInfo = infoGetter;
	}

	public static void init() {
		/* Noop, used as static-init */}

	private static UpdateInfo pollUpdate() {
		String current = ResourceInterface.getMetadata().version;
		try (BufferedReader versionFile = new BufferedReader(new InputStreamReader(new URL(urlString).openStream()))) {
			String newVersionStr = versionFile.readLine();
			Matcher match = Pattern.compile("version\\s*=\\s*\\\"(.*?)\\\"").matcher(newVersionStr);
			if (!match.matches()) {
				return new UpdateInfo(UpdateStatus.FAILED, newVersionStr);
			}
			newVersionStr = match.group(1);
			if (!current.equals(newVersionStr)) {
				return new UpdateInfo(UpdateStatus.NEWUPDATE, newVersionStr);
			}
			return new UpdateInfo(UpdateStatus.NOUPDATE, current);
		} catch (IOException e) {
			return new UpdateInfo(UpdateStatus.OFFLINE, current);
		}
	}

	/**
	 * Gets or waits for the UpdateInfo that is available.
	 *
	 * @return infos about possibly available updates.
	 */
	public static UpdateInfo getUpdateInfo() {
		try {
			return updateInfo.get();
		} catch (InterruptedException e) {
			return new UpdateInfo(UpdateStatus.FAILED, "UNKNOWN"); // Not likely
		} catch (ExecutionException e) {
			return new UpdateInfo(UpdateStatus.FAILED, "UNKNOWN"); // impossible
		}
	}

	public static void onServerStarting(FMLServerStartingEvent sse) {
		if (sse.getSide().isServer()) {
			sendUpdateAsync(sse.getServer());
		}
	}

	public static void sendUpdateAsync(final ICommandSender finalConsole) {
		new Thread((Runnable) () -> {
			UpdateInfo info = UpdateSystem.getUpdateInfo(); // Blocks
			notifyOfUpdate(finalConsole, info);
		}, "MHFC Update Notifier").start();
	}

	private static void notifyOfUpdate(ICommandSender console, UpdateInfo info) {
		switch (info.status) {
		case NEWUPDATE:
			console.addChatMessage(
					new TextComponentString(
							ColorSystem.ENUMGOLD + "Hunter " + console.getName() + ", a new version (" + info.version
									+ ") of Monster Hunter Frontier Craft is out!"
									+ " Check out the facebook page or the mod thread."));
			break;
		case NOUPDATE:
			console.addChatMessage(
					new TextComponentString(
							ColorSystem.ENUMGOLD + "Welcome Hunter " + console.getName()
									+ ", you're up to date, have fun hunting !!"));
			break;
		case OFFLINE:
			console.addChatMessage(
					new TextComponentString(
							ColorSystem.ENUMGOLD + "Hunter " + console.getName()
									+ ", unable to check for updates automatically"));
			console.addChatMessage(
					new TextComponentString(
							"Make sure you frequently stop by on our facebook site or Minecraft forum thread!!"));
			break;
		default: // Should not happen?
			console.addChatMessage(
					new TextComponentString(
							ColorSystem.ENUMGOLD
									+ "MHFC: There is no current Update System Atm please wait for the patch.."
									+ " Version Info: " + info.version));
		}
	}
}
