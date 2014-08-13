package mhfc.heltrato.common.item;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemWyverniaClay extends Item {

	public ItemWyverniaClay() {
		super();
		setUnlocalizedName(MHFCReference.item_wyverniaclay_name);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public void registerIcons(IIconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister
				.registerIcon(MHFCReference.item_wyverniaclay_icon);
	}
}
