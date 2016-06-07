package mhfc.net.common.weapon;

import java.util.Objects;

import net.minecraft.util.DamageSource;

public enum ElementalType {
	Fire(new DamageSource("mhfc.fireelement").setDamageBypassesArmor()),
	Water(new DamageSource("mhfc.waterelement").setDamageBypassesArmor()),
	Thunder(new DamageSource("mhfc.thunderelement").setDamageBypassesArmor()),
	Dragon(new DamageSource("mhfc.dragonelement").setDamageBypassesArmor()),
	Ice(new DamageSource("mhfc.iceelement").setDamageBypassesArmor());

	public final DamageSource damageSource;

	private ElementalType(DamageSource damageSrc) {
		this.damageSource = Objects.requireNonNull(damageSrc);
	}
}
