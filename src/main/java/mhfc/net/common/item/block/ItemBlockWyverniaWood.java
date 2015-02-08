package mhfc.net.common.item.block;

import mhfc.net.common.block.BlockWyverniaWood.WyverniaLogSubType;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockWyverniaWood extends ItemBlock {

	public ItemBlockWyverniaWood(Block block) {
		super(block);
		setHasSubtypes(true);

	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int i = stack.getItemDamage();
		if (i < 0 || i >= WyverniaLogSubType.values().length) {
			i = 0;
		}

		return super.getUnlocalizedName() + "."
				+ WyverniaLogSubType.values()[i].getName();
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

}
