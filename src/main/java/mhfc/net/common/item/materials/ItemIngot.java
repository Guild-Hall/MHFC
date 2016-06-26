package mhfc.net.common.item.materials;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.item.AbstractSubTypedItem;
import mhfc.net.common.item.materials.ItemIngot.IngotsSubType;
import mhfc.net.common.util.SubTypedItem;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.item.Item;

public class ItemIngot extends AbstractSubTypedItem<IngotsSubType> {
	public static enum IngotsSubType implements SubTypedItem.SubTypeEnum<Item> {
		CARBALITE(MHFCReference.item_ingot0_name, MHFCReference.item_ingot0_icon), //
		DRAGONITE(MHFCReference.item_ingot1_name, MHFCReference.item_ingot1_icon), //
		ELTALITE(MHFCReference.item_ingot2_name, MHFCReference.item_ingot2_icon), //
		MACHALITE(MHFCReference.item_ingot3_name, MHFCReference.item_ingot3_icon);

		public final String name;
		public final String texture;

		private IngotsSubType(String name, String texture) {
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
			return MHFCItemRegistry.mhfcitemingot;
		}
	}

	public ItemIngot() {
		super(IngotsSubType.class);
		setUnlocalizedName(MHFCReference.item_ingot_basename);
		setCreativeTab(MHFCMain.mhfctabs);
	}
}
