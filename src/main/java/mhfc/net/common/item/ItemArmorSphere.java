package mhfc.net.common.item;

import mhfc.net.MHFCMain;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemArmorSphere extends Item{
	
	public int meta;
	
	public ItemArmorSphere(int metaData) {
		super();
		meta = metaData;
		setUnlocalizedName("armorsphere" + meta);
		setCreativeTab(MHFCMain.mhfctabs);
		setMaxStackSize(16);
		
	}
	
	public void registerIcons(IIconRegister par1IconRegister){
        this.itemIcon = par1IconRegister.registerIcon("mhfc:armorsphere" + meta);
}

}
