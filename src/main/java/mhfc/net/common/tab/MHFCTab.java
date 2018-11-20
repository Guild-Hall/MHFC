package mhfc.net.common.tab;

import mhfc.net.common.core.registry.MHFCItemRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class MHFCTab extends CreativeTabs {

	public MHFCTab(int par1,String label) {
		super(par1,label);
		this.setBackgroundImageName("mhfc.png");
	}
	

	@Override
	public ItemStack createIcon() {
		return new ItemStack(MHFCItemRegistry.getRegistry().weapon_hm_kirinspark);
	}



}
