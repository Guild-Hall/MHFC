package mhfc.heltrato.common.block;

import java.util.Random;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.util.lib.MHFCReference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockWyverniaStone extends Block {

	public BlockWyverniaStone() {
		super(Material.rock);
		setBlockName(MHFCReference.block_wyverianstone_name);
		setBlockTextureName(MHFCReference.block_wyverianstone_tex);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}
}
