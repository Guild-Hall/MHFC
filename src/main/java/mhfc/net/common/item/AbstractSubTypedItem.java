package mhfc.net.common.item;

import mhfc.net.common.util.SubTypedItem;
import mhfc.net.common.util.SubTypedItem.SubTypeEnum;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.List;

public class AbstractSubTypedItem<T extends Enum<T> & SubTypeEnum<Item>> extends Item
		implements
		IItemColored,
		IItemVarianted {

	protected final SubTypedItem<Item, T> itemPerk;

	public AbstractSubTypedItem(Class<T> subtypeEnumClazz) {
		itemPerk = new SubTypedItem<>(subtypeEnumClazz);
		this.setHasSubtypes(true);
	}

	protected T getSubType(ItemStack stack) {
		return itemPerk.getSubType(stack);
	}

	@Override
	public List<String> getVariantNames() {
		return itemPerk.getVariants();
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return false;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		return super.getUnlocalizedName(itemStack) + "." + itemPerk.getSubType(itemStack).getUnlocalizedName();
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		  if (this.isInCreativeTab(tab))
	        {
	            items.add(new ItemStack(this));
	        }
	}

	@Override
	public int getColorFromItemStack(ItemStack stack, int texIndex) {
		return itemPerk.getSubType(stack).getColor().getRGB();
	}

}
