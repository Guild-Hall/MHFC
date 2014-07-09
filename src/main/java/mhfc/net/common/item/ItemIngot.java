package mhfc.net.common.item;

import mhfc.net.MHFCMain;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemIngot extends Item{
	
	public int meta;
	
	public ItemIngot(int metaData) {
		super();
		setHasSubtypes(true);
		meta = metaData;
		setUnlocalizedName("ingot" + meta);
		setCreativeTab(MHFCMain.mhfctabs);
		
	}
	
	public void registerIcons(IIconRegister par1IconRegister){
        this.itemIcon = par1IconRegister.registerIcon("mhfc:ingot" + meta);
}

}
