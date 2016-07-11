package mhfc.net.common.item.materials;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.item.AbstractSubTypedItem;
import mhfc.net.common.item.materials.ItemBase.BaseSubType;
import mhfc.net.common.util.SubTypedItem;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.item.Item;

public class ItemBase extends AbstractSubTypedItem<BaseSubType> {
	public static enum BaseSubType implements SubTypedItem.SubTypeEnum<Item> {
		ANUMIUM(MHFCReference.item_base0_name, MHFCReference.item_base0_icon), //
		MEGANUM(MHFCReference.item_base1_name, MHFCReference.item_base1_icon);

		public final String name;
		public final String texture;

		private BaseSubType(String name, String texture) {
			this.name = name;
			this.texture = texture;
		}

		@Override
		public String getName() {
			return this.name;
		}

		@Override
		public String getTexPath() {
			return this.texture;
		}

		@Override
		public Item getBaseItem() {
			return MHFCItemRegistry.getRegistry().base;
		}
	}

	public ItemBase() {
		super(BaseSubType.class);
		setUnlocalizedName(MHFCReference.item_base_basename);
		setCreativeTab(MHFCMain.mhfctabs);
		setMaxStackSize(5);
		setHasSubtypes(true);
	}
}
