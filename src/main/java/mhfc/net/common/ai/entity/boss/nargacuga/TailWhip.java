package mhfc.net.common.ai.entity.boss.nargacuga;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.general.actions.AIGeneralTailWhip;
import mhfc.net.common.ai.general.provider.simple.ISelectionPredicate;
import mhfc.net.common.entity.monster.EntityNargacuga;
import net.minecraft.entity.Entity;

public class TailWhip extends AIGeneralTailWhip<EntityNargacuga> {

	private static final String ANIMATION_LOCATION = "mhfc:models/Nargacuga/TailSwipeRight.mcanm";
	private static final int ANIMATION_LENGTH = 0;
	private static final float WEIGHT = 2;
	private static final float MIN_ANGLE = 0;
	private static final float MAX_ANGLE = -150;
	private static final float MAX_DISTANCE = 4;

	private static final IDamageCalculator calculator;
	private static final ISelectionPredicate<EntityNargacuga> predicate;

	static {
		calculator = AIUtils.defaultDamageCalc(100, 500, 3333333);
		predicate = new ISelectionPredicate.SelectionAdapter<>(MIN_ANGLE, MAX_ANGLE, 0, MAX_DISTANCE);
	}

	public TailWhip() {}

	@Override
	public String getAnimationLocation() {
		return ANIMATION_LOCATION;
	}

	@Override
	public int getAnimationLength() {
		return ANIMATION_LENGTH;
	}

	@Override
	public boolean shouldSelectAttack(
			IExecutableAction<? super EntityNargacuga> attack,
			EntityNargacuga actor,
			Entity target) {
		return predicate.shouldSelectAttack(attack, actor, target);
	}

	@Override
	public float getWeight(EntityNargacuga entity, Entity target) {
		return WEIGHT;
	}

	@Override
	public IDamageCalculator getDamageCalculator() {
		return calculator;
	}

}
