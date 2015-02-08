package mhfc.net.common.item.materials;

import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.util.SubTypedItem;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemIngot extends Item {
	public static enum IngotsSubType implements SubTypedItem.SubTypeEnum<Item> {
		CARBALITE(MHFCReference.item_ingot0_name,
				MHFCReference.item_ingot0_icon), //
		DRAGONITE(MHFCReference.item_ingot1_name,
				MHFCReference.item_ingot1_icon), //
		ELTALITE(MHFCReference.item_ingot2_name, MHFCReference.item_ingot2_icon), //
		MACHALTIE(MHFCReference.item_ingot3_name,
				MHFCReference.item_ingot3_icon);

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

	private final SubTypedItem<Item, IngotsSubType> itemPerk;

	public ItemIngot() {
		setHasSubtypes(true);
		itemPerk = new SubTypedItem<>(IngotsSubType.class);
		setUnlocalizedName(MHFCReference.item_ingot_basename);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		return super.getUnlocalizedName(itemStack) + "."
				+ itemPerk.getSubType(itemStack).name;
	}

	@Override
	public void registerIcons(IIconRegister iconRegister) {
		itemPerk.registerIcons(iconRegister);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void getSubItems(Item base, CreativeTabs tab,
			@SuppressWarnings("rawtypes") List list) {
		itemPerk.getSubItems(base, list);
	}

}
