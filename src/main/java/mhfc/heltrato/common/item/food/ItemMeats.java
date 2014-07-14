package mhfc.heltrato.common.item.food;

import mhfc.heltrato.MHFCMain;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemFood;

public class ItemMeats extends ItemFood {

	public int metaData;
	
	public ItemMeats(int healAmount, float modifier, boolean p_i45339_3_, int meta) {
		super(healAmount, modifier, p_i45339_3_);
		metaData = meta;
		setUnlocalizedName("meat" + metaData);
		setCreativeTab(MHFCMain.mhfctabs);
		setMaxStackSize(1);
	}
	
	public void registerIcons(IIconRegister par1IconRegister){
        this.itemIcon = par1IconRegister.registerIcon("mhfc:meat" + metaData);
	}
	

}
