package mhfc.net.common.item.materials;

import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.item.AbstractSubTypedItem;
import mhfc.net.common.item.ItemColor;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.entity.player.EntityPlayer;
import mhfc.net.common.item.materials.ItemBug.BugSubType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBug extends AbstractSubTypedItem<BugSubType> {
	public static enum BugSubType implements SubTypedItem.SubTypeEnum<Item> {
		
		INSECTHUSK("insecthusk", ResourceInterface.item_bug0_name, ItemColor.GRAY),
		YAMBUG("yambug", ResourceInterface.item_bug1_name, ItemColor.BLUE),
		BUGHOPPER("bughopper", ResourceInterface.item_bug2_name, ItemColor.WHITE),
		SNAKEBEELARVA("snakebeelarva", ResourceInterface.item_bug3_name, ItemColor.GREEN),
		GODBUG("godbug", ResourceInterface.item_bug4_name, ItemColor.WHITE),
		BITTERBUG("bitterbug", ResourceInterface.item_bug5_name, ItemColor.WHITE),
		FLASHBUG("flashbug", ResourceInterface.item_bug6_name, ItemColor.CYAN),
		THUNDERBUG("thunderbug", ResourceInterface.item_bug7_name, ItemColor.YELLOW),
		CARPENTERBUG("carpenterbug", ResourceInterface.item_bug8_name, ItemColor.YELLOW),
		KINGSCARAB("kingscarab", ResourceInterface.item_bug9_name, ItemColor.PINK),
		EMPERORCRICKET("emperorcricket", ResourceInterface.item_bug10_name, ItemColor.BLUE),
		KILLERBEETLE("killerbeetle", ResourceInterface.item_bug11_name, ItemColor.BROWN),
		HERCUDROME("hercudrome", ResourceInterface.item_bug12_name, ItemColor.GREEN),
		RARESCARAB("rarescarab", ResourceInterface.item_bug13_name, ItemColor.RED),
		PHANTOMBUTTERFLY("phantombutterfly", ResourceInterface.item_bug14_name, ItemColor.WHITE),
		RAINBOWINSECT("rainbowinsect", ResourceInterface.item_bug15_name, ItemColor.CYAN),
		GREATHORNFLY("greathornfly", ResourceInterface.item_bug16_name, ItemColor.YELLOW),
		GREATLADYBUG("greatladybug", ResourceInterface.item_bug17_name, ItemColor.GRAY),
		ROYALRHINO("royalrhino", ResourceInterface.item_bug18_name, ItemColor.GREEN),
		DIVINERHINO("divinerhino", ResourceInterface.item_bug19_name, ItemColor.GREEN),
		GLUEHOPPER("gluehopper", ResourceInterface.item_bug20_name, ItemColor.CYAN);
		

		public final String registryName;
		public final String name;
		public final ItemColor color;

		private BugSubType(String registryName, String name, ItemColor color) {
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
			return MHFCItemRegistry.getRegistry().bug;
		}

		@Override
		public ItemColor getColor() {
			return this.color;
		}
	}

	public ItemBug() {
		super(BugSubType.class);
		setUnlocalizedName(ResourceInterface.item_bug_basename);
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
