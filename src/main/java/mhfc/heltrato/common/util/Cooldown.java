package mhfc.heltrato.common.util;

import java.util.List;

import mhfc.heltrato.common.system.NBTSystem;
import net.minecraft.item.ItemStack;

public class Cooldown{

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
	public static void onUpdate(ItemStack iStack, int maxCooldown){
		NBTSystem.checkNBT(iStack);
		if(!NBTSystem.verifyKey(iStack, COOLDOWN_NBT)){
			NBTSystem.setInteger(iStack, COOLDOWN_NBT, maxCooldown);
		}
		if(NBTSystem.getInteger(iStack, COOLDOWN_NBT) > 0){
			NBTSystem.decreaseInteger(iStack, COOLDOWN_NBT, 1);
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
	public static boolean canUse(ItemStack iStack, int maxCooldown){
		NBTSystem.checkNBT(iStack);
		if(NBTSystem.getInteger(iStack, COOLDOWN_NBT) > 0){
			return false;
		}else{
			NBTSystem.setInteger(iStack, COOLDOWN_NBT, maxCooldown);
			return true;
		}
	}
	
	/**
	 * Call this addInformation() in your Item
	 * 
	 * @param iStack
	 *            stack to update
	 * @param info
	 *            cooldown text
	 */
	public static void displayCooldown(ItemStack iStack, List info, int maxCooldown){
		NBTSystem.checkNBT(iStack);
		if(NBTSystem.getInteger(iStack, COOLDOWN_NBT) > 0){
			info.add("Cooldown Time: " + NBTSystem.getInteger(iStack, COOLDOWN_NBT));
		}else{
			info.add("Cooldown Time: Ready to Use");
		}
	}
}