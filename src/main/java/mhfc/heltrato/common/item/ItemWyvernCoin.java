package mhfc.heltrato.common.item;

import mhfc.heltrato.MHFCMain;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemWyvernCoin extends Item{

	public ItemWyvernCoin(){
		super();
		setUnlocalizedName("wyverncoin");
		setCreativeTab(MHFCMain.mhfctabs);
		maxStackSize = 5;
	}
	
	public void registerIcons(IIconRegister par1IconRegister){
	        this.itemIcon = par1IconRegister.registerIcon("mhfc:wyverncoin");
	}
}
