package mhfc.net.common.item.materials;

import mhfc.net.MHFCMain;
import mhfc.net.common.util.Libraries;
import net.minecraft.item.Item;

public class ItemMoldedIron extends Item {

	public ItemMoldedIron() {
		super();
		setUnlocalizedName(Libraries.item_moldediron_name);
		setCreativeTab(MHFCMain.mhfctabs);
	}

}
