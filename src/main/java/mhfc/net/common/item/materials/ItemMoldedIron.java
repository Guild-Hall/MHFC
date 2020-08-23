package mhfc.net.common.item.materials;

import mhfc.net.MHFCMain;
import mhfc.net.common.index.ResourceInterface;
import net.minecraft.item.Item;

public class ItemMoldedIron extends Item {

	public ItemMoldedIron() {
		super();
		setUnlocalizedName(ResourceInterface.item_moldediron_name);
		setCreativeTab(MHFCMain.mhfctabs);
	}

}
