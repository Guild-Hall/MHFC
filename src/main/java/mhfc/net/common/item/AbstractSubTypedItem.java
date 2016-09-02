package mhfc.net.common.item;

import java.util.List;

import mhfc.net.common.util.SubTypedItem;
import mhfc.net.common.util.SubTypedItem.SubTypeEnum;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AbstractSubTypedItem<T extends Enum<T> & SubTypeEnum<Item>> extends Item {

	protected final SubTypedItem<Item, T> itemPerk;

	public AbstractSubTypedItem(Class<T> subtypeEnumClazz) {
		itemPerk = new SubTypedItem<>(subtypeEnumClazz);
		this.setHasSubtypes(true);
	}

	protected T getSubType(ItemStack stack) {
		return itemPerk.getSubType(stack);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return false;
	}


	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		return super.getUnlocalizedName(itemStack) + "." + itemPerk.getSubType(itemStack).getName();
	}


	@Override
	public void getSubItems(Item base, CreativeTabs tab, List list) {
		itemPerk.getSubItems(base, list);
	}

}
