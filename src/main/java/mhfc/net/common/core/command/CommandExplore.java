package mhfc.net.common.core.command;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import mhfc.net.common.core.registry.MHFCExplorationRegistry;
import mhfc.net.common.quests.world.QuestFlair;
import mhfc.net.common.world.area.AreaRegistry;
import mhfc.net.common.world.area.IAreaType;
import net.minecraft.command.CommandBase;
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
	public void execute(MinecraftServer server, ICommandSender sender, String[] arguments) {
		if (!this.checkPermission(server, sender)) {
			return;
		}
		EntityPlayerMP player = (EntityPlayerMP) sender;
		if (arguments.length == 0) {
			MHFCExplorationRegistry.releasePlayer(player).respawn();
			return;
		} else if (arguments.length == 1) {
			String targetAreaName = arguments[0];
			IAreaType areaType = AreaRegistry.instance.getType(targetAreaName);
			if (areaType == null) {
				sender.sendMessage(new TextComponentString("Warning: the area type you choose did not exist"));
			} else {
				MHFCExplorationRegistry.transferPlayer(player, areaType, QuestFlair.DAYTIME);
			}
		} else {
			sender.sendMessage(new TextComponentString("Too many arguments for command mhfcexplore"));
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
