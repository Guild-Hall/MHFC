package mhfc.net.common.block;

import java.util.Random;

import mhfc.net.MHFCMain;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.block.BlockFalling;

public class BlockWyverniaSand extends BlockFalling {

	public BlockWyverniaSand() {
		super();
		setBlockName(MHFCReference.block_wyveriansand_name);
		setBlockTextureName(MHFCReference.block_wyveriansand_tex);
		setHardness(1.3F);
		setResistance(2.0F);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}
}
