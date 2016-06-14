package mhfc.net.common.weapon.melee.huntinghorn;

import mhfc.net.common.weapon.melee.MeleeWeaponStats;

public class HuntingHornWeaponStats extends MeleeWeaponStats {
	public static class HuntingHornWeaponStatsBuilder extends MeleeWeaponStatsBuilder<HuntingHornWeaponStatsBuilder> {
		public HuntingHornWeaponStatsBuilder() {}

		@Override
		protected HuntingHornWeaponStatsBuilder getThis() {
			return this;
		}

		@Override
		public HuntingHornWeaponStats build() {
			return new HuntingHornWeaponStats(this);
		}
	}

	protected HuntingHornWeaponStats(HuntingHornWeaponStatsBuilder builder) {
		super(builder);
	}

	// TODO: add notes to play

}
