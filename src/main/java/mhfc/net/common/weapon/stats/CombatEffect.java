package mhfc.net.common.weapon.stats;

import java.util.Objects;

import com.google.common.base.Preconditions;

import net.minecraft.entity.Entity;

public class CombatEffect {
	private ICombatEffectType effect;
	private float amount;

	public CombatEffect(ICombatEffectType effect, float amount) {
		Preconditions.checkArgument(amount >= 0, "amount must be positive");
		this.effect = Objects.requireNonNull(effect);
		this.amount = amount;
	}

	public ICombatEffectType getType() {
		return effect;
	}

	public float getAmount() {
		return amount;
	}

	public float getAmount(float scale) {
		return getAmount() * scale;
	}

	public void applyTo(Entity target) {
		this.effect.applyTo(target, getAmount());
	}
}
