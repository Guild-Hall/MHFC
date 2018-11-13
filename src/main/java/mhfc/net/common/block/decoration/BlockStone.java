package mhfc.net.common.block.decoration;

import java.util.Random;

import mhfc.net.MHFCMain;
import mhfc.net.common.index.ResourceInterface;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockStone extends Block {

	public BlockStone() {
		super(Material.ROCK);
		setTranslationKey(ResourceInterface.block_wyverianstone_name);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}
}
