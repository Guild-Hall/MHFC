package mhfc.net.common.item.materials;

import mhfc.net.MHFCMain;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.item.Item;

public class ItemLumberBar extends Item {

	public ItemLumberBar() {
		super();
		setUnlocalizedName(MHFCReference.item_lumberbar_name);
		setCreativeTab(MHFCMain.mhfctabs);
	}

}
