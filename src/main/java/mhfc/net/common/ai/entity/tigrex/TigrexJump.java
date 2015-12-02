package mhfc.net.common.ai.entity.tigrex;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.general.actions.AIGeneralJumpAttack;
import mhfc.net.common.ai.general.provider.*;
import mhfc.net.common.entity.monster.EntityTigrex;

public class TigrexJump extends AIGeneralJumpAttack<EntityTigrex> {

	private static final int LAST_FRAME = 50;
	private static final int JUMP_FRAME = 20;
	private static final float TURN_RATE = 14;
	private static final float JUMP_TIME = 16f;

	private static final IDamageCalculator damageCalc = AIUtils
		.defaultDamageCalc(40f, 62f, 1500f);
	private static final double MIN_DIST = 6f;
	private static final double MAX_DIST = 60f;
	private static final float MAX_ANGLE = 140f;
	private static final float SELECTION_WEIGHT = 1f;

	public TigrexJump() {
		super(generateProvider());
	}

	private static IJumpProvider<EntityTigrex> generateProvider() {
		IAnimationProvider anim = new IAnimationProvider.AnimationAdapter(
			"mhfc:models/Tigrex/jump.mcanm", LAST_FRAME);
		IDamageProvider dmg = new IDamageProvider.DamageAdapter(damageCalc);
		ISelectionPredicate<EntityTigrex> pred = new ISelectionPredicate.SelectionAdapter<>(
			-MAX_ANGLE, MAX_ANGLE, MIN_DIST, MAX_DIST);
		IWeightProvider<EntityTigrex> weight = new IWeightProvider.SimpleWeightAdapter<>(
			SELECTION_WEIGHT);
		IJumpParamterProvider<EntityTigrex> params = new IJumpParamterProvider.AttackTargetAdapter<>(
			JUMP_TIME);
		IJumpTimingProvider<EntityTigrex> timing = new IJumpTimingProvider.JumpTimingAdapter<EntityTigrex>(
			JUMP_FRAME, TURN_RATE);
		IJumpProvider<EntityTigrex> jmp = new AIGeneralJumpAttack.JumpAdapter<>(
			anim, pred, weight, dmg, params, timing);
		return jmp;
	}
}
