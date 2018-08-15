package mhfc.net.common.weapon.range.bowgun;

import mhfc.net.common.weapon.ItemWeapon;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class ItemBowgun extends ItemWeapon<BowgunWeaponStats> {

	public ItemBowgun(BowgunWeaponStats stats) {
		super(stats);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}

	public static float getBulletVelocity(int charge) {
		float f = charge / 30.0F;
		f = (f * f + f * 2.0F) / 3.0F;

		if (f > 1.0F) {
			f = 1.0F;
		}

		return f;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity holder, int slot, boolean isHoldItem) {
		super.onUpdate(stack, world, holder, slot, isHoldItem);
		if (!isHoldItem) {
			return;
		}
	}

	// TODO: add some combat interactions

}
