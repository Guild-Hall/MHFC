package mhfc.net.common.item.block;

import mhfc.net.common.block.BlockWyverniaOres.WyverniaOreSubType;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockWyverniaOres extends ItemBlock {

	public ItemBlockWyverniaOres(Block block) {
		super(block);
		setHasSubtypes(true);
		maxStackSize = 12;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int i = stack.getItemDamage();
		if (i < 0 || i >= WyverniaOreSubType.values().length) {
			i = 0;
		}
		return super.getUnlocalizedName() + "."
				+ WyverniaOreSubType.values()[i].getName();
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

}
