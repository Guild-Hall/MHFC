package mhfc.net.common.core.command;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;

import mhfc.net.MHFCMain;
import mhfc.net.common.quests.world.GlobalAreaManager;
import mhfc.net.common.quests.world.QuestFlair;
import mhfc.net.common.util.world.WorldHelper;
import mhfc.net.common.world.AreaTeleportation;
import mhfc.net.common.world.area.AreaRegistry;
import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IAreaType;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Vec3;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class CommandTpHunterDimension implements ICommand {
	private Map<EntityPlayerMP, Vec3> teleportPoints = new HashMap<>();

	private class BackTeleporter extends Teleporter {

		public BackTeleporter(WorldServer server) {
			super(server);
		}

		@Override
		public void placeInPortal(Entity entity, double posX, double posY, double posZ, float rotationYaw) {
			ChunkCoordinates coords = entity.worldObj.getSpawnPoint();
			Vec3 spawnAt = teleportPoints
					.getOrDefault(entity, Vec3.createVectorHelper(coords.posX, coords.posY, coords.posZ));
			entity.setLocationAndAngles(spawnAt.xCoord, spawnAt.yCoord, spawnAt.zCoord, rotationYaw, 0.0F);
			AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(
					entity.posX,
					entity.posY,
					entity.posZ,
					entity.posX + 1,
					entity.posY + entity.height,
					entity.posZ + 1);
			if (entity.worldObj.checkBlockCollision(bb)) {
				spawnAt.yCoord = entity.worldObj.getTopSolidOrLiquidBlock((int) spawnAt.xCoord, (int) spawnAt.zCoord);
				entity.setLocationAndAngles(spawnAt.xCoord, spawnAt.yCoord, spawnAt.zCoord, rotationYaw, 0.0F);
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
	public int compareTo(Object o) {
		return -1;
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
	public List<String> getCommandAliases() {
		return Collections.emptyList();
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (!canCommandSenderUseCommand(sender)) {
			return;
		}

		EntityPlayerMP player = (EntityPlayerMP) sender;
		// players = args.length > 0 ? PlayerSelector.matchPlayers(sender, args[0]) : players;
		ServerConfigurationManager mg = MinecraftServer.getServer().getConfigurationManager();
		int questWorldID = GlobalAreaManager.instance.getWorldIDFor(QuestFlair.DAYTIME);
		WorldServer server = GlobalAreaManager.instance.getServerFor(QuestFlair.DAYTIME);

		if (player.dimension == questWorldID) {
			Teleporter tpOverworld = new BackTeleporter(server);
			mg.transferPlayerToDimension(player, 0, tpOverworld);
		} else {
			String areaName = args.length > 0 ? args[0] : AreaRegistry.NAME_PLAYFIELD;
			IAreaType areaType = AreaRegistry.instance.getType(areaName);
			if (areaType == null) {
				sender.addChatMessage(new ChatComponentText("Could not find requested target area type."));
				MHFCMain.logger().debug("No area type found for " + areaName);
				return;
			}
			CompletionStage<IActiveArea> futureArea = GlobalAreaManager.instance
					.getUnusedInstance(areaType, QuestFlair.DAYTIME);
			sender.addChatMessage(new ChatComponentText("You will be teleported when the area is finished"));
			futureArea.thenAccept((a) -> {
				try (IActiveArea active = a) {
					MHFCMain.logger().info("Teleporting");
					teleportPoints.put(player, WorldHelper.getEntityPositionVector(player));
					AreaTeleportation.movePlayerToArea(player, active.getArea());
				}
			});
		}
		MHFCMain.logger().debug("Teleported to/from dimension " + questWorldID);
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		if (sender instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) sender;
			boolean isOp = MinecraftServer.getServer().getConfigurationManager().func_152596_g(player.getGameProfile());
			return isOp || player.capabilities.isCreativeMode;
		}
		return false;
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_) {
		return Collections.emptyList();
	}

	@Override
	public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
		return false;
	}

}
