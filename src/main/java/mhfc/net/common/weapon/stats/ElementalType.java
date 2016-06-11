package mhfc.net.common.weapon.stats;

import java.util.Objects;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;

public enum ElementalType implements ICombatEffect {
	Fire(new DamageSource("mhfc.fireelement").setDamageBypassesArmor()),
	Water(new DamageSource("mhfc.waterelement").setDamageBypassesArmor()),
	Thunder(new DamageSource("mhfc.thunderelement").setDamageBypassesArmor()),
	Dragon(new DamageSource("mhfc.dragonelement").setDamageBypassesArmor()),
	Ice(new DamageSource("mhfc.iceelement").setDamageBypassesArmor());

	public final DamageSource damageSource;

	private ElementalType(DamageSource damageSrc) {
		this.damageSource = Objects.requireNonNull(damageSrc);
	}

	@Override
	public void applyTo(Entity target, float damageAmount) {
		// TODO: add the attacker as damageSourceEntity
		target.attackEntityFrom(damageSource, damageAmount);
	}
}
