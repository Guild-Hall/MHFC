package mhfc.net.common.weapon.melee;

import com.google.common.collect.Multimap;

import mhfc.net.common.weapon.ItemWeapon;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;

public abstract class ItemWeaponMelee<W extends MeleeWeaponStats> extends ItemWeapon<W> {
	public ItemWeaponMelee(W stats) {
		super(stats);
	}

	// TODO: honor sharpness, honor extended reach

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BLOCK;
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
		return multimap;
	}
}
