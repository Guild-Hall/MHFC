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
import net.minecraft.util.IIcon;

public class ItemBase extends Item {
	public static enum BaseSubType implements SubTypedItem.SubTypeEnum<Item> {
		ANUMIUM(MHFCReference.item_base0_name, MHFCReference.item_base0_icon), //
		MEGANUM(MHFCReference.item_base1_name, MHFCReference.item_base1_icon);

		public final String name;
		public final String texture;
		private BaseSubType(String name, String texture) {
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
			return MHFCItemRegistry.mhfcitembase;
		}
	}

	private SubTypedItem<Item, BaseSubType> itemPerk;

	public ItemBase() {
		itemPerk = new SubTypedItem<>(BaseSubType.class);
		setUnlocalizedName(MHFCReference.item_base_basename);
		setCreativeTab(MHFCMain.mhfctabs);
		setMaxStackSize(5);
		setHasSubtypes(true);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		return super.getUnlocalizedName(itemStack) + "."
				+ itemPerk.getSubType(itemStack).name;
	}

	@Override
	public IIcon getIconFromDamage(int meta) {
		return itemPerk.getIcons()[meta];
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
