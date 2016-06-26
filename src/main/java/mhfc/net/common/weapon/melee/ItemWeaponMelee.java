package mhfc.net.common.weapon.melee;

import mhfc.net.common.weapon.ItemWeapon;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;

public abstract class ItemWeaponMelee<W extends MeleeWeaponStats> extends ItemWeapon<W> {
	public ItemWeaponMelee(W stats) {
		super(stats);
	}

	// TODO: honor sharpness, honor extended reach

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.block;
	}
}
