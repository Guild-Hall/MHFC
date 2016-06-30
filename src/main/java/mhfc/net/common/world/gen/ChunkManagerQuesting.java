package mhfc.net.common.world.gen;

import java.util.List;
import java.util.Random;

import mhfc.net.common.world.MHFCWorldData;
import mhfc.net.common.world.controller.AreaManager;
import mhfc.net.common.world.controller.IAreaManager;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.WorldChunkManager;

public class ChunkManagerQuesting extends WorldChunkManager {
	private World world;
	private IAreaManager areaManger;

	public ChunkManagerQuesting(World world) {
		super(world);
		this.world = world;
	}

	public void finishSetup() {
		MHFCWorldData data = (MHFCWorldData) this.world.perWorldStorage.loadData(MHFCWorldData.class, "mhfcareas");
		if (data == null) {
			data = new MHFCWorldData("mhfcareas");
			this.world.perWorldStorage.setData("mhfcareas", data);
		}
		this.areaManger = new AreaManager(world, data);
	}

	@Override
	public ChunkPosition findBiomePosition(int x, int z, int range, List biomes, Random rand) {
		ChunkPosition ret = super.findBiomePosition(x, z, range, biomes, rand);
		if (x == 0 && z == 0 && !world.getWorldInfo().isInitialized() && ret == null) {
			ret = new ChunkPosition(0, 0, 0);
		}
		return ret;
	}

	public IAreaManager getAreaManager() {
		return this.areaManger;
	}

}
