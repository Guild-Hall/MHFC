package mhfc.net.common.weapon.stats;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

/**
 * An immutable class describing the stats of a weapon. Melee and certain range classes extend this. The attributes here
 * could be boosted with abilities thus the getter methods most often take a player argument.
 *
 * @author WorldSEnder
 *
 */
public class WeaponStats {
	public static class WeaponStatsBuilder {
		private float attackBase;
		private List<ICombatEffect> effects;
		private int slots;

		public WeaponStatsBuilder(float attackBase) {
			Preconditions.checkArgument(attackBase >= 0, "attack must not be less than zero");
			this.attackBase = attackBase;
			this.effects = new ArrayList<>();
			this.slots = 0;
		}

		public WeaponStatsBuilder setSlotCount(int slots) {
			Preconditions.checkArgument(slots >= 0, "slots must not be less than zero");
			this.slots = slots;
			return this;
		}

		public WeaponStatsBuilder addCombatEffect(ICombatEffect effect) {
			Objects.requireNonNull(effect);
			effects.add(effect);
			return this;
		}

		public WeaponStats build() {
			return new WeaponStats(this);
		}
	}

	protected WeaponStats(WeaponStatsBuilder builder) {
		this.attackBase = builder.attackBase;
		this.combatEffects = ImmutableList.copyOf(builder.effects);
		this.slotCount = builder.slots;
	}

	private final float attackBase;
	private final List<ICombatEffect> combatEffects;
	private final int slotCount;

}
