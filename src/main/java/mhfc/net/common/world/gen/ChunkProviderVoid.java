package mhfc.net.common.world.gen;

import java.util.Collections;
import java.util.List;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkGenerator;

public class ChunkProviderVoid implements IChunkGenerator {
	private World worldObj;

	public ChunkProviderVoid(World world) {
		this.worldObj = world;
	}

	@Override
	public void recreateStructures(Chunk chunkIn, int x, int z) {}

	@Override
	public boolean generateStructures(Chunk chunkIn, int x, int z) {
		return false;
	}

	@Override
	public List<SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
		return Collections.emptyList();
	}


	@Override
	public void populate(int x, int z) {}

	@Override
	public Chunk provideChunk(int x, int z) {
		Chunk chunk = new Chunk(worldObj, x, z);

		// Get the biome list from the biome provider (generate no blocks though)
		Biome[] abiome = this.worldObj.getBiomeProvider().getBiomes(null, x * 16, z * 16, 16, 16);
		byte[] biomeArray = chunk.getBiomeArray();

		for (int l = 0; l < biomeArray.length; ++l) {
			biomeArray[l] = (byte) Biome.getIdForBiome(abiome[l]);
		}
		chunk.generateSkylightMap();
		return chunk;
	}

	@Override
	public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position, boolean p_180513_4_) {
		// TODO Auto-generated method stub
		return new BlockPos(0, 0, 0);
	}

}
