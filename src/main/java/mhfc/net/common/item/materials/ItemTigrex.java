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

public class ItemTigrex extends Item {
	public static enum TigrexSubType implements SubTypedItem.SubTypeEnum<Item> {
		SCALE(MHFCReference.item_tigrex0_name, MHFCReference.base_monster_scale, ItemColor.YELLOW), //
		SHELL(MHFCReference.item_tigrex1_name, MHFCReference.base_monster_carapace, ItemColor.YELLOW), //
		CLAW(MHFCReference.item_tigrex2_name, MHFCReference.base_monster_sharp, ItemColor.YELLOW), //
		FANG(MHFCReference.item_tigrex3_name, MHFCReference.base_monster_sharp, ItemColor.YELLOW), //
		SKULLSHELL(MHFCReference.item_tigrex4_name,
				MHFCReference.base_monster_bone, ItemColor.YELLOW), // , //
		TAIL(MHFCReference.item_tigrex5_name, MHFCReference.base_monster_part, ItemColor.YELLOW);

		public final String name;
		public final String texture;
		public final ItemColor color;
		private TigrexSubType(String name, String texture, ItemColor color) {
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
			return MHFCItemRegistry.mhfcitemtigrex;
		}

		@Override
		public ItemColor getColor() {
			return color;
		}
	}

	private final SubTypedItem<Item, TigrexSubType> itemPerk;

	public ItemTigrex() {
		itemPerk = new SubTypedItem<>(TigrexSubType.class);
		setHasSubtypes(true);
		setUnlocalizedName(MHFCReference.item_tigrex_basename);
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
			case SKULLSHELL :
			case TAIL :
				list.add("Rare Drop by Tigrex");
				break;
			default :
				list.add("Drop by Tigrex");
		}
	}
	@Override
	public int getColorFromItemStack(ItemStack stack, int renderLayer) {
		return itemPerk.getSubType(stack).getColor().getRGB();
	}
}
