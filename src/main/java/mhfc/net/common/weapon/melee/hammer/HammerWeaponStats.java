package mhfc.net.common.weapon.melee.hammer;

import mhfc.net.common.weapon.melee.MeleeWeaponStats;

public class HammerWeaponStats extends MeleeWeaponStats {
	public static class HammerWeaponStatsBuilder extends MeleeWeaponStatsBuilder<HammerWeaponStatsBuilder> {
		public HammerWeaponStatsBuilder() {}

		@Override
		protected HammerWeaponStatsBuilder getThis() {
			return this;
		}

		@Override
		public HammerWeaponStats build() {
			return new HammerWeaponStats(this);
		}
	}

	protected HammerWeaponStats(HammerWeaponStatsBuilder builder) {
		super(builder);
	}

	// Currently none? Maybe charge times, i don't know

}
