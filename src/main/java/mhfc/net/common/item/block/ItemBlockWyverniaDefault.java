package mhfc.net.common.item.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBlockWyverniaDefault extends ItemBlock {

	// Default ItemBlock for blocks that are intend to be only 1 stacksize.
	public ItemBlockWyverniaDefault(Block par1) {
		super(par1);
		maxStackSize = 1;
	}

}
