package mhfc.net.common.ai.entity.boss.nargacuga;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.general.actions.AIGeneralJumpAttack;
import mhfc.net.common.ai.general.actions.IJumpTimingProvider;
import mhfc.net.common.ai.general.provider.simple.IJumpParamterProvider;
import mhfc.net.common.ai.general.provider.simple.ISelectionPredicate;
import mhfc.net.common.entity.monster.EntityNargacuga;
import net.minecraft.entity.Entity;

public class BackOff extends AIGeneralJumpAttack<EntityNargacuga> {

	private static final String ANIMATION_LOCATION = "mhfc:models/Nargacuga/JumpBack.mcanm";
	private static final int ANIMATION_LENGTH = 50;
	private static final float WEIGHT = 1;
	private static final float JUMP_TIME = 12;
	private static final float ANGLE = 40;
	private static final int JUMP_FRAME = 23;
	private static final float TURN_RATE = 2.5f;
	private static final float TURN_RATE_AIR = 1.5f;
	private static final float BACK_OFF_SPEED = -8.5f;

	private static final IDamageCalculator calculator;
	private static final IJumpTimingProvider<EntityNargacuga> jumpTiming;
	private static final ISelectionPredicate<EntityNargacuga> predicate;
	private static final IJumpParamterProvider<EntityNargacuga> jumpProvider;

	static {
		calculator = AIUtils.defaultDamageCalc(50, 250, 70);
		predicate = new ISelectionPredicate.AngleAdapter<>(ANGLE, -ANGLE);
		jumpProvider = new IJumpParamterProvider.AttackTargetAdapter<EntityNargacuga>(JUMP_TIME) {
			@Override
			public float getForwardVelocity(EntityNargacuga entity) {
				return BACK_OFF_SPEED;
			}
		};
		jumpTiming = new IJumpTimingProvider.JumpTimingAdapter<EntityNargacuga>(JUMP_FRAME, TURN_RATE, TURN_RATE_AIR);
	}

	public BackOff() {}

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
	
	@Override
	public void update(){
		EntityNargacuga entity = getEntity();
		if(this.getCurrentFrame() == 5)
		entity.playSound("mhfc:narga.leapback", 2.0F, 1.0F);
	}

	@Override
	public float getInitialUpVelocity(EntityNargacuga entity) {
		return jumpProvider.getInitialUpVelocity(entity);
	}

	@Override
	public float getForwardVelocity(EntityNargacuga entity) {
		return jumpProvider.getInitialUpVelocity(entity);
	}

	@Override
	public boolean isJumpFrame(EntityNargacuga entity, int frame) {
		return jumpTiming.isJumpFrame(entity, frame);
	}

	@Override
	public boolean isDamageFrame(EntityNargacuga entity, int frame) {
		return jumpTiming.isDamageFrame(entity, frame);
	}

	@Override
	public float getTurnRate(EntityNargacuga entity, int frame) {
		return jumpTiming.getTurnRate(entity, frame);
	}

}
