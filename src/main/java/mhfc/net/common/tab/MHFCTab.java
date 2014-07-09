package mhfc.net.common.tab;

import mhfc.net.common.core.registry.MHFCRegItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MHFCTab extends CreativeTabs{

	public MHFCTab(int par1, String par2Str) {
		super(par1, par2Str);
	}
	
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem()
	{
	return (MHFCRegItem.mhfcitemhkirinspark);
	}
	
	public String getTranslatedTabLabel()
	{
	return "MHFC Tab"; //The name of the tab ingame
	}


}
