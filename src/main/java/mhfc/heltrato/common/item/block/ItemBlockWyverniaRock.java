package mhfc.heltrato.common.item.block;

import mhfc.heltrato.common.util.lib.MHFCReference;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockWyverniaRock extends ItemBlock {
	
	
	public ItemBlockWyverniaRock(Block block) {
		super(block);
		setHasSubtypes(true);
		maxStackSize = 20;
		
	}

	public String getUnlocalizedName(ItemStack stack) {
		int i = stack.getItemDamage();
		if(i<0||i >= MHFCReference.stoneBlocks.length) {
			i = 0;
		}
		
		return super.getUnlocalizedName() + "." + MHFCReference.stoneBlocks[i];
	}
	
	public int getMetadata(int meta) {
		return meta;
	}
	
	

}
