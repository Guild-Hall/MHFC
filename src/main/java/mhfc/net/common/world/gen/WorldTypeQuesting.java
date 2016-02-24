package mhfc.net.common.world.gen;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldTypeQuesting extends WorldType {

	public WorldTypeQuesting() {
		super("questing");
	}

	@Override
	public IChunkProvider getChunkGenerator(World world, String generatorOptions) {
		return new ChunkProviderQuesting(world);
	}

	@Override
	public WorldChunkManager getChunkManager(World world) {
		return new ChunkManagerQuesting(world);
	}

	@Override
	public int getSpawnFuzz() {
		return 1;
	}

}
