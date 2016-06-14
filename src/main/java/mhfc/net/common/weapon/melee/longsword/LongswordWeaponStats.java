package mhfc.net.common.weapon.melee.longsword;

import mhfc.net.common.weapon.melee.MeleeWeaponStats;

public class LongswordWeaponStats extends MeleeWeaponStats {
	public static class LongswordWeaponStatsBuilder extends MeleeWeaponStatsBuilder<LongswordWeaponStatsBuilder> {
		public LongswordWeaponStatsBuilder() {}

		@Override
		protected LongswordWeaponStatsBuilder getThis() {
			return this;
		}

		@Override
		public LongswordWeaponStats build() {
			return new LongswordWeaponStats(this);
		}
	}

	public LongswordWeaponStats(LongswordWeaponStatsBuilder builder) {
		super(builder);
	}

}
