package mhfc.net.common.item.materials;

import mhfc.net.MHFCMain;
import mhfc.net.common.item.ItemColor;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemWyvernCoin extends Item {

	public ItemWyvernCoin() {
		super();
		setUnlocalizedName(MHFCReference.item_wyverncoin_name);
		setCreativeTab(MHFCMain.mhfctabs);
		maxStackSize = 5;
	}

	@Override
	public void registerIcons(IIconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon(MHFCReference.base_misc_token);
	}


	@Override
	public int getColorFromItemStack(ItemStack stack, int renderLayer) {
		return ItemColor.LIBLUE.getRGB();
	}
}
