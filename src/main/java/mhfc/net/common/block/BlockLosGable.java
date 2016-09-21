package mhfc.net.common.block;

import java.util.Random;

import mhfc.net.MHFCMain;
import mhfc.net.common.index.ResourceInterface;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockLosGable extends Block {

	public BlockLosGable() {
		super(Material.ROCK);
		setUnlocalizedName(ResourceInterface.block_losgable_name);
		setHardness(1.5F);
		setResistance(1.0F);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}
}
