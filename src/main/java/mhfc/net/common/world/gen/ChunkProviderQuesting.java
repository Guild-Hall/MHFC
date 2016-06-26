package mhfc.net.common.world.gen;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderFlat;

public class ChunkProviderQuesting extends ChunkProviderFlat {
	private World world;

	public ChunkProviderQuesting(World world) {
		super(world, world.getSeed(), false, null);
		this.world = world;
	}

	@Override
	public void populate(IChunkProvider par1IChunkProvider, int par2, int par3) {}

	@Override
	public Chunk provideChunk(int x, int z) {
		Chunk ret = new Chunk(world, x, z);
		@SuppressWarnings("unused")
		BiomeGenBase[] biomes = world.getWorldChunkManager().loadBlockGeneratorData(null, x * 16, z * 16, 16, 16);
		byte[] ids = ret.getBiomeArray();

		for (int i = 0; i < ids.length; ++i) {
			ids[i] = 0;
		}

		ret.generateSkylightMap();
		return ret;
	}

}
