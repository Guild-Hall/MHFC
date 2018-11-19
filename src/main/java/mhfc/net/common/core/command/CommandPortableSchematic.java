package mhfc.net.common.core.command;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.logging.log4j.Level;

import com.sk89q.worldedit.LocalConfiguration;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.entity.Player;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardWriter;
import com.sk89q.worldedit.forge.ForgeWorldEdit;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.transform.Transform;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.util.io.Closer;

import mhfc.net.MHFCMain;
import mhfc.net.common.system.ColorSystem;
import mhfc.net.common.worldedit.ClipboardFormats;
import mhfc.net.common.worldedit.FlattenedClipboardTransform;
import mhfc.net.common.worldedit.IClipboardFormat;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class CommandPortableSchematic extends CommandBase {

	private final WorldEdit worldEdit;

	public CommandPortableSchematic(WorldEdit world) {
		checkNotNull(world);
		worldEdit = world;
	}

	@Override
	public String getName() {
		return "mhfc.save";
	}

	@Override
	public String getUsage(ICommandSender p_71518_1_) {
		return "/" + getName() + "[<format>] <filename>";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length != 1) {
			throw new CommandException("Wrong arg count");
		}
		if (!(sender instanceof EntityPlayerMP)) {
			throw new CommandException("Command user must be a player");
		}
		final EntityPlayerMP player = (EntityPlayerMP) sender;
		final ForgeWorldEdit forgeWorldEdit = ForgeWorldEdit.inst;
		try {
			saveWorld(forgeWorldEdit.wrap(player), forgeWorldEdit.getSession(player), args[0]);
		} catch (final WorldEditException e) {
			throw new CommandException("Error while executing store", e);
		}
	}

	protected void saveWorld(Player player, LocalSession session, String filename)
			throws CommandException,
			WorldEditException {
		final LocalConfiguration config = worldEdit.getConfiguration();

		final File dir = worldEdit.getWorkingDirectoryFile(config.saveDir);
		final File f = worldEdit.getSafeSaveFile(player, dir, filename, "schematic", "schematic");

		final IClipboardFormat format = ClipboardFormats.EXTENDED_FORGE_SCHEMATIC;
		final ClipboardHolder holder = session.getClipboard();
		final Clipboard clipboard = holder.getClipboard();
		final Transform transform = holder.getTransform();
		Clipboard target;

		// If we have a transform, bake it into the copy
		if (!transform.isIdentity()) {
			final FlattenedClipboardTransform result = FlattenedClipboardTransform
					.transform(clipboard, transform, holder.getWorldData());
			target = new BlockArrayClipboard(result.getTransformedRegion());
			target.setOrigin(clipboard.getOrigin());
			Operations.completeLegacy(result.copyTo(target));
		} else {
			target = clipboard;
		}

		try (Closer closer = Closer.create()) {
			// Create parent directories
			final File parent = f.getParentFile();
			if (parent != null && !parent.exists()) {
				if (!parent.mkdirs()) {
					throw new CommandException("Could not create folder for schematics!");
				}
			}

			final FileOutputStream fos = closer.register(new FileOutputStream(f));
			final BufferedOutputStream bos = closer.register(new BufferedOutputStream(fos));
			final ClipboardWriter writer = closer.register(format.getWriter(bos));
			writer.write(target, holder.getWorldData());
			MHFCMain.logger().info(player.getName() + " saved " + f.getCanonicalPath());
			player.print(filename + ColorSystem.dark_green + " saved as MHFC schematic compatible format.");
		} catch (final IOException e) {
			player.printError("Schematic could not written: " + e.getMessage());
			MHFCMain.logger().log(Level.WARN, "Failed to write a saved clipboard", e);
		}
	}

}
