package mhfc.net.common.world.gen;

import java.util.List;
import java.util.Random;

import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.WorldChunkManager;

public class ChunkManagerQuesting extends WorldChunkManager {
	private World world;

	public ChunkManagerQuesting(World world) {
		super(world);
		this.world = world;
	}

	@Override
	public ChunkPosition findBiomePosition(int x, int z, int range, List biomes, Random rand) {
		ChunkPosition ret = super.findBiomePosition(x, z, range, biomes, rand);
		if (x == 0 && z == 0 && !world.getWorldInfo().isInitialized() && ret == null) {
			ret = new ChunkPosition(0, 0, 0);
		}
		return ret;
	}
}
