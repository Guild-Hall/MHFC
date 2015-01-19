package mhfc.heltrato.common.item.materials;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemWyvernCoin extends Item {

	public ItemWyvernCoin() {
		super();
		setUnlocalizedName(MHFCReference.item_wyverncoin_name);
		setCreativeTab(MHFCMain.mhfctabs);
		maxStackSize = 5;
	}

	@Override
	public void registerIcons(IIconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon(MHFCReference.item_wyverncoin_icon);
	}
}
