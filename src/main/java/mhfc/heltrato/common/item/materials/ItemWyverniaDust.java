package mhfc.heltrato.common.item.materials;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemWyverniaDust extends Item {

	public ItemWyverniaDust() {
		super();
		setUnlocalizedName(MHFCReference.item_wyverniadust_name);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public void registerIcons(IIconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister
				.registerIcon(MHFCReference.item_wyverniadust_icon);
	}
}
