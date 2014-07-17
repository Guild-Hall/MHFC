package mhfc.net.common.item;

import mhfc.net.MHFCMain;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemBase extends Item {
	private static final String[] itemNames = {MHFCReference.item_base0_name,
			MHFCReference.item_base1_name};
	private static final String[] itemIcons = {MHFCReference.item_base0_icon,
			MHFCReference.item_base1_icon};

	public int meta;

	public ItemBase(int metaData) {
		super();
		meta = metaData;
		setUnlocalizedName(itemNames[meta]);
		setCreativeTab(MHFCMain.mhfctabs);
		setMaxStackSize(5);
	}

	@Override
	public void registerIcons(IIconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon(itemIcons[meta]);
	}

}
