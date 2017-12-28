package mhfc.net.common.tab;

import mhfc.net.common.core.registry.MHFCBlockRegistry;
import mhfc.net.common.index.ResourceInterface;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MHFCTab extends CreativeTabs {

	public MHFCTab(int par) {
		super(par, ResourceInterface.gui_tab_name);
		this.setBackgroundImageName("mhfc.png");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getTabIconItem() {
		return new ItemStack(MHFCBlockRegistry.getRegistry().mhfcblockgrass);
	}

	@Override
	public String getTranslatedTabLabel() {
		return ResourceInterface.gui_tab_name;
		// The main_name of the tab ingame
	}

}
