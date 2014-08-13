package mhfc.heltrato.common.block;

import java.util.Random;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.util.lib.MHFCReference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockIceCrystal extends Block {

	public BlockIceCrystal() {
		super(Material.rock);
		setBlockName(MHFCReference.block_icecrystal_name);
		setBlockTextureName(MHFCReference.block_icecrystal_tex);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}
}
