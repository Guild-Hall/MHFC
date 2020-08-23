package mhfc.net.common.block.decoration;

import mhfc.net.MHFCMain;
import mhfc.net.common.index.ResourceInterface;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.Random;

public class BlockDiscstone extends Block {

	public BlockDiscstone() {
		super(Material.ROCK);
		setUnlocalizedName(ResourceInterface.block_discstone_name);
		setHardness(1.3F);
		setResistance(2.0F);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}
	

}
