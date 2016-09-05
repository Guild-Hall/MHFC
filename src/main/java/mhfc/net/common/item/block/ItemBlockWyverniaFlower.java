package mhfc.net.common.item.block;

import mhfc.net.common.block.BlockWyverniaFlower.WyverniaFlowerSubType;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockWyverniaFlower extends ItemBlock {

	public ItemBlockWyverniaFlower(Block block) {
		super(block);
		setMaxDamage(0);
		setHasSubtypes(true);
		maxStackSize = 4;

	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int i = stack.getItemDamage();
		if (i < 0 || i >= WyverniaFlowerSubType.values().length) {
			i = 0;
		}

		return super.getUnlocalizedName() + "." + WyverniaFlowerSubType.values()[i].name;
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D() {
		return true;
	}

}
