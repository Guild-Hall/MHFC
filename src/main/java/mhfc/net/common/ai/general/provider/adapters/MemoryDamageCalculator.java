package mhfc.net.common.ai.general.provider.adapters;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import mhfc.net.common.ai.general.provider.simple.IDamageCalculator;
import net.minecraft.entity.Entity;

/**
 * This special damage calculator remembers which entities were damaged by it and only damages entities once until
 * it is reset.
 */
public class MemoryDamageCalculator extends DecisiveDamageCalculator {

	private final Set<Entity> damagedEntities = new HashSet<>();
	private IDamageCalculator forward;

	public MemoryDamageCalculator(IDamageCalculator otherCalculator) {
		forward = Objects.requireNonNull(otherCalculator);
	}

	@Override
	public boolean shouldDamage(Entity e) {
		return !damagedEntities.contains(e);
	}

	@Override
	public float damage(Entity e) {
		damagedEntities.add(e);
		return forward.accept(e);
	}

	public void reset() {
		damagedEntities.clear();
	}

}