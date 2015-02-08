package mhfc.net.common.item.block;

import mhfc.net.common.block.BlockWyverniaRock.WyverniaRockSubType;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockWyverniaRock extends ItemBlock {

	public ItemBlockWyverniaRock(Block block) {
		super(block);
		setHasSubtypes(true);
		maxStackSize = 20;

	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int i = stack.getItemDamage();
		if (i < 0 || i >= WyverniaRockSubType.values().length) {
			i = 0;
		}

		return super.getUnlocalizedName() + "."
				+ WyverniaRockSubType.values()[i].getName();
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

}
