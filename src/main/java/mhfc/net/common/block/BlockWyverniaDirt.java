package mhfc.net.common.block;

import java.util.Random;

import mhfc.net.MHFCMain;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockWyverniaDirt extends Block {

	public BlockWyverniaDirt() {
		super(Material.ground);
		setBlockName(MHFCReference.block_wyveriandirt_name);
		setBlockTextureName(MHFCReference.block_wyveriandirt_tex);
		setHardness(0.8F);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}
}
