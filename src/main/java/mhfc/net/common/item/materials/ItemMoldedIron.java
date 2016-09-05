package mhfc.net.common.item.materials;

import mhfc.net.MHFCMain;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.item.Item;

public class ItemMoldedIron extends Item {

	public ItemMoldedIron() {
		super();
		setUnlocalizedName(MHFCReference.item_moldediron_name);
		setCreativeTab(MHFCMain.mhfctabs);
	}

}
