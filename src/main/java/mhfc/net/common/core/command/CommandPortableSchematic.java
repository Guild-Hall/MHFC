package mhfc.net.common.core.command;

import com.sk89q.worldedit.LocalConfiguration;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.entity.Player;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.util.command.parametric.Optional;

import mhfc.net.common.worldedit.PortableSchematicReader;
import mhfc.net.common.worldedit.PortableSchematicWriter;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;

public class CommandPortableSchematic extends CommandBase {
	
	private final WorldEdit worldEd;
	
	public CommandPortableSchematic(WorldEdit world) {
		checkNotNull(world);
		this.worldEd = world;
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
		
	}
	
	
	protected void saveWorld(Player player, LocalSession session,  @Optional("schematic") String formatName, String filename)throws CommandException, WorldEditException
	{
		LocalConfiguration config = worldEd.getConfiguration();
		File dir = worldEd.getWorkingDirectoryFile(config.saveDir);
		File f = worldEd.getSafeSaveFile(player, dir, filename, "schematic", "schematic");
		ClipboardFormat format = ClipboardFormat.findByAlias(formatName);
	}
	

}
