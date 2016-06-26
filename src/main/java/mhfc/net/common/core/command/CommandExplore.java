package mhfc.net.common.core.command;

import java.util.List;

import mhfc.net.common.core.registry.MHFCExplorationRegistry;
import mhfc.net.common.world.area.AreaRegistry;
import mhfc.net.common.world.area.IAreaType;
import mhfc.net.common.world.exploration.OverworldManager;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;

public class CommandExplore implements ICommand {

	@Override
	public int compareTo(Object o) {
		return -1;
	}

	@Override
	public String getCommandName() {
		return "mhfcexplore";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return "/" + getCommandName() + " [area-name]";
	}

	@Override
	public List<String> getCommandAliases() {
		return java.util.Collections.emptyList();
	}

	@Override
	public void processCommand(ICommandSender sender, String[] arguments) {
		if (!canCommandSenderUseCommand(sender))
			return;
		EntityPlayerMP player = (EntityPlayerMP) sender;
		if (arguments.length == 0) {
			MHFCExplorationRegistry.releasePlayer(player);
			OverworldManager.instance.respawn(player);
			return;
		} else if (arguments.length == 1) {
			String targetAreaName = arguments[0];
			IAreaType areaType = AreaRegistry.instance.getType(targetAreaName);
			if (areaType == null) {
				sender.addChatMessage(new ChatComponentText("Warning: the area type you choose did not exist"));
			}
			MHFCExplorationRegistry.transferPlayer(player, areaType, (t) -> {});
		} else {
			sender.addChatMessage(new ChatComponentText("Too many arguments for command mhfcexplore"));
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return sender instanceof EntityPlayerMP;
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender p_71516_1_, String[] arguments) {
		return java.util.Collections.emptyList();
	}

	@Override
	public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
		return false;
	}

}
