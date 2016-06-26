package mhfc.net.common.ai.entity.deviljho;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.actions.AIGeneralTailWhip;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.IDamageProvider;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.monster.EntityDeviljho;

public class DeviljhoTailWhip extends AIGeneralTailWhip<EntityDeviljho> {

	private static final double MAX_DISTANCE = 8F;
	private static final double MIN_DIST = 0f;
	private static final float MIN_RIGHT_ANGLE = 5f;
	private static final int LAST_FRAME = 55; // CLEANUP exact value here please

	public DeviljhoTailWhip() {
		super(generateProvider());
	}

	private static ISpinProvider<EntityDeviljho> generateProvider() {
		IAnimationProvider anim = new IAnimationProvider.AnimationAdapter("mhfc:models/Deviljho/tailswipe.mcanm",
				LAST_FRAME);
		IDamageProvider dmg = new IDamageProvider.DamageAdapter(AIUtils.defaultDamageCalc(85, 50, 9999999f));
		IWeightProvider<EntityDeviljho> weight = new IWeightProvider.SimpleWeightAdapter<>(5F);
		ISelectionPredicate<EntityDeviljho> pred = new ISelectionPredicate.SelectionAdapter<>(MIN_RIGHT_ANGLE, 180,
				MIN_DIST, MAX_DISTANCE);
		ISpinProvider<EntityDeviljho> provide = new AIGeneralTailWhip.TailWhipAdapter<>(anim, weight, dmg, pred);
		return provide;
	}

}
