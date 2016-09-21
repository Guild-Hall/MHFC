package mhfc.net.common.item.materials;

import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.item.AbstractSubTypedItem;
import mhfc.net.common.item.ItemColor;
import mhfc.net.common.item.materials.ItemKirin.KirinSubType;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemKirin extends AbstractSubTypedItem<KirinSubType> {
	public static enum KirinSubType implements SubTypedItem.SubTypeEnum<Item> {
		MANE(ResourceInterface.item_kirin0_name, ItemColor.GRAY),
		GEM(ResourceInterface.item_kirin1_name, ItemColor.WHITE),
		THUNDERTAIL(ResourceInterface.item_kirin2_name, ItemColor.WHITE),
		LIGHTCRYSTAL(ResourceInterface.item_kirin3_name, ItemColor.GRAY),
		PURECRYSTAL(ResourceInterface.item_kirin4_name, ItemColor.WHITE),
		PLATINUMMANE(ResourceInterface.item_kirin5_name, ItemColor.WHITE);

		public final String name;
		public final ItemColor color;

		private KirinSubType(String name, ItemColor color) {
			this.name = name;
			this.color = color;
		}

		@Override
		public String getName() {
			return this.name;
		}

		@Override
		public Item getBaseItem() {
			return MHFCItemRegistry.getRegistry().kirindrops;
		}

		@Override
		public ItemColor getColor() {
			return this.color;
		}
	}

	public ItemKirin() {
		super(KirinSubType.class);
		setUnlocalizedName(ResourceInterface.item_kirin_basename);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> list, boolean par4) {
		switch (itemPerk.getSubType(itemStack)) {
		case PURECRYSTAL:
		case PLATINUMMANE:
			list.add("Rare Drop by Kirin");
			break;
		default:
			list.add("Drop by Kirin");
			break;
		}
	}
}
