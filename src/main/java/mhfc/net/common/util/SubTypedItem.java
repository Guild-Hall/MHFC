package mhfc.net.common.util;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Objects;

import mhfc.net.MHFCMain;
import mhfc.net.common.item.ItemColor;
import mhfc.net.common.util.SubTypedItem.SubTypeEnum;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * A util interface to describes blocks and items that store part of their actual "value" in the meta-information of
 * each Block/Item.
 *
 *
 * @author WorldSEnder
 *
 * @param <I>
 *            The underlying item type
 * @param <T>
 *            The enum-type used to enumerate all subtypes
 */
public class SubTypedItem<I, T extends Enum<T> & SubTypeEnum<I>> {
	public static interface SubTypeEnum<I> extends IStringSerializable {
		/**
		 * The name of this sub item.
		 *
		 * @return
		 */
		@Override
		public String getName();

		/**
		 * Gets the base item of this sub-item. This <b>must</b> be the same for all values
		 *
		 * @return
		 */
		public I getBaseItem();

		/**
		 * Gets the meta data associated with this subitem.
		 *
		 * @return
		 */
		public int ordinal();

		public default ItemColor getColor() {
			return ItemColor.WHITE;
		}
	}

	/**
	 * A quick and dirty way to reuse the same texture path for multiple textures. Used for trees as they have different
	 * paths for the top and sides of leave blocks.
	 *
	 * @author WorldSEnder
	 *
	 */
	public static interface TexturePathModificator {
		public String modify(String texPath);
	}

	public static ItemStack fromSubBlock(SubTypeEnum<Block> subBlock, int size) {
		return new ItemStack(subBlock.getBaseItem(), size, subBlock.ordinal());
	}

	public static ItemStack fromSubItem(SubTypeEnum<Item> subItem, int size) {
		return new ItemStack(subItem.getBaseItem(), size, subItem.ordinal());
	}

	private static <T extends Enum<T> & IStringSerializable> T[] getValues(PropertyEnum<T> property) {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) Array.newInstance(property.getValueClass(), 0);
		array = property.getAllowedValues().toArray(array);
		return array;
	}

	private boolean warningDisplay = false;
	private final Class<T> clazzToken;
	private final T[] values;

	public SubTypedItem(Class<T> enumClazz) {
		this(enumClazz, enumClazz.getEnumConstants());
	}

	public SubTypedItem(PropertyEnum<T> property) {
		this(property.getValueClass(), getValues(property));
	}

	private SubTypedItem(Class<T> enumClazz, T[] values) {
		this.clazzToken = Objects.requireNonNull(enumClazz);
		this.values = mhfc.net.common.util.Objects.requireNonNullDeep(values);
	}

	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, List<ItemStack> list) {
		for (int i = 0; i < values.length;) {
			T value = values[i++];
			list.add(new ItemStack(item, 1, value.ordinal()));
		}
	}

	public int getMeta(T subType) {
		return subType.ordinal();
	}

	public T getSubType(ItemStack stack) {
		return getSubType(stack.getItemDamage());
	}

	public T getSubType(int meta) {
		if (values.length == 0) {
			if (!warningDisplay) {
				MHFCMain.logger().debug("SubTypedItem with zero entries: " + clazzToken);
			}
			return null;
		}
		int clumpedMeta = MathHelper.clamp_int(meta, 0, values.length - 1);
		return values[clumpedMeta];
	}
}
