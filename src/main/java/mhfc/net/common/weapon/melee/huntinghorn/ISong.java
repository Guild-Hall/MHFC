package mhfc.net.common.weapon.melee.huntinghorn;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface ISong {
	/**
	 * Called when the song is played by play holding the stack. itemStats is of the itemstack is also given for
	 * convenience.
	 *
	 * @param player
	 *            the player playing the song
	 * @param stack
	 *            the stack of the weapon played with
	 * @param itemStats
	 *            the stats of the hold item
	 */
	void onPlayed(EntityPlayer player, ItemStack stack, HuntingHornWeaponStats itemStats);

	/**
	 *
	 * @return the unlocalized name of the song
	 */
	String getUnlocalizedName();
}
