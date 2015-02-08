package mhfc.net.common.item.block;

import mhfc.net.common.block.BlockWyverniaPlank.WyverniaPlankSubType;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockWyverniaPlank extends ItemBlock {

	public ItemBlockWyverniaPlank(Block block) {
		super(block);
		setHasSubtypes(true);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int i = stack.getItemDamage();
		if (i < 0 || i >= WyverniaPlankSubType.values().length) {
			i = 0;
		}

		return super.getUnlocalizedName() + "."
				+ WyverniaPlankSubType.values()[i].getName();
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

}
