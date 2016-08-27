package mhfc.net.common.core.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.eventhandler.MHFCInteractionHandler;
import mhfc.net.common.network.message.quest.MessageMHFCInteraction;
import mhfc.net.common.network.message.quest.MessageMHFCInteraction.Interaction;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;

public class CommandMHFC implements ICommand {

	List<String> aliases;

	public CommandMHFC() {
		aliases = new ArrayList<String>();
		aliases.add("mhfc");
		aliases.add("MHFC");
	}

	@Override
	public int compareTo(Object o) {
		return -1;
	}

	@Override
	public String getCommandName() {
		return "Mhfc";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return "/mhfc <action> [options]";
	}

	@Override
	public List<String> getCommandAliases() {
		return aliases;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] parameters) {
		if (sender instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) sender;
			MHFCMain.logger().debug("Look vec: {} yaw: {}", player.getLookVec(), player.rotationYaw);
			Interaction action;
			if (parameters.length == 0)
				return;
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
				System.out.println("Invalid parameter in command mhfc");
				return;
			}
			MessageMHFCInteraction msg = new MessageMHFCInteraction(
					action,
					Arrays.copyOfRange(parameters, 1, parameters.length));
			MHFCInteractionHandler.onInteraction(player, msg);
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_) {
		return true;
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender p_71516_1_, String[] options) {
		List<String> list = new ArrayList<String>();
		if (options.length == 1) {
			for (String s : new String[] { "accept", "leave", "new", "surrender", "start", "reload" }) {
				if (options[0] == null || s.startsWith(options[0]))
					list.add(s);
			}
		}
		return list;
	}

	@Override
	public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
		return false;
	}

}
