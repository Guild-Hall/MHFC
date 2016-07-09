package mhfc.net.common.biomes;

import mhfc.net.common.core.registry.MHFCBlockRegistry;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeManager;

public class WyverniaBiomeBase extends BiomeGenBase {

	protected boolean depends;
	protected float getTemp;
	public static boolean questActive;// just sort of test for making sure if
										// the biome will spawn in overworld

	public WyverniaBiomeBase(int par1) {
		super(par1);
		spawnableMonsterList.clear();
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		worldGeneratorBigTree = null;
		enableRain = depends;
		temperature = getTemp;
		topBlock = MHFCBlockRegistry.getRegistry().mhfcblockgrass;
		fillerBlock = MHFCBlockRegistry.getRegistry().mhfcblockdirt;
		spawnThisBiome(this);
	}

	@Override
	public WyverniaBiomeBase setColor(int color) {
		return (WyverniaBiomeBase) super.setColor(color);
	}

	// TODO this is just a test method for generating for quest.
	public static void spawnThisBiome(BiomeGenBase base) {
		if (questActive) {
			BiomeManager.addSpawnBiome(base);
		} else {
			BiomeManager.removeSpawnBiome(base);
		}
	}

}
