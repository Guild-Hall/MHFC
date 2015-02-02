package mhfc.net.common.item.materials;

import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemKirin extends Item {
	private static final String[] itemNames = {MHFCReference.item_kirin0_name,
			MHFCReference.item_kirin1_name, MHFCReference.item_kirin2_name,
			MHFCReference.item_kirin3_name, MHFCReference.item_kirin4_name,
			MHFCReference.item_kirin5_name};
	private static final String[] itemIcons = {MHFCReference.item_kirin_icon0,
			MHFCReference.item_kirin_icon1, MHFCReference.item_kirin_icon2,
			MHFCReference.item_kirin_icon3, MHFCReference.item_kirin_icon4,
			MHFCReference.item_kirin_icon5};

	public int meta;

	public ItemKirin(int metaData) {
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
			par3List.add("Rare Drop by Kirin");
		} else {
			par3List.add("Drop by Kirin");
		}

	}

}
