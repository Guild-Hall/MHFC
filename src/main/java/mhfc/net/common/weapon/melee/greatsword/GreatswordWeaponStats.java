package mhfc.net.common.weapon.melee.greatsword;

import mhfc.net.common.weapon.melee.MeleeWeaponStats;

public class GreatswordWeaponStats extends MeleeWeaponStats {
	public static class GreatswordWeaponStatsBuilder extends MeleeWeaponStatsBuilder<GreatswordWeaponStatsBuilder> {
		public GreatswordWeaponStatsBuilder() {}

		@Override
		protected GreatswordWeaponStatsBuilder getThis() {
			return this;
		}

		@Override
		public GreatswordWeaponStats build() {
			return new GreatswordWeaponStats(this);
		}
	}

	public GreatswordWeaponStats(GreatswordWeaponStatsBuilder builder) {
		super(builder);
	}

}
