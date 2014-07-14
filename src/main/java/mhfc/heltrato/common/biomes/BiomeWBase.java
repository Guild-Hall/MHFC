package mhfc.heltrato.common.biomes;

import mhfc.heltrato.common.core.registry.MHFCRegBlock;
import mhfc.heltrato.common.entity.mob.EntityAptonoth;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeWBase extends BiomeGenBase {
	
	public boolean depends;
	public String name;
	

	public BiomeWBase(int par1) {
		super(par1);
		spawnableMonsterList.clear();
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		worldGeneratorBigTree = null;
		enableRain = depends;
		setBiomeName(name);
		topBlock = MHFCRegBlock.mhfcblockgrass;
		fillerBlock = MHFCRegBlock.mhfcblockdirt;
		spawnableCreatureList.add(new SpawnListEntry(EntityAptonoth.class, 10, 3,5));
	}
	
	public BiomeWBase setColor(int color) {
		return (BiomeWBase)super.setColor(color);
	}
	
	

}
