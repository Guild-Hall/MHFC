package mhfc.net.common.item.materials;

import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.item.AbstractSubTypedItem;
import mhfc.net.common.item.ItemColor;
import mhfc.net.common.item.materials.ItemTigrex.TigrexSubType;
import mhfc.net.common.util.Libraries;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemTigrex extends AbstractSubTypedItem<TigrexSubType> {
	public static enum TigrexSubType implements SubTypedItem.SubTypeEnum<Item> {
		SCALE(Libraries.item_tigrex0_name, ItemColor.YELLOW),
		SHELL(Libraries.item_tigrex1_name, ItemColor.YELLOW),
		CLAW(Libraries.item_tigrex2_name, ItemColor.YELLOW),
		FANG(Libraries.item_tigrex3_name, ItemColor.YELLOW),
		SKULLSHELL(Libraries.item_tigrex4_name, ItemColor.YELLOW),
		TAIL(Libraries.item_tigrex5_name, ItemColor.YELLOW);

		public final String name;
		public final ItemColor color;

		private TigrexSubType(String name, ItemColor color) {
			this.name = name;
			this.color = color;
		}

		@Override
		public String getName() {
			return this.name;
		}

		@Override
		public Item getBaseItem() {
			return MHFCItemRegistry.getRegistry().tigrexdrops;
		}

		@Override
		public ItemColor getColor() {
			return color;
		}
	}

	public ItemTigrex() {
		super(TigrexSubType.class);
		setUnlocalizedName(Libraries.item_tigrex_basename);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> list, boolean par4) {
		switch (itemPerk.getSubType(itemStack)) {
		case SKULLSHELL:
		case TAIL:
			list.add("Rare Drop by Tigrex");
			break;
		default:
			list.add("Drop by Tigrex");
		}
	}
}
