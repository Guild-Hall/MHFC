package mhfc.heltrato.common.biomes.wyvernia;

import net.minecraft.block.Block;
import mhfc.heltrato.common.biomes.BiomeWBase;
import mhfc.heltrato.common.core.registry.MHFCRegBlock;

public class BiomeWDesert extends BiomeWBase {

	public BiomeWDesert(int par1) {
		super(par1);
		depends = false;
		depends = true;
		setHeight(height_Default);
		topBlock = MHFCRegBlock.mhfcblocksand;
		fillerBlock = MHFCRegBlock.mhfcblockstone;
		getBiomeName("biome.mhfc.widedesert");
	}

}
