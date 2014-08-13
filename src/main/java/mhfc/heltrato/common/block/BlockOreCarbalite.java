package mhfc.heltrato.common.block;

import java.util.Random;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.util.lib.MHFCReference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockOreCarbalite extends Block {

	public BlockOreCarbalite() {
		super(Material.rock);
		setBlockName(MHFCReference.block_orecarbalite_name);
		setBlockTextureName(MHFCReference.block_orecarbalite_tex);
		setHardness(1.3F);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}
}
