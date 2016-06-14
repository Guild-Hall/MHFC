package mhfc.net.common.weapon.range.bowgun;

import mhfc.net.common.weapon.stats.WeaponStats;

public class BowgunWeaponStats extends WeaponStats {
	public static class BowgunWeaponStatsBuilder extends WeaponStatsBuilder<BowgunWeaponStatsBuilder> {
		public BowgunWeaponStatsBuilder() {}

		@Override
		protected BowgunWeaponStatsBuilder getThis() {
			return this;
		}

		@Override
		public BowgunWeaponStats build() {
			return new BowgunWeaponStats(this);
		}
	}

	protected BowgunWeaponStats(BowgunWeaponStatsBuilder builder) {
		super(builder);
		// TODO Auto-generated constructor stub
	}

}
