package mhfc.heltrato.common.item.materials;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemSteelBar extends Item {

	public ItemSteelBar() {
		super();
		setUnlocalizedName(MHFCReference.item_steelbar_name);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public void registerIcons(IIconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister
				.registerIcon(MHFCReference.item_steelbar_icon);
	}
}
