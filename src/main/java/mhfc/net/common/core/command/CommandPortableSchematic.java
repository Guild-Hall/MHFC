package mhfc.net.common.core.command;

import com.sk89q.worldedit.LocalConfiguration;
import com.sk89q.worldedit.WorldEdit;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import static com.google.common.base.Preconditions.checkNotNull;

public class CommandPortableSchematic extends CommandBase {
	
	private final WorldEdit world;
	
	public CommandPortableSchematic(WorldEdit world) {
		checkNotNull(world);
		this.world = world;
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
		LocalConfiguration config = world.getConfiguration();
		
	}
	
	

}
