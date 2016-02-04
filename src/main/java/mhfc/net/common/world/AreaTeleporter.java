package mhfc.net.common.world;

import mhfc.net.common.world.area.IArea;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class AreaTeleporter extends Teleporter {

	/**
	 * Moves a player to an area and teleports him to the dimension if necessary
	 */
	public static void movePlayerToArea(EntityPlayerMP player, IArea area) {
		int areaDimensionId = area.getWorldView().getWorldObject().provider.dimensionId;
		if (player.dimension == areaDimensionId) {
			area.teleportToSpawn(player);
		} else {
			ServerConfigurationManager mg = MinecraftServer.getServer().getConfigurationManager();
			mg.transferPlayerToDimension(player, areaDimensionId, new AreaTeleporter(area));
		}
	}

	private static WorldServer getWorldServer(IArea area) {
		int dim = area.getWorldView().getWorldObject().provider.dimensionId;
		return MinecraftServer.getServer().worldServerForDimension(dim);
	}

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
		if (player instanceof EntityPlayer)
			area.teleportToSpawn((EntityPlayer) player);
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
