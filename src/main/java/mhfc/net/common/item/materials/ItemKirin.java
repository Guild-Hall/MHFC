package mhfc.net.common.item.materials;

import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.util.SubTypedItem;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemKirin extends Item {
	public static enum KirinSubType implements SubTypedItem.SubTypeEnum<Item> {
		MANE(MHFCReference.item_kirin0_name, MHFCReference.item_kirin0_icon), //
		GEM(MHFCReference.item_kirin1_name, MHFCReference.item_kirin1_icon), //
		THUNDERTAIL(MHFCReference.item_kirin2_name,
				MHFCReference.item_kirin2_icon), //
		LIGHTCRYSTAL(MHFCReference.item_kirin3_name,
				MHFCReference.item_kirin3_icon), //
		PURECRYSTAL(MHFCReference.item_kirin4_name,
				MHFCReference.item_kirin4_icon), // , //
		PLATINUMMANE(MHFCReference.item_kirin5_name,
				MHFCReference.item_kirin5_icon);

		public final String name;
		public final String texture;
		private KirinSubType(String name, String texture) {
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
			return MHFCItemRegistry.mhfcitemkirin;
		}
	}

	private final SubTypedItem<Item, KirinSubType> itemPerk;

	public ItemKirin() {
		itemPerk = new SubTypedItem<>(KirinSubType.class);
		setHasSubtypes(true);
		setUnlocalizedName(MHFCReference.item_kirin_basename);
		setCreativeTab(MHFCMain.mhfctabs);
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

	@Override
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack itemStack, EntityPlayer player,
			@SuppressWarnings("rawtypes") List list, boolean par4) {
		switch (itemPerk.getSubType(itemStack)) {
			case PURECRYSTAL :
			case PLATINUMMANE :
				list.add("Rare Drop by Kirin");
				break;
			default :
				list.add("Drop by Kirin");
				break;
		}
	}

}
