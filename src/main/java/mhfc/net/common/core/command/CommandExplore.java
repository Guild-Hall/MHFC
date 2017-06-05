package mhfc.net.common.core.command;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCExplorationRegistry;
import mhfc.net.common.quests.world.QuestFlair;
import mhfc.net.common.world.area.AreaRegistry;
import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IAreaType;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
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
	public void execute(MinecraftServer server, ICommandSender sender, String[] arguments) throws CommandException {
		if (!this.checkPermission(server, sender)) {
			return;
		}

		if (!(sender instanceof EntityPlayerMP)) {
			throw new CommandException("Command only useable by players");
		}

		EntityPlayerMP player = (EntityPlayerMP) sender;
		if (arguments.length == 0) {
			MHFCExplorationRegistry.releasePlayer(player).respawn();
			return;
		}

		if (arguments.length == 1) {
			String targetAreaName = arguments[0];
			IAreaType areaType = AreaRegistry.instance.getType(targetAreaName);
			if (areaType == null) {
				sender.sendMessage(new TextComponentString("The area type " + targetAreaName + " does not exist"));
				return;
			}

			MHFCExplorationRegistry.transferPlayer(player, areaType, QuestFlair.DAYTIME)
					.whenComplete((a, e) -> onTransferComplete(player, a, e));
			return;
		}

		sender.sendMessage(new TextComponentString("Too many arguments for command mhfcexplore"));
	}

	private void onTransferComplete(EntityPlayerMP player, IActiveArea area, Throwable exception) {
		if (exception != null) {
			TextComponentString failMessage = new TextComponentString("Failed to allocate an area for the player: " + exception.getMessage());
			player.sendMessage(failMessage);
			MHFCMain.logger().catching(exception);
		} else {
			;
		}
	}

	@Override
	public List<String> getTabCompletions(
			MinecraftServer server,
			ICommandSender sender,
			String[] args,
			BlockPos targetPos) {
		if (args.length > 1) {
			return Collections.emptyList();
		}
		Set<String> areas = AreaRegistry.getTypeNames();
		String filter = args.length > 0 ? args[0] : StringUtils.EMPTY;
		return areas.stream().filter(s -> s.startsWith(filter)).collect(Collectors.toList());
	}
}
