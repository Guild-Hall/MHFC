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

public class ItemArmorSphere extends Item {
	public static enum ArmorSphereSubType
			implements
				SubTypedItem.SubTypeEnum<Item> {
		NORMAL(MHFCReference.item_armorsphere0_name,
				MHFCReference.item_armorsphere0_icon), //
		PLUS(MHFCReference.item_armorsphere1_name,
				MHFCReference.item_armorsphere1_icon);

		public final String name;
		public final String texture;
		private ArmorSphereSubType(String name, String texture) {
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
			return MHFCItemRegistry.mhfcitemarmorsphere;
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
