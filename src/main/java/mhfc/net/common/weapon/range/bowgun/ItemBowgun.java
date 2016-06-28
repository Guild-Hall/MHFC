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
		return EnumAction.bow;
	}

	// TODO: add some combat interactions

}
