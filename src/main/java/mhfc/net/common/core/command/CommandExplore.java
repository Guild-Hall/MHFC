package mhfc.net.common.core.command;

import mhfc.net.common.core.registry.MHFCExplorationRegistry;
import mhfc.net.common.world.area.AreaRegistry;
import mhfc.net.common.world.area.IAreaType;
import mhfc.net.common.world.exploration.OverworldManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class CommandExplore extends CommandBase {

	@Override
	public String getName() {
		return "mhfcexplore";
	}

	@Override
	public String getUsage(ICommandSender p_71518_1_) {
		return "/" + getName() + " [area-name]";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] arguments) {
		if (!this.checkPermission(server, sender)) {
			return;
		}
		EntityPlayerMP player = (EntityPlayerMP) sender;
		if (arguments.length == 0) {
			MHFCExplorationRegistry.releasePlayer(player);
			OverworldManager.instance.respawn(player);
			return;
		} else if (arguments.length == 1) {
			String targetAreaName = arguments[0];
			IAreaType areaType = AreaRegistry.instance.getType(targetAreaName);
			if (areaType == null) {
				sender.sendMessage(new TextComponentString("Warning: the area type you choose did not exist"));
			}
			MHFCExplorationRegistry.transferPlayer(player, areaType);
		} else {
			sender.sendMessage(new TextComponentString("Too many arguments for command mhfcexplore"));
		}
	}
}
