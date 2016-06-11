package mhfc.net.common.weapon.melee;

import java.util.Objects;

import mhfc.net.common.weapon.stats.ICombatEffect;
import mhfc.net.common.weapon.stats.Sharpness;
import mhfc.net.common.weapon.stats.WeaponStats;

public class MeleeWeaponStats extends WeaponStats {
	public static class MeleeWeaponStatsBuilder extends WeaponStatsBuilder {
		private Sharpness sharpness;

		public MeleeWeaponStatsBuilder(float attackAmount, Sharpness sharpness) {
			super(attackAmount);
			this.sharpness = Objects.requireNonNull(sharpness);
		}

		@Override
		public MeleeWeaponStatsBuilder addCombatEffect(ICombatEffect effect) {
			super.addCombatEffect(effect);
			return this;
		}

		@Override
		public MeleeWeaponStatsBuilder setSlotCount(int slots) {
			super.setSlotCount(slots);
			return this;
		}

		@Override
		public MeleeWeaponStats build() {
			return new MeleeWeaponStats(this);
		}
	}

	protected MeleeWeaponStats(MeleeWeaponStatsBuilder builder) {
		super(builder);
		this.weaponSharpness = builder.sharpness;
	}

	private final Sharpness weaponSharpness;

}
