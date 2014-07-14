package mhfc.heltrato.common.item;

import mhfc.heltrato.MHFCMain;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemMoldedIron extends Item{

	public ItemMoldedIron(){
		super();
		setUnlocalizedName("moldediron");
		setCreativeTab(MHFCMain.mhfctabs);
	}
	
	public void registerIcons(IIconRegister par1IconRegister){
	        this.itemIcon = par1IconRegister.registerIcon("mhfc:moldediron");
	}
}
