package mhfc.net.common.ai.general.provider.adapters;

import mhfc.net.common.ai.general.provider.simple.IDamageCalculator;
import net.minecraft.entity.Entity;

public abstract class DecisiveDamageCalculator implements IDamageCalculator {

	/**
	 * Decides whether the given Entity should be damaged
	 */
	public abstract boolean shouldDamage(Entity e);

	/**
	 * Returns the damage that should be dealt to the entity e.
	 */
	public abstract float damage(Entity e);

	@Override
	public float accept(Entity e) {
		if (shouldDamage(e)) {
			return damage(e);
		}
		return 0f;
	}
}