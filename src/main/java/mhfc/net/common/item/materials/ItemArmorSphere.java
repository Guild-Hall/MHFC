package mhfc.net.common.item.materials;

import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.item.ItemColor;
import mhfc.net.common.util.SubTypedItem;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemArmorSphere extends Item {
	public static enum ArmorSphereSubType
			implements
				SubTypedItem.SubTypeEnum<Item> {
		NORMAL(MHFCReference.item_armorsphere0_name,
				ItemColor.BLUE), //
		PLUS(MHFCReference.item_armorsphere1_name,
				ItemColor.LIME);

		public final String name;
		public final String texture;
		public final ItemColor color;
		private ArmorSphereSubType(String name, ItemColor color) {
			this.name = name;
			this.texture = MHFCReference.base_misc_armorsphere;
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
			return MHFCItemRegistry.mhfcitemarmorsphere;
		}
		public ItemColor getColor() {
			return this.color;
		}
	}

	private final SubTypedItem<Item, ArmorSphereSubType> itemPerk;

	public ItemArmorSphere() {
		itemPerk = new SubTypedItem<>(ArmorSphereSubType.class);
		setUnlocalizedName(MHFCReference.item_armorsphere_basename);
		setCreativeTab(MHFCMain.mhfctabs);
		setMaxStackSize(16);
	}

	@Override
	public IIcon getIconFromDamage(int meta) {
		return itemPerk.getIcons()[meta];
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

	@Override
	public int getColorFromItemStack(ItemStack stack, int renderLayer) {
		return itemPerk.getSubType(stack).getColor().getRGB();
	}
}
