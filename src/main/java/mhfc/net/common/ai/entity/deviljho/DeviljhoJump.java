package mhfc.net.common.ai.entity.deviljho;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.general.actions.AIGeneralJumpAttack;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.IDamageProvider;
import mhfc.net.common.ai.general.provider.IJumpParamterProvider;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.monster.EntityDeviljho;

public class DeviljhoJump extends AIGeneralJumpAttack<EntityDeviljho> {

	private static final int set_FRAME = 60;
	private static final int set_JUMPFRAME = 20;
	private static final float set_TURNRATE = 14;
	private static final float set_JUMPDURATION = 16f;

	private static final IDamageCalculator set_DAMAGEBASE = AIUtils.defaultDamageCalc(105f, 2000f, 999999F);
	private static final double set_DISTANCEMINIMUM = 6F;
	private static final double set_DISTANCEMAX = 15F;
	private static final float set_ANGLETHETA = 140f;
	private static final float set_ARITHMETICWEIGHT = 1f;

	public DeviljhoJump() {
		super(generateProvider());
	}

	private static IJumpProvider<EntityDeviljho> generateProvider() {
		IAnimationProvider set_ANIMATION = new IAnimationProvider.AnimationAdapter(
				"mhfc:models/Deviljho/DeviljhoJump.mcanm",
				set_FRAME);
		IDamageProvider set_DAMAGE = new IDamageProvider.DamageAdapter(set_DAMAGEBASE);
		ISelectionPredicate<EntityDeviljho> set_PREDICATE = new ISelectionPredicate.SelectionAdapter<>(
				-set_ANGLETHETA,
				set_ANGLETHETA,
				set_DISTANCEMINIMUM,
				set_DISTANCEMAX);
		IWeightProvider<EntityDeviljho> set_ARITHWEIGHT = new IWeightProvider.SimpleWeightAdapter<>(
				set_ARITHMETICWEIGHT);
		IJumpParamterProvider<EntityDeviljho> set_ADAPTERVAR = new IJumpParamterProvider.AttackTargetAdapter<>(
				set_JUMPDURATION);
		IJumpTimingProvider<EntityDeviljho> set_COUNTIME = new IJumpTimingProvider.JumpTimingAdapter<EntityDeviljho>(
				set_JUMPFRAME,
				set_TURNRATE,
				0);
		IJumpProvider<EntityDeviljho> set_JUMPPROVIDER = new AIGeneralJumpAttack.JumpAdapter<>(
				set_ANIMATION,
				set_PREDICATE,
				set_ARITHWEIGHT,
				set_DAMAGE,
				set_ADAPTERVAR,
				set_COUNTIME);
		return set_JUMPPROVIDER;
	}
}
