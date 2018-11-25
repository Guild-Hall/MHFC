package mhfc.net.common.core.command;

import mhfc.net.MHFCMain;
import mhfc.net.common.network.handler.MHFCInteractionHandler;
import mhfc.net.common.network.message.quest.MessageMHFCInteraction;
import mhfc.net.common.network.message.quest.MessageMHFCInteraction.Interaction;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandMHFC extends CommandBase {

	List<String> aliases;

	public CommandMHFC() {
		aliases = new ArrayList<>();
		aliases.add("mhfc");
		aliases.add("MHFC");
	}

	@Override
	public String getName() {
		return "Mhfc";
	}

	@Override
	public String getUsage(ICommandSender p_71518_1_) {
		return "/mhfc <action> [options]";
	}

	@Override
	public List<String> getAliases() {
		return aliases;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] parameters) {
		if (sender instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) sender;
			MHFCMain.logger().debug("Look vec: {} yaw: {}", player.getLookVec(), player.rotationYaw);
			Interaction action;
			if (parameters.length == 0) {
				return;
			}
			switch (parameters[0]) {
			case "new":
				action = Interaction.NEW_QUEST;
				break;
			case "surrender":
				action = Interaction.END_QUEST;
				break;
			case "accept":
				action = Interaction.ACCEPT_QUEST;
				break;
			case "leave":
				action = Interaction.FORFEIT_QUEST;
				break;
			case "start":
				action = Interaction.START_QUEST;
				break;
			case "reload":
				action = Interaction.MOD_RELOAD;
				break;
			default:
				MHFCMain.logger().debug("Invalid parameter in command mhfc");
				return;
			}
			MessageMHFCInteraction msg = new MessageMHFCInteraction(
					action,
					Arrays.copyOfRange(parameters, 1, parameters.length));
			MHFCInteractionHandler.onInteraction(player, msg);
		}
	}

	@Override
	public List<String> getTabCompletions(
			MinecraftServer server,
			ICommandSender sender,
			String[] options,
			BlockPos pos) {
		List<String> list = new ArrayList<>();
		if (options.length == 1) {
			for (String s : new String[] { "accept", "leave", "new", "surrender", "start", "reload" }) {
				if (options[0] == null || s.startsWith(options[0])) {
					list.add(s);
				}
			}
		}
		return list;
	}
}
