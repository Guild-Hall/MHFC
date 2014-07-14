package mhfc.heltrato.common.item;

import mhfc.heltrato.MHFCMain;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemBase extends Item{
	
	public int meta;
	
	public ItemBase(int metaData) {
		super();
		meta = metaData;
		setUnlocalizedName("base" + meta);
		setCreativeTab(MHFCMain.mhfctabs);
		setMaxStackSize(5);
	}
	
	public void registerIcons(IIconRegister par1IconRegister){
        this.itemIcon = par1IconRegister.registerIcon("mhfc:base" + meta);
}

}
