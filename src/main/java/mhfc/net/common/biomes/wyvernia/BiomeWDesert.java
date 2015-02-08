package mhfc.net.common.biomes.wyvernia;

import mhfc.net.common.biomes.WyverniaBiomeBase;
import mhfc.net.common.core.registry.MHFCBlockRegistry;

public class BiomeWDesert extends WyverniaBiomeBase {

	public BiomeWDesert(int par1) {
		super(par1);
		depends = false;
		depends = true;
		setHeight(height_Default);
		topBlock = MHFCBlockRegistry.mhfcblocksand;
		fillerBlock = MHFCBlockRegistry.mhfcblockstone;
		this.biomeName = "biome.mhfc.widedesert";
	}

}
