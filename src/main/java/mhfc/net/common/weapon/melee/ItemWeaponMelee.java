package mhfc.net.common.weapon.melee;

import com.google.common.collect.Multimap;

import mhfc.net.common.weapon.ItemWeapon;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
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

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(ItemStack stack) {
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(stack);
		multimap.put(
				SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
				new AttributeModifier(field_111210_e, "Weapon Attack", stats.getAttack(), 0));
		return multimap;
	}
}
