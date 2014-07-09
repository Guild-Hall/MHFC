package mhfc.net.common.item;

import mhfc.net.MHFCMain;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemWyverniaDust extends Item{

	public ItemWyverniaDust(){
		super();
		setUnlocalizedName("wyverniadust");
		setCreativeTab(MHFCMain.mhfctabs);
	}
	
	public void registerIcons(IIconRegister par1IconRegister){
	        this.itemIcon = par1IconRegister.registerIcon("mhfc:wyverniadust");
	}
}
