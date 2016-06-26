package mhfc.net.common.weapon.range.bow;

import mhfc.net.common.weapon.stats.WeaponStats;

public class BowWeaponStats extends WeaponStats {
	public static class BowWeaponStatsBuilder extends WeaponStatsBuilder<BowWeaponStatsBuilder> {
		public BowWeaponStatsBuilder() {}

		@Override
		protected BowWeaponStatsBuilder getThis() {
			return this;
		}

		@Override
		public BowWeaponStats build() {
			return new BowWeaponStats(this);
		}
	}

	protected BowWeaponStats(BowWeaponStatsBuilder builder) {
		super(builder);
		// TODO Auto-generated constructor stub
	}

}
