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

public class ItemSac extends ItemRecolorable {
	public static enum SacSubType implements SubTypedItem.SubTypeEnum<Item> {
		FIRE(MHFCReference.item_sac0_name, MHFCReference.item_sac0_icon, ItemColor.RED);

		public final String name;
		public final String texture;
		public final ItemColor color;
		private SacSubType(String name, String texture, ItemColor color) {
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
			return MHFCItemRegistry.mhfcitemsac;
		}
		@Override
		public ItemColor getColor() {
			return color;
		}
	}

	private final SubTypedItem<Item, SacSubType> itemPerk;

	public ItemSac() {
		itemPerk = new SubTypedItem<>(SacSubType.class);
		setHasSubtypes(true);
		setUnlocalizedName(MHFCReference.item_sac_basename);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		return super.getUnlocalizedName(itemStack) + "."
				+ itemPerk.getSubType(itemStack).getName();
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
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer,
			@SuppressWarnings("rawtypes") List par3List, boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
	}

	@Override
	public int getColorFromItemStack(ItemStack stack, int renderLayer) {
		return itemPerk.getSubType(stack).getColor().getRGB();
	}

}
