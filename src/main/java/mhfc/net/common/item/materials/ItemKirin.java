package mhfc.net.common.item.materials;

import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.item.ItemColor;
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
		MANE(MHFCReference.item_kirin0_name, MHFCReference.base_monster_pelt, ItemColor.GRAY), //
		GEM(MHFCReference.item_kirin1_name, MHFCReference.base_monster_gem, ItemColor.WHITE), //
		THUNDERTAIL(MHFCReference.item_kirin2_name,
				MHFCReference.base_monster_part, ItemColor.WHITE), //
		LIGHTCRYSTAL(MHFCReference.item_kirin3_name,
				MHFCReference.base_misc_ore, ItemColor.GRAY), //
		PURECRYSTAL(MHFCReference.item_kirin4_name,
				MHFCReference.base_misc_ore, ItemColor.WHITE), // , //
		PLATINUMMANE(MHFCReference.item_kirin5_name,
				MHFCReference.base_monster_pelt, ItemColor.WHITE);

		public final String name;
		public final String texture;
		public final ItemColor color;
		private KirinSubType(String name, String texture, ItemColor color) {
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
			return MHFCItemRegistry.mhfcitemkirin;
		}
		@Override
		public ItemColor getColor() {
			return this.color;
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
	@Override
	public int getColorFromItemStack(ItemStack stack, int renderLayer) {
		return itemPerk.getSubType(stack).getColor().getRGB();
	}

}
