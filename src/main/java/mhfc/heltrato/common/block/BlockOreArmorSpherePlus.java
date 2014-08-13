package mhfc.heltrato.common.block;

import java.util.Random;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.util.lib.MHFCReference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockOreArmorSpherePlus extends Block {

	public BlockOreArmorSpherePlus() {
		super(Material.rock);
		setBlockName(MHFCReference.block_orearmorsphereplus_name);
		setBlockTextureName(MHFCReference.block_orearmorsphereplus_tex);
		setHardness(1.3F);
		setResistance(2.0F);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}
}
