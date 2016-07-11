package mhfc.net.common.item.materials;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.item.AbstractSubTypedItem;
import mhfc.net.common.item.ItemColor;
import mhfc.net.common.item.materials.ItemIngot.IngotsSubType;
import mhfc.net.common.util.SubTypedItem;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.item.Item;

public class ItemIngot extends AbstractSubTypedItem<IngotsSubType> {
	public static enum IngotsSubType implements SubTypedItem.SubTypeEnum<Item> {
		CARBALITE(MHFCReference.item_ingot0_name, ItemColor.PURPLE), //
		DRAGONITE(MHFCReference.item_ingot1_name, ItemColor.GREEN), //
		ELTALITE(MHFCReference.item_ingot2_name, ItemColor.RED), //
		MACHALITE(MHFCReference.item_ingot3_name, ItemColor.CYAN);

		public final String name;
		public final String texture;
		public final ItemColor color;

		private IngotsSubType(String name, ItemColor color) {
			this.name = name;
			this.texture = MHFCReference.base_misc_ore;
			this.color = color;
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
			return MHFCItemRegistry.getRegistry().ingot;
		}

		@Override
		public ItemColor getColor() {
			return color;
		}
	}

	public ItemIngot() {
		super(IngotsSubType.class);
		setUnlocalizedName(MHFCReference.item_ingot_basename);
		setCreativeTab(MHFCMain.mhfctabs);
	}
}
