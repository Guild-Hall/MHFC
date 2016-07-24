package mhfc.net.common.ai.entity.boss.nargacuga;

import mhfc.net.MHFCMain;
import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.general.actions.AIGeneralJumpAttack;
import mhfc.net.common.ai.general.actions.IJumpTimingProvider;
import mhfc.net.common.ai.general.provider.simple.IAnimationProvider;
import mhfc.net.common.ai.general.provider.simple.IJumpParamterProvider;
import mhfc.net.common.ai.general.provider.simple.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.simple.IWeightProvider;
import mhfc.net.common.entity.monster.EntityNargacuga;
import net.minecraft.entity.Entity;

public final class Pounce extends AIGeneralJumpAttack<EntityNargacuga> {

	
	public static enum JumpBehaviour {
		TwoJumps(BehaviourJump.TWO_JUMPS),
		ThreeJump(BehaviourJump.THREE_JUMPS);
		private JumpBehaviour(BehaviourJump internal) {
			this.internal = internal;
		}

		BehaviourJump internal;
	}

	private static final IDamageCalculator dmgCalculator = AIUtils.defaultDamageCalc(112, 300, 5000);

	public static Pounce createNargaPounce(JumpBehaviour jumpBehaviour) {
		return new Pounce(jumpBehaviour.internal);
	}

	private BehaviourJump behaviour;
	private IAnimationProvider animation = new IAnimationProvider.AnimationAdapter("mhfc:models/Nargacuga/Pounce.mcanm", 5);
	private ISelectionPredicate<EntityNargacuga> select;
	private IWeightProvider<EntityNargacuga> weight;
	private IJumpTimingProvider<EntityNargacuga> timing;
	private IJumpParamterProvider<EntityNargacuga> params;

	private Pounce(BehaviourJump behaviour) {
		this.behaviour = behaviour;
		animation = behaviour.getAnimation();
		select = behaviour.getSelectionPredicate();
		weight = behaviour.getWeightProvider();
		timing = behaviour.getJumpTiming();
		params = behaviour.getJumpParameters();
	}

	@Override
	public void beginExecution() {
		super.beginExecution();
		EntityNargacuga entity = getEntity();
		entity.playSound("narga.leapforward", 2.0F, 1.0F);
		MHFCMain.logger().debug("Narga jump {}", this.behaviour);
		
		setToNextFrame(18);
	}

	@Override
	public String getAnimationLocation() {
		return "mhfc:models/Nargacuga/Pounce.mcanm";
	}

	@Override
	public int getAnimationLength() {
		return 68;
	}

	@Override
	public boolean shouldSelectAttack(
			IExecutableAction<? super EntityNargacuga> attack,
			EntityNargacuga actor,
			Entity target) {
		return select.shouldSelectAttack(attack, actor, target);
	}

	@Override
	public float getWeight(EntityNargacuga entity, Entity target) {
		return weight.getWeight(entity, target);
	}

	@Override
	public IDamageCalculator getDamageCalculator() {
		return dmgCalculator;
	}

	@Override
	public float getInitialUpVelocity(EntityNargacuga entity) {
		return params.getInitialUpVelocity(entity);
	}

	@Override
	public float getForwardVelocity(EntityNargacuga entity) {
		return params.getForwardVelocity(entity);
	}

	@Override
	public boolean isJumpFrame(EntityNargacuga entity, int frame) {
		return timing.isJumpFrame(entity, frame);
	}

	@Override
	public boolean isDamageFrame(EntityNargacuga entity, int frame) {
		return timing.isDamageFrame(entity, frame);
	}

	@Override
	public float getTurnRate(EntityNargacuga entity, int frame) {
		return timing.getTurnRate(entity, frame);
	}

}
