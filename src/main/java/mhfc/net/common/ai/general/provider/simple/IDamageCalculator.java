package mhfc.net.common.ai.general.provider.simple;

import net.minecraft.entity.Entity;

public interface IDamageCalculator {
	/**
	 * Given an entity e calculates the damage done to it.
	 *
	 * @param e
	 * @return
	 */
	public float accept(Entity e);
}