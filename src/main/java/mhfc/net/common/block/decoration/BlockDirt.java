package mhfc.net.common.block.decoration;

import java.util.Random;

import mhfc.net.MHFCMain;
import mhfc.net.common.index.ResourceInterface;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockDirt extends Block {

	public BlockDirt() {
		super(Material.GROUND);
		setUnlocalizedName(ResourceInterface.block_wyveriandirt_name);
		setHardness(0.8F);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}
}
