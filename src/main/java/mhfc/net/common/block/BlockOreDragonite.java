package mhfc.net.common.block;

import java.util.Random;

import mhfc.net.MHFCMain;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockOreDragonite extends Block {

	public BlockOreDragonite() {
		super(Material.rock);
		setBlockName(MHFCReference.block_oredragonite_name);
		setBlockTextureName(MHFCReference.block_oredragonite_tex);
		setHardness(1.5F);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}
}
