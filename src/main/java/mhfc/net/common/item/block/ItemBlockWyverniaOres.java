package mhfc.net.common.item.block;

import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockWyverniaOres extends ItemBlock {
	
	
	public ItemBlockWyverniaOres(Block block) {
		super(block);
		setHasSubtypes(true);
		maxStackSize = 12;
		
	}

	public String getUnlocalizedName(ItemStack stack) {
		int i = stack.getItemDamage();
		if(i<0||i >= MHFCReference.oreSubBlocks.length) {
			i = 0;
		}
		
		return super.getUnlocalizedName() + "." + MHFCReference.oreSubBlocks[i];
	}
	
	public int getMetadata(int meta) {
		return meta;
	}
	
	

}
