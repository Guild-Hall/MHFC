package mhfc.net.common.item.materials;

import mhfc.net.MHFCMain;
import mhfc.net.common.util.Libraries;
import net.minecraft.item.Item;

public class ItemSteelBar extends Item {

	public ItemSteelBar() {
		super();
		setUnlocalizedName(Libraries.item_steelbar_name);
		setCreativeTab(MHFCMain.mhfctabs);
	}

}
