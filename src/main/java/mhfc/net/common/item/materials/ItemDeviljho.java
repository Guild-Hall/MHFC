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

public class ItemDeviljho extends ItemRecolorable {
	public static enum DeviljhoSubType
			implements
				SubTypedItem.SubTypeEnum<Item> {
		SCALE (MHFCReference.item_deviljho0_name, MHFCReference.base_item_scale, ItemColor.GREEN), //
		FANG  (MHFCReference.item_deviljho1_name , MHFCReference.base_item_sharp, ItemColor.GREEN), //
		HIDE  (MHFCReference.item_deviljho2_name , MHFCReference.base_item_pelt, ItemColor.GREEN), //
		TALON (MHFCReference.item_deviljho3_name, MHFCReference.base_item_sharp, ItemColor.GREEN), //
		SCALP (MHFCReference.item_deviljho4_name, MHFCReference.base_item_part, ItemColor.GREEN),
		TAIL  (MHFCReference.item_deviljho5_name , MHFCReference.base_item_part, ItemColor.GREEN)		;


		public final String name;
		public final String texture;
		public final ItemColor color;
		private DeviljhoSubType(String name, String texture, ItemColor color) {
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
			return MHFCItemRegistry.mhfcitemdeviljho;
		}
		//@Override
		public ItemColor getColor() {
			return this.color;
		}
	}

	private final SubTypedItem<Item, DeviljhoSubType> itemPerk;

	public ItemDeviljho() {
		itemPerk = new SubTypedItem<>(DeviljhoSubType.class);
		setHasSubtypes(true);
		setUnlocalizedName(MHFCReference.item_deviljho_basename);
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
			case SCALP :
				par3List.add("Rare Drop by Deviljho");
				break;
			default :
				par3List.add("Drop by Deviljho");
				break;
		}
	}

	@Override
	public int getColorFromItemStack(ItemStack stack, int renderLayer) {
		return itemPerk.getSubType(stack).getColor().getRGB();
	}
}
