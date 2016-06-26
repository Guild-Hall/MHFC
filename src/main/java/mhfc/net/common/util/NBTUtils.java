package mhfc.net.common.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTUtils {

	/**
	 * Used for null-safety
	 *
	 * @param stack
	 */
	public static NBTTagCompound getNBTChecked(ItemStack stack) {
		if (stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}
		return stack.stackTagCompound;
	}

	/**
	 * Decrease the integer tag by decrease, capping at 0.
	 *
	 * @param tag
	 * @param name
	 * @param decrease
	 */
	public static void decreaseIntUnsigned(NBTTagCompound tag, String name, int decrease) {
		int current = tag.getInteger(name);
		current = Math.max(current - decrease, 0);
		tag.setInteger(name, current);
	}

	public static void decreaseIntUnsigned(NBTTagCompound tag, String name) {
		decreaseIntUnsigned(tag, name, 1);
	}

	/**
	 * Decrease the integer tag by decrease, without caps
	 *
	 * @param tag
	 * @param name
	 * @param decrease
	 */
	public static void decreaseInt(NBTTagCompound tag, String name, int decrease) {
		tag.setInteger(name, tag.getInteger(name) - decrease);
	}

	/**
	 * Inverts the boolean tag name
	 *
	 * @param stack
	 * @param name
	 */
	public static void invertBoolean(NBTTagCompound tag, String name) {
		tag.setBoolean(name, !tag.getBoolean(name));
	}

}
