package mhfc.net.common.item.materials;

import mhfc.net.MHFCMain;
import mhfc.net.common.util.Libraries;
import net.minecraft.item.Item;

public class ItemWoodRig extends Item {

	public ItemWoodRig() {
		super();
		setUnlocalizedName(Libraries.item_woodrig_name);
		setCreativeTab(MHFCMain.mhfctabs);
	}

}
