package mhfc.heltrato.common.item.weapon;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ReloadHelper
{
	public static int	STATE_NONE	= 0, STATE_RELOADED = 1, STATE_READY = 2;
	
	private static void initTagCompound(ItemStack itemstack)
	{
		if (!itemstack.hasTagCompound())
		{
			itemstack.setTagCompound(new NBTTagCompound());
		}
	}
	
	public static int getReloadState(ItemStack itemstack)
	{
		if (itemstack.hasTagCompound()) return itemstack.getTagCompound().getByte("reloaded");
		return 0;
	}
	
	public static void setReloadState(ItemStack itemstack, int state)
	{
		initTagCompound(itemstack);
		itemstack.getTagCompound().setByte("reloaded", (byte) state);
	}
}
