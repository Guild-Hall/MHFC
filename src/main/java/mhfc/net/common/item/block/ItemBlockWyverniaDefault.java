package mhfc.net.common.item.block;

import java.util.List;

import mhfc.net.common.core.registry.MHFCRegBlock;
import mhfc.net.common.core.registry.MHFCRegItem;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockWyverniaDefault extends ItemBlock{

	// Default ItemBlock for blocks that are intend to be only 1 stacksize.
	public ItemBlockWyverniaDefault(Block par1) {
		super(par1);
		maxStackSize = 1;
	}
	
	

}
