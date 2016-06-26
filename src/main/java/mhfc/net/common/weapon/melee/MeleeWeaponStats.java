package mhfc.net.common.weapon.melee;

import java.util.function.Consumer;

import com.google.common.base.Preconditions;

import mhfc.net.common.weapon.stats.Sharpness;
import mhfc.net.common.weapon.stats.Sharpness.SharpnessBuilder;
import mhfc.net.common.weapon.stats.WeaponStats;

public class MeleeWeaponStats extends WeaponStats {
	public abstract static class MeleeWeaponStatsBuilder<T extends MeleeWeaponStatsBuilder<T>>
			extends
			WeaponStatsBuilder<T> {
		private Sharpness sharpness;
		private float affinity;
		private float extendedReach;

		public MeleeWeaponStatsBuilder() {
			this.sharpness = Sharpness.DEFAULT_SHARPNESS;
			this.extendedReach = 0;
		}

		public MeleeWeaponStatsBuilder<T> setAffinity(float affinity) {
			this.affinity = affinity;
			return getThis();
		}

		public MeleeWeaponStatsBuilder<T> setExtendedReach(float reachBonus) {
			Preconditions.checkArgument(
					reachBonus >= -3f,
					"reachBonus must be more than -3 (so that the reach will never be less than 0)");
			this.extendedReach = reachBonus;
			return getThis();
		}

		public MeleeWeaponStatsBuilder<T> configureSharpness(Consumer<SharpnessBuilder> config) {
			SharpnessBuilder builder = new SharpnessBuilder();
			config.accept(builder);
			this.sharpness = builder.build();
			return getThis();
		}

		@Override
		public MeleeWeaponStats build() {
			Preconditions.checkState(sharpness != null, "configure sharpness first");
			return new MeleeWeaponStats(this);
		}
	}

	protected MeleeWeaponStats(MeleeWeaponStatsBuilder<?> builder) {
		super(builder);
		this.weaponSharpness = builder.sharpness;
		this.affinity = builder.affinity;
		this.extendedReach = builder.extendedReach;
	}

	private final Sharpness weaponSharpness;
	private final float affinity;
	private final float extendedReach;

}
