package mhfc.net.common.item.block;

import mhfc.net.common.block.BlockWyverniaOreBlock.WyverniaOreBlockSubType;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockWyverniaOreBlock extends ItemBlock {

	public ItemBlockWyverniaOreBlock(Block block) {
		super(block);
		setHasSubtypes(true);
		maxStackSize = 16;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int i = stack.getItemDamage();
		if (i < 0 || i >= WyverniaOreBlockSubType.values().length) {
			i = 0;
		}
		return super.getUnlocalizedName() + "."
				+ WyverniaOreBlockSubType.values()[i].getName();
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

}
