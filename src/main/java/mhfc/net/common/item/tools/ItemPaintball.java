package mhfc.net.common.item.tools;

import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.item.ItemColor;
import mhfc.net.common.item.ItemRecolorable;
import mhfc.net.common.util.SubTypedItem;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemPaintball extends ItemRecolorable {

	public static enum PaintballType implements SubTypedItem.SubTypeEnum<Item> {
		BLACK ( "black", ItemColor.BLACK),
		RED   ( "red", ItemColor.RED),
		GREEN ( "green", ItemColor.GREEN),
		BROWN ( "brown", ItemColor.BROWN),
		BLUE  ( "blue", ItemColor.BLUE),
		PURPLE( "purple", ItemColor.PURPLE),
		CYAN  ( "cyan", ItemColor.CYAN),
		SILVER( "silver", ItemColor.SILVER),
		GRAY  ( "gray", ItemColor.GRAY),
		PINK  ( "pink", ItemColor.PINK),
		LIME  ( "lime", ItemColor.LIME),
		YELLOW( "yellow", ItemColor.YELLOW),
		LIBLUE( "light_blue", ItemColor.LIBLUE ),
		MAGNTA( "magenta", ItemColor.MAGNTA),
		ORANGE( "orange", ItemColor.ORANGE),
		WHITE ( "white", ItemColor.WHITE);

		public final String name;
		public final String texture;
		public final ItemColor color;

		private PaintballType(String name, ItemColor color) {
			this.name = name;
			this.texture = MHFCReference.base_monster_gem;
			this.color = color;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public String getTexPath() {
			return texture;
		}

		@Override
		public Item getBaseItem() {
			return MHFCItemRegistry.MHFCItemPaintball;
		}

		@Override
		public ItemColor getColor() {
			return color;
		}
	}

	private final SubTypedItem<Item, PaintballType> itemPerk;

	public ItemPaintball() {
		super();
		itemPerk = new SubTypedItem<>(PaintballType.class);
		setUnlocalizedName(MHFCReference.item_paintball_basename);
		setCreativeTab(MHFCMain.mhfctabs);
		setMaxStackSize(64);
	}

	@Override
	public int getColorFromItemStack(ItemStack stack, int renderLayer) {
		return itemPerk.getSubType(stack).getColor().getRGB();
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		itemPerk.getSubItems(item, list);
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

	@Override
	public IIcon getIconFromDamage(int meta) {
		return itemPerk.getIcons()[meta];
	}
}
