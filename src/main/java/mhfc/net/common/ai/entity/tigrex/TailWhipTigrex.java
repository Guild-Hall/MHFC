package mhfc.net.common.ai.entity.tigrex;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.actions.AIGeneralTailWhip;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.IDamageProvider;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.mob.EntityTigrex;

public class TailWhipTigrex extends AIGeneralTailWhip<EntityTigrex> {

	private static final double MAX_DISTANCE = 7F;
	private static final double MIN_DIST = 0f;
	private static final float MIN_RIGHT_ANGLE = 10f;
	private static final int LAST_FRAME = 70; // CLEANUP exact value here please

	public TailWhipTigrex() {
		super(generateProvider());
	}

	private static ISpinProvider<EntityTigrex> generateProvider() {
		IAnimationProvider anim = new IAnimationProvider.AnimationAdapter(
			"mhfc:models/Tigrex/tailswipe.mcanm", LAST_FRAME);
		IDamageProvider dmg = new IDamageProvider.DamageAdapter(AIUtils
			.defaultDamageCalc(46, 92, 777));
		IWeightProvider<EntityTigrex> weight = new IWeightProvider.SimpleWeightAdapter<>(
			7F);
		ISelectionPredicate<EntityTigrex> pred = new ISelectionPredicate.SelectionAdapter<>(
			MIN_RIGHT_ANGLE, 180, MIN_DIST, MAX_DISTANCE);
		ISpinProvider<EntityTigrex> provide = new AIGeneralTailWhip.TailWhipAdapter<>(
			anim, weight, dmg, pred);
		return provide;
	}

}
