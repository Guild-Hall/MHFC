package mhfc.net.common.block.decoration;

import mhfc.net.MHFCMain;
import mhfc.net.common.index.ResourceInterface;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.Random;

public class BlockStone extends Block {

	public BlockStone() {
		super(Material.ROCK);
		setUnlocalizedName(ResourceInterface.block_wyverianstone_name);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}
}
