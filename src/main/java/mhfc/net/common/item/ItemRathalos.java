package mhfc.net.common.item;

import java.util.List;

import mhfc.net.MHFCMain;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemRathalos extends Item {

	public int meta;

	public ItemRathalos(int metaData) {
		super();
		setHasSubtypes(true);
		meta = metaData;
		setUnlocalizedName("rathalos" + meta);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public void registerIcons(IIconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon("mhfc:rathalos" + meta);
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
