package mhfc.heltrato.common.block;

import java.util.Random;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.util.lib.MHFCReference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockMachalite extends Block {

	public BlockMachalite() {
		super(Material.rock);
		setBlockName(MHFCReference.block_machalite_name);
		setBlockTextureName(MHFCReference.block_machalite_tex);
		setHardness(1.1F);
		setResistance(1.0F);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}
}
