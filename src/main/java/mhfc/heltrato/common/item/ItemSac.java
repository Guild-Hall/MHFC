package mhfc.heltrato.common.item;

import java.util.List;

import mhfc.heltrato.MHFCMain;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemSac extends Item {
	
	public int meta;
	
	public ItemSac(int metaData) {
		super();
		setHasSubtypes(true);
		meta = metaData;
		setUnlocalizedName("sac" + meta);
		setCreativeTab(MHFCMain.mhfctabs);
	}
	
	public void registerIcons(IIconRegister par1IconRegister){
        this.itemIcon = par1IconRegister.registerIcon("mhfc:sac" + meta);
}
	
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4){
		
	}

}
