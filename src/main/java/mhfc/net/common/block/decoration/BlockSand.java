package mhfc.net.common.block.decoration;

import java.util.Random;

import mhfc.net.MHFCMain;
import mhfc.net.common.index.ResourceInterface;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockSand extends Block {

	public BlockSand() {
		super(Material.SAND);
		setTranslationKey(ResourceInterface.block_wyveriansand_name);
		setHardness(1.3F);
		setResistance(2.0F);
		setCreativeTab(MHFCMain.mhfctabs);
		setSoundType(SoundType.SAND);
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}
}
