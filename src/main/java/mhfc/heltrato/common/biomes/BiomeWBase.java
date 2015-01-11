package mhfc.heltrato.common.biomes;

import mhfc.heltrato.common.core.registry.MHFCRegBlock;
import mhfc.heltrato.common.entity.mob.EntityAptonoth;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeManager;

public class BiomeWBase extends BiomeGenBase {
	
	public boolean depends;
	public String name;
	public float getTemp;
	public static boolean questActive;// just sort of test for making sure if the biome will spawn in overworld

	public BiomeWBase(int par1) {
		super(par1);
		spawnableMonsterList.clear();
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		worldGeneratorBigTree = null;
		enableRain = depends;
		setBiomeName(name);
		temperature = getTemp;
		topBlock = MHFCRegBlock.mhfcblockgrass;
		fillerBlock = MHFCRegBlock.mhfcblockdirt;
		spawnableCreatureList.add(new SpawnListEntry(EntityAptonoth.class, 10, 3,5));
		spawnThisBiome(this);
	}
	
	public BiomeWBase setColor(int color) {
		return (BiomeWBase)super.setColor(color);
	}
	
	public void getBiomeName(String biomename){
		name = biomename;
	}
	
	
	//TODO this is just a test method for generating for quest.
	public static void spawnThisBiome(BiomeGenBase base){
		if(questActive)
		BiomeManager.addSpawnBiome(base);
		if(!questActive){
			BiomeManager.removeSpawnBiome(base);
		}
	}
	
	

}
