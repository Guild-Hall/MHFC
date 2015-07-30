package mhfc.net.common.ai.tigrex;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.general.attacks.JumpAttack;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.IDamageProvider;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.mob.EntityTigrex;

public class JumpTigrex extends JumpAttack<EntityTigrex> {

	private static final int LAST_FRAME = 80;
	private static final int JUMP_FRAME = 20;
	private static final float TURN_RATE = 4;
	private static final float JUMP_SCALE = 0.17f;
	private static final float MAX_SCALE = 4f;
	private static final float JUMP_HEIGHT = 0.7f;

	private static final IDamageCalculator damageCalc = AIUtils
		.defaultDamageCalc(32f, 62f, 500f);
	private static final double MIN_DIST = 3f;
	private static final float MAX_ANGLE = 45f;
	private static final float SELECTION_WEIGHT = 5f;

	public JumpTigrex() {
		super(generateProvider());
	}

	private static IJumpProvider<EntityTigrex> generateProvider() {
		IAnimationProvider anim = new IAnimationProvider.AnimationAdapter(
			"mhfc:models/Tigrex/jump.mcanm", LAST_FRAME);
		IDamageProvider dmg = new IDamageProvider.DamageAdapter(damageCalc);
		ISelectionPredicate<EntityTigrex> pred = new ISelectionPredicate.SelectionAdapter<>(
			-MAX_ANGLE, MAX_ANGLE, MIN_DIST, Double.MAX_VALUE);
		IWeightProvider<EntityTigrex> weight = new IWeightProvider.SimpleWeightAdapter<>(
			SELECTION_WEIGHT);
		IJumpProvider<EntityTigrex> jmp = new JumpAttack.JumpAdapter<>(anim,
			pred, weight, dmg, JUMP_SCALE, MAX_SCALE, JUMP_HEIGHT, JUMP_FRAME,
			TURN_RATE);
		return jmp;
	}
}
