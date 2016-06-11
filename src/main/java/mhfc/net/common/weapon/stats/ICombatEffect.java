package mhfc.net.common.weapon.stats;

import net.minecraft.entity.Entity;

public interface ICombatEffect {
	void applyTo(Entity target, float damageAmount);
}
