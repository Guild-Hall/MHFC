package mhfc.net.common.item.materials;

import mhfc.net.MHFCMain;
import mhfc.net.common.util.Libraries;
import net.minecraft.item.Item;

public class ItemGaguaEgg extends Item {

	public ItemGaguaEgg() {
		super();
		setUnlocalizedName(Libraries.item_gaguaegg_name);
		setCreativeTab(MHFCMain.mhfctabs);
	}
}
