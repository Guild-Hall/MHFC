package mhfc.heltrato.common.item.materials;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemIngot extends Item {
	private static final String[] itemNames = {MHFCReference.item_ingot0_name,
			MHFCReference.item_ingot1_name, MHFCReference.item_ingot2_name,
			MHFCReference.item_ingot3_name};
	private static final String[] itemIcons = {MHFCReference.item_ingot0_icon,
			MHFCReference.item_ingot1_icon, MHFCReference.item_ingot2_icon,
			MHFCReference.item_ingot3_icon};

	public int meta;

	public ItemIngot(int metaData) {
		super();
		setHasSubtypes(true);
		meta = metaData;
		setUnlocalizedName(itemNames[meta]);
		setCreativeTab(MHFCMain.mhfctabs);

	}

	@Override
	public void registerIcons(IIconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon(itemIcons[meta]);
	}

}
