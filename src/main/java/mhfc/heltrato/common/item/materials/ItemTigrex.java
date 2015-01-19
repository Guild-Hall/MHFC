package mhfc.heltrato.common.item.materials;

import java.util.List;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemTigrex extends Item {
	private static final String[] itemNames = {MHFCReference.item_tigrex0_name,
			MHFCReference.item_tigrex1_name, MHFCReference.item_tigrex2_name,
			MHFCReference.item_tigrex3_name, MHFCReference.item_tigrex4_name,
			MHFCReference.item_tigrex5_name};
	private static final String[] itemIcons = {MHFCReference.item_tigrex_icon0,
			MHFCReference.item_tigrex_icon1, MHFCReference.item_tigrex_icon2,
			MHFCReference.item_tigrex_icon3, MHFCReference.item_tigrex_icon4,
			MHFCReference.item_tigrex_icon5};

	public int meta;

	public ItemTigrex(int metaData) {
		super();
		meta = metaData;
		setHasSubtypes(true);
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
			par3List.add("Rare Drop by Tigrex");
		} else {
			par3List.add("Drop by Tigrex");
		}

	}

}
