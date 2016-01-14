package mhfc.net.common.world;

import mhfc.net.common.world.gen.ChunkManagerQuesting;
import mhfc.net.common.world.gen.ChunkProviderQuesting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderQuesting extends WorldProvider {

	public WorldProviderQuesting() {
		super();
	}

	@Override
	public String getDimensionName() {
		return "MHFC Questing";
	}

	@Override
	public ChunkCoordinates getRandomizedSpawnPoint() {
		ChunkCoordinates spawn = new ChunkCoordinates(worldObj.getSpawnPoint());
		spawn.posY = worldObj.getTopSolidOrLiquidBlock(spawn.posX, spawn.posZ);
		return spawn;
	}

	@Override
	protected void registerWorldChunkManager() {
		worldChunkMgr = new ChunkManagerQuesting(worldObj);
	}

	@Override
	public IChunkProvider createChunkGenerator() {
		return new ChunkProviderQuesting(worldObj);
	}

	@Override
	public boolean canMineBlock(EntityPlayer player, int x, int y, int z) {
		return false;
	}

	@Override
	public boolean canCoordinateBeSpawn(int x, int z) {
		return true;
	}
}
