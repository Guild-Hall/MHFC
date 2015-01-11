package mhfc.heltrato.common.item.block;

import mhfc.heltrato.common.util.lib.MHFCReference;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockWyverniaWood extends ItemBlock {
	
	
	public ItemBlockWyverniaWood(Block block) {
		super(block);
		setHasSubtypes(true);
		
	}

	public String getUnlocalizedName(ItemStack stack) {
		int i = stack.getItemDamage();
		if(i<0||i >= MHFCReference.logBlocks.length) {
			i = 0;
		}
		
		return super.getUnlocalizedName() + "." + MHFCReference.logBlocks[i];
	}
	
	public int getMetadata(int meta) {
		return meta;
	}
	
	

}
