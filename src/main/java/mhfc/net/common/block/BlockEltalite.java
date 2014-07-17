package mhfc.net.common.block;

import java.util.Random;

import mhfc.net.MHFCMain;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockEltalite extends Block {

	public BlockEltalite() {
		super(Material.rock);
		setBlockName(MHFCReference.block_eltalite_name);
		setBlockTextureName(MHFCReference.block_eltalite_tex);
		setHardness(1.5F);
		setResistance(1.0F);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}
}
