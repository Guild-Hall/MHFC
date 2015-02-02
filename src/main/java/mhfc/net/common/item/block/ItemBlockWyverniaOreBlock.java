package mhfc.net.common.item.block;

import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockWyverniaOreBlock extends ItemBlock {
	
	
	public ItemBlockWyverniaOreBlock(Block block) {
		super(block);
		setHasSubtypes(true);
		maxStackSize = 16;
		
	}

	public String getUnlocalizedName(ItemStack stack) {
		int i = stack.getItemDamage();
		if(i<0||i >= MHFCReference.oreblockSubBlocks.length) {
			i = 0;
		}
		
		return super.getUnlocalizedName() + "." + MHFCReference.oreblockSubBlocks[i];
	}
	
	public int getMetadata(int meta) {
		return meta;
	}
	
	

}
