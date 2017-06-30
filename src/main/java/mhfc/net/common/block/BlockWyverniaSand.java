package mhfc.net.common.block;

import java.util.Random;

import mhfc.net.MHFCMain;
import mhfc.net.common.index.ResourceInterface;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockWyverniaSand extends Block {

	public BlockWyverniaSand() {
		super(Material.SAND);
		setUnlocalizedName(ResourceInterface.block_wyveriansand_name);
		setHardness(1.3F);
		setResistance(2.0F);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}
}
