package mhfc.net.common.item.materials;

import mhfc.net.MHFCMain;
import mhfc.net.common.index.ResourceInterface;
import net.minecraft.item.Item;

public class ItemGaguaEgg extends Item {

	public ItemGaguaEgg() {
		super();
		setTranslationKey(ResourceInterface.item_gaguaegg_name);
		setCreativeTab(MHFCMain.mhfctabs);
	}
}
