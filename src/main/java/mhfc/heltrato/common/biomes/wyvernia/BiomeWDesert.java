package mhfc.heltrato.common.biomes.wyvernia;

import net.minecraft.block.Block;
import mhfc.heltrato.common.biomes.WyverniaBiomeBase;
import mhfc.heltrato.common.core.registry.MHFCRegBlock;

public class BiomeWDesert extends WyverniaBiomeBase {

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
