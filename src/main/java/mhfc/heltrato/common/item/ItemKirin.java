package mhfc.heltrato.common.item;

import java.util.List;

import mhfc.heltrato.MHFCMain;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemKirin extends Item {
	
	public int meta;
	
	public ItemKirin(int metaData) {
		super();
		setHasSubtypes(true);
		meta = metaData;
		setUnlocalizedName("kirin" + meta);
		setCreativeTab(MHFCMain.mhfctabs);
	}
	
	public void registerIcons(IIconRegister par1IconRegister){
        this.itemIcon = par1IconRegister.registerIcon("mhfc:kirin" + meta);
}
	
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4){
		if(meta > 3){
			par3List.add("Rare Drop by Kirin");
		}else{
			par3List.add("Drop by Kirin");
		}
		
	}

}
