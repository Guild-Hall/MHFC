package mhfc.net.common.item.materials;

import mhfc.net.MHFCMain;
import mhfc.net.common.util.Libraries;
import net.minecraft.item.Item;

public class ItemLumberBar extends Item {

	public ItemLumberBar() {
		super();
		setUnlocalizedName(Libraries.item_lumberbar_name);
		setCreativeTab(MHFCMain.mhfctabs);
	}

}
