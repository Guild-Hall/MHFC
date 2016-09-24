package mhfc.net.common.item.materials;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.item.AbstractSubTypedItem;
import mhfc.net.common.item.materials.ItemBase.BaseSubType;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.item.Item;

public class ItemBase extends AbstractSubTypedItem<BaseSubType> {
	public static enum BaseSubType implements SubTypedItem.SubTypeEnum<Item> {
		ANUMIUM("anumium", ResourceInterface.item_base0_name),
		MEGANUM("meganum", ResourceInterface.item_base1_name);

		public final String registryName;
		public final String name;

		private BaseSubType(String registryName, String name) {
			this.registryName = registryName;
			this.name = name;
		}

		@Override
		public String getName() {
			return this.registryName;
		}

		@Override
		public String getUnlocalizedName() {
			return this.name;
		}

		@Override
		public Item getBaseItem() {
			return MHFCItemRegistry.getRegistry().base;
		}
	}

	public ItemBase() {
		super(BaseSubType.class);
		setUnlocalizedName(ResourceInterface.item_base_basename);
		setCreativeTab(MHFCMain.mhfctabs);
		setMaxStackSize(5);
		setHasSubtypes(true);
	}
}
