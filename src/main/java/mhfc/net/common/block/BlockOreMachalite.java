package mhfc.net.common.block;

import java.util.Random;

import mhfc.net.MHFCMain;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockOreMachalite extends Block {

	public BlockOreMachalite() {
		super(Material.rock);
		setBlockName(MHFCReference.block_oremachalite_name);
		setBlockTextureName(MHFCReference.block_oremachalite_tex);
		setHardness(1.2F);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public int quantityDropped(Random random) {
		return 2;
	}
}
