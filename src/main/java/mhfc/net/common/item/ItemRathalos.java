package mhfc.net.common.item;

import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemRathalos extends Item {
	private static final String[] itemNames = {
			MHFCReference.item_rathalos0_name,
			MHFCReference.item_rathalos1_name,
			MHFCReference.item_rathalos2_name,
			MHFCReference.item_rathalos3_name,
			MHFCReference.item_rathalos4_name};
	private static final String[] itemIcons = {
			MHFCReference.item_rathalos_icon0,
			MHFCReference.item_rathalos_icon1,
			MHFCReference.item_rathalos_icon2,
			MHFCReference.item_rathalos_icon3,
			MHFCReference.item_rathalos_icon4};

	public int meta;

	public ItemRathalos(int metaData) {
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

	@Override
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer,
			@SuppressWarnings("rawtypes") List par3List, boolean par4) {
		if (meta > 3) {
			par3List.add("Rare Drop by Rathalos");
		} else {
			par3List.add("Drop by Rathalos");
		}

	}

}
