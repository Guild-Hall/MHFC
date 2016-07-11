package mhfc.net.common.item.materials;

import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.item.AbstractSubTypedItem;
import mhfc.net.common.item.ItemColor;
import mhfc.net.common.item.materials.ItemRathalos.RathalosSubType;
import mhfc.net.common.util.SubTypedItem;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemRathalos extends AbstractSubTypedItem<RathalosSubType> {
	public static enum RathalosSubType implements SubTypedItem.SubTypeEnum<Item> {
		SHELL(MHFCReference.item_rathalos0_name, MHFCReference.base_monster_carapace, ItemColor.RED), //
		WEBBING(MHFCReference.item_rathalos1_name, MHFCReference.base_monster_webbing, ItemColor.RED), //
		MARROW(MHFCReference.item_rathalos2_name, MHFCReference.base_monster_part, ItemColor.RED), //
		WING(MHFCReference.item_rathalos3_name, MHFCReference.base_monster_part, ItemColor.RED), //
		PLATE(MHFCReference.item_rathalos4_name, MHFCReference.base_monster_scale, ItemColor.RED);

		public final String name;
		public final String texture;
		public final ItemColor color;

		private RathalosSubType(String name, String texture, ItemColor color) {
			this.name = name;
			this.texture = texture;
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
			return MHFCItemRegistry.getRegistry().rathalosdrops;
		}

		@Override
		public ItemColor getColor() {
			return color;
		}
	}

	public ItemRathalos() {
		super(RathalosSubType.class);
		setUnlocalizedName(MHFCReference.item_rathalos_basename);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
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
