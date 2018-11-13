package mhfc.net.common.block.decoration;

import java.util.Random;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.index.ResourceInterface;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class BlockClay extends Block {

	public BlockClay() {
		super(Material.CLAY);
		setTranslationKey(ResourceInterface.block_wyverianclay_name);
		
		setHardness(0.9f);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return MHFCItemRegistry.getRegistry().wyverniaClay;
	}

	@Override
	public int quantityDropped(Random random) {
		return 2;
	}
}
