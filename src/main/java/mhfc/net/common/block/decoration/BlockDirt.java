package mhfc.net.common.block.decoration;

import java.util.Random;

import mhfc.net.MHFCMain;
import mhfc.net.common.index.ResourceInterface;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockDirt extends Block {

	public BlockDirt() {
		super(Material.GROUND);
		setTranslationKey(ResourceInterface.block_wyveriandirt_name);
		setHardness(0.8F);
		setCreativeTab(MHFCMain.mhfctabs);
		this.setSoundType(SoundType.GROUND);
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}
}
