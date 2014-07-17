package mhfc.net.common.item;

import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemSac extends Item {
	public static final String[] itemNames = {MHFCReference.item_sac0_name};
	public static final String[] itemIcons = {MHFCReference.item_sac0_icon};

	public int meta;

	public ItemSac(int metaData) {
		super();
		setHasSubtypes(true);
		meta = metaData;
		setUnlocalizedName(itemNames[meta]);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public void registerIcons(IIconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon(itemNames[meta]);
	}

	@Override
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer,
			@SuppressWarnings("rawtypes") List par3List, boolean par4) {

	}

}
