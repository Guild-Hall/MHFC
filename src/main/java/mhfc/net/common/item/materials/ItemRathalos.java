package mhfc.net.common.item.materials;

import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.item.AbstractSubTypedItem;
import mhfc.net.common.item.ItemColor;
import mhfc.net.common.item.materials.ItemRathalos.RathalosSubType;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemRathalos extends AbstractSubTypedItem<RathalosSubType> {
	public static enum RathalosSubType implements SubTypedItem.SubTypeEnum<Item> {
		SHELL("shell", ResourceInterface.item_rathalos0_name, ItemColor.RED), //
		WEBBING("webbing", ResourceInterface.item_rathalos1_name, ItemColor.RED), //
		MARROW("marrow", ResourceInterface.item_rathalos2_name, ItemColor.RED), //
		WING("wing", ResourceInterface.item_rathalos3_name, ItemColor.RED), //
		PLATE("plate", ResourceInterface.item_rathalos4_name, ItemColor.RED);

		public final String registryName;
		public final String name;
		public final ItemColor color;

		private RathalosSubType(String registryName, String name, ItemColor color) {
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
			return MHFCItemRegistry.getRegistry().rathalosdrops;
		}

		@Override
		public ItemColor getColor() {
			return color;
		}
	}

	public ItemRathalos() {
		super(RathalosSubType.class);
		setUnlocalizedName(ResourceInterface.item_rathalos_basename);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public void addInformation(
			ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer,
			List<String> par3List,
			boolean par4) {
		switch (itemPerk.getSubType(par1ItemStack)) {
		case PLATE:
			par3List.add("Rare Drop by Rathalos");
			break;
		default:
			par3List.add("Drop by Rathalos");
			break;
		}
	}
}
