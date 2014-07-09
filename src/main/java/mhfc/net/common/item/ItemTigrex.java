package mhfc.net.common.item;

import java.util.List;

import mhfc.net.MHFCMain;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemTigrex extends Item {
	
	public int meta;
	
	public ItemTigrex(int metaData) {
		super();
		meta = metaData;
		setHasSubtypes(true);
		setUnlocalizedName("tigrex" + meta);
		setCreativeTab(MHFCMain.mhfctabs);
	}
	
	public void registerIcons(IIconRegister par1IconRegister){
        this.itemIcon = par1IconRegister.registerIcon("mhfc:tigrex" + meta);
}
	
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4){
		if(meta > 3){
			par3List.add("Rare Drop by Tigrex");
		}else{
			par3List.add("Drop by Tigrex");
		}
		
	}
	

}
