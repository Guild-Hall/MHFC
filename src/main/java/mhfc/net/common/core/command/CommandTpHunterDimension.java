package mhfc.net.common.core.command;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionStage;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCDimensionRegistry;
import mhfc.net.common.quests.world.QuestFlair;
import mhfc.net.common.world.AreaTeleportation;
import mhfc.net.common.world.area.AreaRegistry;
import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IAreaType;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class CommandTpHunterDimension extends CommandBase {
	private Map<EntityPlayerMP, BlockPos> teleportPoints = new HashMap<>();

	// FIXME: use or delete back teleporter
	private class BackTeleporter extends Teleporter {

		public BackTeleporter(WorldServer server) {
			super(server);
		}

		@Override
		public void placeInPortal(Entity entity, float rotationYaw) {
			BlockPos worldSpawn = entity.worldObj.getSpawnPoint();
			BlockPos.MutableBlockPos spawnAt = new BlockPos.MutableBlockPos(
					teleportPoints.getOrDefault(entity, worldSpawn));

			entity.moveToBlockPosAndAngles(spawnAt, rotationYaw, 0.0F);
			AxisAlignedBB bb = entity.getEntityBoundingBox();
			if (entity.worldObj.checkBlockCollision(bb)) {
				spawnAt.setY(entity.worldObj.getTopSolidOrLiquidBlock(spawnAt).getY());
				entity.setPositionAndRotation(spawnAt.getX(), spawnAt.getY(), spawnAt.getZ(), rotationYaw, 0.0F);
			}
			entity.motionX = entity.motionY = entity.motionZ = 0.0D;
		}

		@Override
		public boolean makePortal(Entity e) {
			return false;
		}

		@Override
		public void removeStalePortalLocations(long time) {
			;
		}

	}

	@Override
	public String getCommandName() {
		return "mhfctp";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return "/" + getCommandName() + "[area-name]";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
		if (!checkPermission(server, sender)) {
			return;
		}

		EntityPlayerMP player = (EntityPlayerMP) sender;
		// players = args.length > 0 ? PlayerSelector.matchPlayers(sender, args[0]) : players;
		int questWorldID = MHFCDimensionRegistry.getWorldIDFor(QuestFlair.DAYTIME);

		if (player.dimension == questWorldID) {
			AreaTeleportation.movePlayerToOverworld(server, player);
		} else {
			String areaName = args.length > 0 ? args[0] : AreaRegistry.NAME_PLAYFIELD;
			IAreaType areaType = AreaRegistry.instance.getType(areaName);
			if (areaType == null) {
				sender.addChatMessage(new TextComponentString("Could not find requested target area type."));
				MHFCMain.logger().debug("No area type found for " + areaName);
				return;
			}
			CompletionStage<IActiveArea> futureArea = MHFCDimensionRegistry
					.getUnusedInstance(areaType, QuestFlair.DAYTIME);
			sender.addChatMessage(new TextComponentString("You will be teleported when the area is finished"));
			futureArea.thenAccept((a) -> {
				try (IActiveArea active = a) {
					MHFCMain.logger().info("Teleporting");
					teleportPoints.put(player, player.getPosition());
					AreaTeleportation.movePlayerToArea(player, active.getArea());
				}
			});
		}
		MHFCMain.logger().debug("Teleported to/from dimension " + questWorldID);
	}
}
