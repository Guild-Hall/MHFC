package mhfc.net.common.tab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class MHFCTab extends CreativeTabs {

	public MHFCTab(int par) {
		super(par, MHFCReference.gui_tab_name);
		this.setBackgroundImageName("mhfc.png");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return MHFCItemRegistry.getRegistry().weapon_hm_kirinspark;
	}

	@Override
	public String getTranslatedTabLabel() {
		return MHFCReference.gui_tab_name;
		// The main_name of the tab ingame
	}

}
