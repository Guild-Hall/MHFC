package mhfc.net.common.block;

import java.util.Random;

import mhfc.net.MHFCMain;
import mhfc.net.common.util.Libraries;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockIceCrystal extends Block {

	public BlockIceCrystal() {
		super(Material.ROCK);
		setUnlocalizedName(Libraries.block_icecrystal_name);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}
}
