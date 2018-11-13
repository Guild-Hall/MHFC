package mhfc.net.common.item.materials;

import mhfc.net.MHFCMain;
import mhfc.net.common.index.ResourceInterface;
import net.minecraft.item.Item;

public class ItemWyverniaClay extends Item {

	public ItemWyverniaClay() {
		super();
		setTranslationKey(ResourceInterface.item_wyverniaclay_name);
		setCreativeTab(MHFCMain.mhfctabs);
	}

}
