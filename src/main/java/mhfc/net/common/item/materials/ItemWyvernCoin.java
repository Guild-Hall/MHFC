package mhfc.net.common.item.materials;

import mhfc.net.MHFCMain;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.item.IItemColored;
import mhfc.net.common.item.ItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemWyvernCoin extends Item implements IItemColored {

	public ItemWyvernCoin() {
		super();
		setUnlocalizedName(ResourceInterface.item_wyverncoin_name);
		setCreativeTab(MHFCMain.mhfctabs);
		maxStackSize = 5;
	}

	@Override
	public int getColorFromItemStack(ItemStack stack, int renderLayer) {
		return ItemColor.LIBLUE.getRGB();
	}
}
