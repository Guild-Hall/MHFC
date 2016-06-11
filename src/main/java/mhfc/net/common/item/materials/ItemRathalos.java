package mhfc.net.common.item.materials;

import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.item.ItemColor;
import mhfc.net.common.item.ItemRecolorable;
import mhfc.net.common.util.SubTypedItem;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemRathalos extends ItemRecolorable {
	public static enum RathalosSubType
			implements
				SubTypedItem.SubTypeEnum<Item> {
		SHELL	(MHFCReference.item_rathalos0_name,MHFCReference.base_monster_carapace, ItemColor.RED), //
		WEBBING (MHFCReference.item_rathalos1_name,MHFCReference.base_monster_webbing, ItemColor.RED), //
		MARROW  (MHFCReference.item_rathalos2_name,MHFCReference.base_monster_part, ItemColor.RED), //
		WING    (MHFCReference.item_rathalos3_name,MHFCReference.base_monster_part, ItemColor.RED), //
		PLATE   (MHFCReference.item_rathalos4_name,MHFCReference.base_monster_scale, ItemColor.RED);

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
			return MHFCItemRegistry.mhfcitemrathalos;
		}
		@Override
		public ItemColor getColor() {
			return color;
		}
	}

	private final SubTypedItem<Item, RathalosSubType> itemPerk;

	public ItemRathalos() {
		itemPerk = new SubTypedItem<>(RathalosSubType.class);
		setHasSubtypes(true);
		setUnlocalizedName(MHFCReference.item_rathalos_basename);
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
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer,
			@SuppressWarnings("rawtypes") List par3List, boolean par4) {
		switch (itemPerk.getSubType(par1ItemStack)) {
			case PLATE :
				par3List.add("Rare Drop by Rathalos");
				break;
			default :
				par3List.add("Drop by Rathalos");
				break;
		}
	}

	@Override
	public int getColorFromItemStack(ItemStack stack, int renderLayer) {
		return itemPerk.getSubType(stack).getColor().getRGB();
	}
}
