package mhfc.net.common.weapon.range.bowgun;

import mhfc.net.common.weapon.ItemWeapon;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;

public abstract class ItemBowgun extends ItemWeapon<BowgunWeaponStats> {

	public ItemBowgun(BowgunWeaponStats stats) {
		super(stats);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}
	
	public static float getBulletVelocity(int charge)
	{
		float f = (float)charge / 30.0F;
		f = (f * f + f * 2.0F) / 3.0F;

		if (f > 1.0F)
		{
			f = 1.0F;
		}

		return f;
	}

	// TODO: add some combat interactions

}
