package mhfc.net.common.item.materials;

import mhfc.net.MHFCMain;
import mhfc.net.common.index.ResourceInterface;
import net.minecraft.item.Item;

public class ItemLumberBar extends Item {

	public ItemLumberBar() {
		super();
		setTranslationKey(ResourceInterface.item_lumberbar_name);
		setCreativeTab(MHFCMain.mhfctabs);
	}

}
