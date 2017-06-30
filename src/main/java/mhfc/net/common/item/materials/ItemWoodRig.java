package mhfc.net.common.item.materials;

import mhfc.net.MHFCMain;
import mhfc.net.common.index.ResourceInterface;
import net.minecraft.item.Item;

public class ItemWoodRig extends Item {

	public ItemWoodRig() {
		super();
		setUnlocalizedName(ResourceInterface.item_woodrig_name);
		setCreativeTab(MHFCMain.mhfctabs);
	}

}
