package mhfc.net.common.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
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
		public final String reason;

		public UpdateInfo(UpdateStatus offline, String current, Throwable e) {
			this(offline, current, e == null ? null : e.getMessage());
		}

		public UpdateInfo(UpdateStatus status, String version, String reason) {
			this.status = Objects.requireNonNull(status);
			this.version = Objects.requireNonNull(version);
			this.reason = reason == null ? "Unknown reason" : reason;
		}
	}

	private static final String urlString = "https://raw.githubusercontent.com/Guild-Hall/MHFC/master/build.properties";
	private static CompletionStage<UpdateInfo> updateInfo;

	static {
		CompletableFuture<UpdateInfo> infoGetter = new CompletableFuture<>();
		new Thread(() -> infoGetter.complete(pollUpdate()), "MHFC Updater").start();
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
				return new UpdateInfo(UpdateStatus.FAILED, newVersionStr, "File doesn't match expected pattern");
			}
			newVersionStr = match.group(1);
			if (!current.equals(newVersionStr)) {
				return new UpdateInfo(UpdateStatus.NEWUPDATE, newVersionStr, "Update available");
			}
			return new UpdateInfo(UpdateStatus.NOUPDATE, current, "Newest version same as ours");
		} catch (IOException e) {
			return new UpdateInfo(UpdateStatus.OFFLINE, current, e);
		}
	}

	public static void onServerStarting(FMLServerStartingEvent sse) {
		if (sse.getSide().isServer()) {
			sendUpdateAsync(sse.getServer());
		}
	}

	public static void sendUpdateAsync(final ICommandSender finalConsole) {
		updateInfo.whenComplete((info, ex) -> {
			if (info == null) {
				info = new UpdateInfo(UpdateStatus.FAILED, "UNKNOWN", ex);
			}
			notifyOfUpdate(finalConsole, info);
		});
	}

	private static void notifyOfUpdate(ICommandSender console, UpdateInfo info) {
		console.addChatMessage(new TextComponentString(makeUpdateNotification(console, info)));
	}

	private static String makeUpdateNotification(ICommandSender console, UpdateInfo info) {
		switch (info.status) {
		case NEWUPDATE:
			return ColorSystem.ENUMGOLD + "[MHFC] Hi there " + console.getName() + ", a new version (" + info.version
					+ ") of Monster Hunter Frontier Craft is out!" + " Check out the facebook page or the mod thread.";
		case NOUPDATE:
			return ColorSystem.ENUMGOLD + "[MHFC] Hi there " + console.getName()
					+ ", you're up to date, have fun hunting !!";
		case OFFLINE:
			return ColorSystem.ENUMGOLD + "[MHFC] Hi there " + console.getName()
					+ ", unable to check for updates automatically. "
					+ "Make sure you frequently stop by on our facebook site or Minecraft forum thread!!";
		case FAILED:
			return ColorSystem.ENUMGOLD + "[MHFC] Failed to retrieve update info. Current version: " + info.version
					+ ". Reason for failure: " + info.reason;
		default: // Should not happen?
			return ColorSystem.ENUMGOLD + "[MHFC] Internal problem while retrieving update info. "
					+ "Current Version Info: " + info.version + ". Reason for failure: " + info.reason;
		}

	}
}
