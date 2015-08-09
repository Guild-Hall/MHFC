package mhfc.net.common.ai.tigrex;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.actions.AITailWhipGeneral;
import mhfc.net.common.entity.mob.EntityTigrex;
import mhfc.net.common.ai.general.actions.AITailWhipGeneral.ISpinProvider;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.IDamageProvider;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.IWeightProvider;

public class TailWhipTigrex extends AITailWhipGeneral<EntityTigrex> {

	private static final float SELECTION_WEIGHT = 3f;
	private static final double MAX_DISTANCE = 7F;
	private static final double MAX_ANGLE_DOT = 0.2f;
	
	private static final double MIN_DIST = 7f;
	private static final float MAX_ANGLE = 0.2f;
	
	public TailWhipTigrex() {
		super(generateProvider());
	}

	private static ISpinProvider<EntityTigrex> generateProvider() {
		IAnimationProvider anim = new IAnimationProvider.AnimationAdapter(
				"mhfc:models/Tigrex/tailswipe.mcanm", 40);
			IDamageProvider dmg = new IDamageProvider.DamageAdapter(AIUtils.defaultDamageCalc(46, 92, 777));
			IWeightProvider<EntityTigrex> weight = new IWeightProvider.SimpleWeightAdapter<>(SELECTION_WEIGHT);
			ISelectionPredicate<EntityTigrex> pred = new ISelectionPredicate.SelectionAdapter<>(
					-MAX_ANGLE, MAX_ANGLE, MIN_DIST, Double.MAX_VALUE);
			ISpinProvider<EntityTigrex> provide = new AITailWhipGeneral.TailWhipAdapter<>(anim, weight, dmg, pred);
		return provide;
	}

}
