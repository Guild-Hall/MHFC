package mhfc.net.common.item.materials;

import mhfc.net.MHFCMain;
import mhfc.net.common.index.ResourceInterface;
import net.minecraft.item.Item;

public class ItemWyverniaDust extends Item {

	public ItemWyverniaDust() {
		super();
		setUnlocalizedName(ResourceInterface.item_wyverniadust_name);
		setCreativeTab(MHFCMain.mhfctabs);
	}

}
