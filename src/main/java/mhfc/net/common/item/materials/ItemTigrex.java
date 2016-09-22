package mhfc.net.common.item.materials;

import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.item.AbstractSubTypedItem;
import mhfc.net.common.item.ItemColor;
import mhfc.net.common.item.materials.ItemTigrex.TigrexSubType;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemTigrex extends AbstractSubTypedItem<TigrexSubType> {
	public static enum TigrexSubType implements SubTypedItem.SubTypeEnum<Item> {
		SCALE("scale", ResourceInterface.item_tigrex0_name, ItemColor.YELLOW),
		SHELL("shell", ResourceInterface.item_tigrex1_name, ItemColor.YELLOW),
		CLAW("claw", ResourceInterface.item_tigrex2_name, ItemColor.YELLOW),
		FANG("fang", ResourceInterface.item_tigrex3_name, ItemColor.YELLOW),
		SKULLSHELL("skullshell", ResourceInterface.item_tigrex4_name, ItemColor.YELLOW),
		TAIL("tail", ResourceInterface.item_tigrex5_name, ItemColor.YELLOW);

		public final String registryName;
		public final String name;
		public final ItemColor color;

		private TigrexSubType(String registryName, String name, ItemColor color) {
			this.registryName = registryName;
			this.name = name;
			this.color = color;
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
			return MHFCItemRegistry.getRegistry().tigrexdrops;
		}

		@Override
		public ItemColor getColor() {
			return color;
		}
	}

	public ItemTigrex() {
		super(TigrexSubType.class);
		setUnlocalizedName(ResourceInterface.item_tigrex_basename);
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
