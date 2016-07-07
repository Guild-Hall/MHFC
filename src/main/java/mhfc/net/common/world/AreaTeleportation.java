package mhfc.net.common.world;

import java.util.HashMap;
import java.util.Map;

import mhfc.net.MHFCMain;
import mhfc.net.common.world.area.IArea;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class AreaTeleportation {

	private static class OverworldTeleporter extends Teleporter {
		private double posX;
		private double posY;
		private double posZ;

		public OverworldTeleporter(double posX, double posY, double posZ) {
			super(MinecraftServer.getServer().worldServerForDimension(0));
			this.posX = posX;
			this.posY = posY;
			this.posZ = posZ;
		}

		@Override
		public void placeInPortal(
				Entity entity,
				double p_77185_2_,
				double p_77185_4_,
				double p_77185_6_,
				float p_77185_8_) {
			MHFCMain.logger().debug("Teleporting {} to overworld to {} {} {}", entity, posX, posY, posZ);
			moveEntityTo(entity, posX, posY, posZ);
		}

		@Override
		public boolean placeInExistingPortal(
				Entity p_77184_1_,
				double p_77184_2_,
				double p_77184_4_,
				double p_77184_6_,
				float p_77184_8_) {
			placeInPortal(p_77184_1_, p_77184_2_, p_77184_4_, p_77184_6_, p_77184_8_);
			return true;
		}

		@Override
		public boolean makePortal(Entity p_85188_1_) {
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
		public void placeInPortal(
				Entity player,
				double p_77185_2_,
				double p_77185_4_,
				double p_77185_6_,
				float p_77185_8_) {
			if (player instanceof EntityPlayerMP) {
				MHFCMain.logger().debug("Teleporting {} to area {}", player, area);
				area.teleportToSpawn((EntityPlayerMP) player);
			}
		}

		@Override
		public boolean makePortal(Entity p_85188_1_) {
			return false;
		}

		@Override
		public void removeStalePortalLocations(long p_85189_1_) {}

		@Override
		public boolean placeInExistingPortal(
				Entity p_77184_1_,
				double p_77184_2_,
				double p_77184_4_,
				double p_77184_6_,
				float p_77184_8_) {
			placeInPortal(p_77184_1_, p_77184_2_, p_77184_4_, p_77184_6_, p_77184_8_);
			return false;
		}
	}

	private static Map<Entity, IArea> entityToArea = new HashMap<>();

	/**
	 * Moves a player to an area and teleports him to the dimension if necessary. If area is null, returns the player to
	 * the overwold instead.
	 */
	public static void movePlayerToArea(EntityPlayerMP player, IArea area) {
		assignAreaForEntity(player, area);
		if (area == null) {
			movePlayerToOverworld(player);
			return;
		}
		int areaDimensionId = area.getWorldView().getWorldObject().provider.dimensionId;
		AreaTeleporter areaTeleporter = new AreaTeleporter(area);
		if (player.dimension == areaDimensionId) {
			areaTeleporter.placeInPortal(player, 0, 0, 0, 0);
		} else {
			ServerConfigurationManager mg = MinecraftServer.getServer().getConfigurationManager();
			mg.transferPlayerToDimension(player, areaDimensionId, areaTeleporter);
		}
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

	public static void movePlayerToOverworld(EntityPlayerMP player) {
		ChunkCoordinates spawnPoint = MinecraftServer.getServer().worldServerForDimension(0).getSpawnPoint();
		movePlayerToOverworld(player, spawnPoint.posX, spawnPoint.posY, spawnPoint.posZ);
	}

	public static void movePlayerToOverworld(EntityPlayerMP player, double posX, double posY, double posZ) {
		assignAreaForEntity(player, null);
		OverworldTeleporter tp = new OverworldTeleporter(posX, posY, posZ);
		if (player.dimension == 0) {
			tp.placeInPortal(player, 0, 0, 0, 0);
		} else {
			ServerConfigurationManager mg = MinecraftServer.getServer().getConfigurationManager();
			mg.transferPlayerToDimension(player, 0, tp);
		}
	}

	private static WorldServer getWorldServer(IArea area) {
		int dim = area.getWorldView().getWorldObject().provider.dimensionId;
		return MinecraftServer.getServer().worldServerForDimension(dim);
	}

	public static void moveEntityTo(Entity entity, double posX, double posY, double posZ) {
		if (entity instanceof EntityLivingBase) {
			EntityLivingBase entityLiving = (EntityLivingBase) entity;
			entityLiving.setPositionAndUpdate(posX, posY, posZ);
			entityLiving.setPosition(posX, posY, posZ);
		} else {
			entity.setPosition(posX, posY, posZ);
		}
	}

}
