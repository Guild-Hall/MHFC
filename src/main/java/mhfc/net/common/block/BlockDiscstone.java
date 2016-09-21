package mhfc.net.common.block;

import java.util.Random;

import mhfc.net.MHFCMain;
import mhfc.net.common.util.Libraries;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockDiscstone extends Block {

	public BlockDiscstone() {
		super(Material.ROCK);
		setUnlocalizedName(Libraries.block_discstone_name);
		setHardness(1.3F);
		setResistance(2.0F);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}
}
