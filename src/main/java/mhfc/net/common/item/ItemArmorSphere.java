package mhfc.net.common.item;

import mhfc.net.MHFCMain;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemArmorSphere extends Item {
	private static final String[] itemNames = {
			MHFCReference.item_armorsphere0_name,
			MHFCReference.item_armorsphere1_name};
	private static final String[] itemIcons = {
			MHFCReference.item_armorsphere_icon0,
			MHFCReference.item_armorsphere_icon1};

	public int meta;

	public ItemArmorSphere(int metaData) {
		super();
		meta = metaData;
		setUnlocalizedName(itemNames[meta]);
		setCreativeTab(MHFCMain.mhfctabs);
		setMaxStackSize(16);

	}

	@Override
	public void registerIcons(IIconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon(itemIcons[meta]);
	}

}
