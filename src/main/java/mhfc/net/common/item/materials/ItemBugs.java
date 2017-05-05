package mhfc.net.common.item.materials;

import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.item.AbstractSubTypedItem;
import mhfc.net.common.item.ItemColor;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.entity.player.EntityPlayer;
import mhfc.net.common.item.materials.ItemBugs.BugsSubType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBugs extends AbstractSubTypedItem<BugsSubType> {
	public static enum BugsSubType implements SubTypedItem.SubTypeEnum<Item> {
		
		INSECTHUSK("insecthusk", ResourceInterface.item_bug0_name, ItemColor.GRAY);

		public final String registryName;
		public final String name;
		public final ItemColor color;

		private BugsSubType(String registryName, String name, ItemColor color) {
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
			return MHFCItemRegistry.getRegistry().bugdrops;
		}

		@Override
		public ItemColor getColor() {
			return this.color;
		}
	}

	public ItemBugs() {
		super(BugsSubType.class);
		setUnlocalizedName(ResourceInterface.item_kirin_basename);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> list, boolean par4) {
		switch (itemPerk.getSubType(itemStack)) {
		default:
			list.add("Used for crafting materials");
			break;
		}
	}
}
