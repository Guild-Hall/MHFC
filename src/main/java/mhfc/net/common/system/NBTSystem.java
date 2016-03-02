package mhfc.net.common.system;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTSystem {

	/**
	 * Used for null-safety
	 *
	 * @param stack
	 */
	public static NBTTagCompound checkNBT(ItemStack stack) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		return stack.getTagCompound();
	}

	/**
	 * Shortcut for NBTTagCompound.hasKey()
	 */
	public static boolean verifyKey(ItemStack stack, String name) {
		return stack.stackTagCompound.hasKey(name);
	}

	public static void setInteger(ItemStack stack, String name, int value) {
		stack.stackTagCompound.setInteger(name, value);
	}

	public static int getInteger(ItemStack stack, String name) {
		return stack.stackTagCompound.getInteger(name);
	}

	public static void decreaseInteger(ItemStack stack, String name, int value) {
		if (NBTSystem.getInteger(stack, name) > 0) {
			NBTSystem.setInteger(stack, name, NBTSystem.getInteger(stack, name) - value);
		}
	}

	public static void decreaseIntegerIgnoreZero(ItemStack stack, String name, int value) {
		NBTSystem.setInteger(stack, name, NBTSystem.getInteger(stack, name) - value);
	}

	public static void setString(ItemStack stack, String name, String value) {
		stack.stackTagCompound.setString(name, value);
	}

	public static String getString(ItemStack stack, String name) {
		return stack.stackTagCompound.getString(name);
	}

	public static void setBoolean(ItemStack stack, String name, boolean value) {
		stack.stackTagCompound.setBoolean(name, value);
	}

	public static boolean getBoolean(ItemStack stack, String name) {
		return stack.stackTagCompound.getBoolean(name);
	}

	public static void invertBoolean(ItemStack stack, String name) {
		NBTSystem.setBoolean(stack, name, !NBTSystem.getBoolean(stack, name));
	}

	public static void setByte(ItemStack stack, String name, byte value) {
		stack.stackTagCompound.setByte(name, value);
	}

	public static byte getByte(ItemStack stack, String name) {
		if (!NBTSystem.verifyKey(stack, name)) {
			NBTSystem.setByte(stack, name, (byte) 0);
		}
		return stack.stackTagCompound.getByte(name);
	}
}
