package mhfc.net.common.block;

import java.util.Random;

import mhfc.net.MHFCMain;
import mhfc.net.common.util.Libraries;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockLosGable extends Block {

	public BlockLosGable() {
		super(Material.ROCK);
		setUnlocalizedName(Libraries.block_losgable_name);
		setHardness(1.5F);
		setResistance(1.0F);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}
}
