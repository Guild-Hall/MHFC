package mhfc.net.common.item.materials;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.item.AbstractSubTypedItem;
import mhfc.net.common.item.ItemColor;
import mhfc.net.common.item.materials.ItemArmorSphere.ArmorSphereSubType;
import mhfc.net.common.util.Libraries;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.item.Item;

public class ItemArmorSphere extends AbstractSubTypedItem<ArmorSphereSubType> {
	public static enum ArmorSphereSubType implements SubTypedItem.SubTypeEnum<Item> {
		NORMAL(Libraries.item_armorsphere0_name, ItemColor.BLUE),
		PLUS(Libraries.item_armorsphere1_name, ItemColor.LIME);

		public final String name;
		public final ItemColor color;

		private ArmorSphereSubType(String name, ItemColor color) {
			this.name = name;
			this.color = color;
		}

		@Override
		public String getName() {
			return this.name;
		}

		@Override
		public Item getBaseItem() {
			return MHFCItemRegistry.getRegistry().armorsphere;
		}

		@Override
		public ItemColor getColor() {
			return this.color;
		}
	}

	public ItemArmorSphere() {
		super(ArmorSphereSubType.class);
		setUnlocalizedName(Libraries.item_armorsphere_basename);
		setCreativeTab(MHFCMain.mhfctabs);
		setMaxStackSize(16);
	}
}
