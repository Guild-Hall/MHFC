package mhfc.heltrato.common.item;

import mhfc.heltrato.MHFCMain;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemWyverniaClay extends Item{

	public ItemWyverniaClay(){
		super();
		setUnlocalizedName("wyverniaclay");
		setCreativeTab(MHFCMain.mhfctabs);
	}
	
	public void registerIcons(IIconRegister par1IconRegister){
	        this.itemIcon = par1IconRegister.registerIcon("mhfc:wyverniaclay");
	}
}
