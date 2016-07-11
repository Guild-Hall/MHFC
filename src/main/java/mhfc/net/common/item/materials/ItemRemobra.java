package mhfc.net.common.item.materials;

import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.item.AbstractSubTypedItem;
import mhfc.net.common.item.ItemColor;
import mhfc.net.common.item.materials.ItemRemobra.RemobraSubType;
import mhfc.net.common.util.SubTypedItem;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemRemobra extends AbstractSubTypedItem<RemobraSubType> {
	public static enum RemobraSubType implements SubTypedItem.SubTypeEnum<Item> {
		SKIN(MHFCReference.item_remobra0_name, MHFCReference.base_monster_pelt, ItemColor.GRAY), //
		STRIPE(MHFCReference.item_remobra1_name, MHFCReference.base_monster_pelt, ItemColor.GRAY), //
		SKULL(MHFCReference.item_remobra2_name, MHFCReference.base_monster_part, ItemColor.GRAY), //
		WING(MHFCReference.item_remobra3_name, MHFCReference.base_monster_part, ItemColor.GRAY),;//

		public final String name;
		public final String texture;
		public final ItemColor color;

		private RemobraSubType(String name, String texture, ItemColor color) {
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
			return MHFCItemRegistry.getRegistry().remobradrops;
		}

		@Override
		public ItemColor getColor() {
			return this.color;
		}
	}

	public ItemRemobra() {
		super(RemobraSubType.class);
		setUnlocalizedName(MHFCReference.item_remobra_basename);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		switch (itemPerk.getSubType(par1ItemStack)) {
		case WING:
			par3List.add("Rare Drop by Remobra");
			break;
		default:
			par3List.add("Drop by Remobra");
			break;
		}
	}
}
