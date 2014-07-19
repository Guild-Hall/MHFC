package mhfc.net.common.block;

import java.util.Random;

import mhfc.net.MHFCMain;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockOreEltalite extends Block {

	public BlockOreEltalite() {
		super(Material.rock);
		setBlockName(MHFCReference.block_oreeltalite_name);
		setBlockTextureName(MHFCReference.block_oreeltalite_tex);
		setHardness(1.7F);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}
}
