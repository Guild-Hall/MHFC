package mhfc.net.common.item.materials;

import mhfc.net.MHFCMain;
import mhfc.net.common.index.ResourceInterface;
import net.minecraft.item.Item;

public class ItemSteelBar extends Item {

	public ItemSteelBar() {
		super();
		setUnlocalizedName(ResourceInterface.item_steelbar_name);
		setCreativeTab(MHFCMain.mhfctabs);
	}

}
