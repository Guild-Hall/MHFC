package mhfc.net.common.world;

import java.util.HashMap;
import java.util.Map;

import mhfc.net.MHFCMain;
import mhfc.net.common.quests.world.IQuestArea;
import mhfc.net.common.world.area.IArea;
import mhfc.net.common.world.area.IWorldView;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class AreaTeleportation {

	private static class OverworldTeleporter extends Teleporter {
		private BlockPos pos;

		public OverworldTeleporter(MinecraftServer server, BlockPos pos) {
			super(server.worldServerForDimension(0));
			this.pos = pos;
		}

		@Override
		public void placeInPortal(Entity entity, float rotationYaw) {
			MHFCMain.logger().debug("Teleporting {} to overworld to {}", entity, pos);
			moveEntityTo(entity, pos);
		}

		@Override
		public boolean placeInExistingPortal(Entity entity, float rotationYaw) {
			placeInPortal(entity, rotationYaw);
			return true;
		}

		@Override
		public boolean makePortal(Entity entity) {
			return false;
		}
	}

	private static class AreaTeleporter extends Teleporter {
		protected IArea area;

		public AreaTeleporter(IArea area) {
			super(getWorldServer(area));
			this.area = area;
		}

		@Override
		public void placeInPortal(Entity player, float rotationYaw) {
			if (player instanceof EntityPlayerMP) {
				MHFCMain.logger().debug("Teleporting {} to area {}", player, area);
				IWorldView worldView = area.getWorldView();
				BlockPos spawnPos = area.resolvePosition(IQuestArea.PLAYER_SPAWN);
				spawnPos = worldView.getTopSolidOrLiquidBlock(spawnPos).up();

				worldView.moveEntityTo(player, spawnPos);
			}
		}

		@Override
		public boolean placeInExistingPortal(Entity entityIn, float rotationYaw) {
			placeInPortal(entityIn, rotationYaw);
			return false;
		}

		@Override
		public boolean makePortal(Entity entity) {
			return false;
		}

		@Override
		public void removeStalePortalLocations(long p_85189_1_) {}
	}

	private static Map<Entity, IArea> entityToArea = new HashMap<>();

	private static void changePlayerDimension(EntityPlayerMP player, int dimension, Teleporter tp) {
		if (player.dimension != dimension) {
			player.mcServer.getPlayerList().transferPlayerToDimension(player, dimension, tp);
		} else {
			tp.placeInPortal(player, player.rotationYaw);
		}
	}

	/**
	 * Moves a player to an area and teleports him to the dimension if necessary. If area is null, returns the player to
	 * the overwold instead.
	 */
	public static void movePlayerToArea(EntityPlayerMP player, IArea area) {
		int areaDimensionId = area.getWorldView().getWorldObject().provider.getDimension();
		assignAreaForEntity(player, area);
		AreaTeleporter areaTeleporter = new AreaTeleporter(area);

		changePlayerDimension(player, areaDimensionId, areaTeleporter);
	}

	/**
	 * Assigns an area to the entity or removes the assignment if area is null
	 */
	public static void assignAreaForEntity(Entity entity, IArea area) {
		if (area == null) {
			entityToArea.remove(entity);
		} else {
			entityToArea.put(entity, area);
		}
	}

	/**
	 * Retrieves the currently assigned area for an entity
	 */
	public static IArea getAssignedArea(Entity entity) {
		return entityToArea.get(entity);
	}

	public static void movePlayerToOverworld(MinecraftServer server, EntityPlayerMP player) {
		BlockPos spawnPoint = server.worldServerForDimension(0).getSpawnPoint();
		movePlayerToOverworld(server, player, spawnPoint);
	}

	public static void movePlayerToOverworld(MinecraftServer server, EntityPlayerMP player, BlockPos pos) {
		assignAreaForEntity(player, null);
		OverworldTeleporter tp = new OverworldTeleporter(server, pos);

		changePlayerDimension(player, 0, tp);
	}

	private static WorldServer getWorldServer(IArea area) {
		MinecraftServer server = area.getWorldView().getWorldObject().getMinecraftServer();
		int dim = area.getWorldView().getWorldObject().provider.getDimension();
		return server.worldServerForDimension(dim);
	}

	public static void moveEntityTo(Entity entity, BlockPos pos) {
		double x = pos.getX();
		double y = pos.getY();
		double z = pos.getZ();
		entity.setPositionAndUpdate(x + 0.5d, y, z + 0.5d);
	}

}
