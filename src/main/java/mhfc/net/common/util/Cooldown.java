package mhfc.net.common.util;

import java.util.List;

import mhfc.net.common.helper.MHFCNBTHelper;
import net.minecraft.item.ItemStack;

public class Cooldown {

	public static final String COOLDOWN_NBT = "mhfc:cooldown";

	/**
	 * Call this onUpdate() in your Item WARNING: you should check for
	 * !world.isRemote
	 *
	 * @param iStack
	 *            stack to update
	 * @param maxCooldown
	 *            maximum cooldown
	 */
	public static void onUpdate(ItemStack iStack, int maxCooldown) {
		MHFCNBTHelper.checkNBT(iStack);
		if (!MHFCNBTHelper.verifyKey(iStack, COOLDOWN_NBT)) {
			MHFCNBTHelper.setInteger(iStack, COOLDOWN_NBT, maxCooldown);
		}
		if (MHFCNBTHelper.getInteger(iStack, COOLDOWN_NBT) > 0) {
			MHFCNBTHelper.decreaseInteger(iStack, COOLDOWN_NBT, 2);
		}
	}

	/**
	 * Checks for cooldown. Call it when your item needs to be used
	 *
	 * @param iStack
	 *            Stack to check
	 * @param maxCooldown
	 *            Maximum cooldown
	 * @return True if item's cooldown is zero, returns true and sets it to
	 *         maximum, otherwise false
	 */
	public static boolean canUse(ItemStack iStack, int maxCooldown) {
		MHFCNBTHelper.checkNBT(iStack);
		if (MHFCNBTHelper.getInteger(iStack, COOLDOWN_NBT) > 0) {
			return false;
		}
		MHFCNBTHelper.setInteger(iStack, COOLDOWN_NBT, maxCooldown);
		return true;
	}

	/**
	 * Call this addInformation() in your Item
	 *
	 * @param iStack
	 *            stack to update
	 * @param info
	 *            cooldown text
	 */
	@SuppressWarnings("unchecked")
	public static void displayCooldown(ItemStack iStack,
			@SuppressWarnings("rawtypes") List info, int maxCooldown) {
		MHFCNBTHelper.checkNBT(iStack);
		if (MHFCNBTHelper.getInteger(iStack, COOLDOWN_NBT) > 0) {
			info.add("Cooldown Time: "
					+ MHFCNBTHelper.getInteger(iStack, COOLDOWN_NBT));
		} else {
			info.add("Cooldown Time: Ready to Use");
		}
	}
}
