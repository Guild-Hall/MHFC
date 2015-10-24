package mhfc.net.common.util;

import java.util.List;
import java.util.Objects;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mhfc.net.common.util.SubTypedItem.SubTypeEnum;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

/**
 * A util interface to describes blocks and items that store part of their
 * actual "value" in the meta-information of each Block/ItemStack.
 *
 * //FIXME: overthink extends Enum<T> as an requirement?!
 *
 * @author WorldSEnder
 *
 * @param <I>
 *            The underlying item type
 * @param <T>
 *            The enum-type used to enumerate all subtypes
 */
public class SubTypedItem<I, T extends Enum<T> & SubTypeEnum<I>> {
	public static interface SubTypeEnum<I> {
		/**
		 * The name of this sub item.
		 *
		 * @return
		 */
		public String getName();

		/**
		 * The texture to be used for this subitem.
		 *
		 * @return
		 */
		public String getTexPath();

		/**
		 * Gets the base item of this sub-item
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
	}

	/**
	 * A quick and dirty way to reuse the same texture path for multiple
	 * textures. Used for trees as they have different paths for the top and
	 * sides of leave blocks.
	 *
	 * @author WorldSEnder
	 *
	 */
	public static interface TexturePathModificator {
		public String modify(String texPath);
	}

	private static TexturePathModificator PASSTHROUGH = new TexturePathModificator() {
		@Override
		public String modify(String texPath) {
			return texPath;
		}
	};

	public static ItemStack fromSubBlock(SubTypeEnum<Block> subBlock, int size) {
		return new ItemStack(subBlock.getBaseItem(), size, subBlock.ordinal());
	}

	public static ItemStack fromSubItem(SubTypeEnum<Item> subItem, int size) {
		return new ItemStack(subItem.getBaseItem(), size, subItem.ordinal());
	}

	private final Class<T> clazzToken;
	private final T[] values;
	@SideOnly(Side.CLIENT)
	private IIcon[] textures;
	@SideOnly(Side.CLIENT)
	private TexturePathModificator modifier;

	public SubTypedItem(Class<T> enumClazz) {
		this(enumClazz, null);
	}

	public SubTypedItem(Class<T> enumClazz, TexturePathModificator modifier) {
		this.clazzToken = Objects.requireNonNull(enumClazz);
		// Cache the value, getEnumConstants() doesn't and can not safely
		this.values = clazzToken.getEnumConstants();
		this.modifier = modifier == null ? PASSTHROUGH : modifier;
		textures = new IIcon[values.length];
	}

	public IIcon[] getIcons() {
		return textures;
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister regIcon) {
		for (int i = 0; i < values.length; i++) {
			textures[i] = regIcon.registerIcon(modifier.modify(values[i].getTexPath()));
		}
	}

	@SideOnly(Side.CLIENT)
	public void getSubItems(Item block, List<ItemStack> list) {
		for (int i = 0; i < values.length; i++) {
			list.add(new ItemStack(block, 1, values[i].ordinal()));
		}
	}

	public T getSubType(ItemStack stack) {
		int clumpedMeta = MathHelper.clamp_int(stack.getItemDamage(), 0, values.length);
		return values[clumpedMeta];
	}
}
