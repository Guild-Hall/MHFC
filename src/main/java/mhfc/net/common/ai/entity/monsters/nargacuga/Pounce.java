package mhfc.net.common.ai.entity.monsters.nargacuga;

import mhfc.net.MHFCMain;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.SelectionUtils;
import mhfc.net.common.ai.general.actions.JumpAction;
import mhfc.net.common.ai.general.provider.adapters.DamageAdapter;
import mhfc.net.common.ai.general.provider.adapters.JumpAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.composite.IJumpProvider;
import mhfc.net.common.ai.general.provider.impl.IHasJumpProvider;
import mhfc.net.common.ai.general.provider.simple.IDamageCalculator;
import mhfc.net.common.ai.general.provider.simple.IJumpParameterProvider;
import mhfc.net.common.ai.general.provider.simple.IJumpTimingProvider;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.creature.Nargacuga;

import java.util.function.Function;

public final class Pounce extends JumpAction<Nargacuga> implements IHasJumpProvider<Nargacuga> {

	public static enum JumpBehaviour {
		TwoJumps(BehaviourJump::createTwoJumps),
		ThreeJump(BehaviourJump::createThreeJumps);

		public Function<Pounce, BehaviourJump> internal;

		private JumpBehaviour(Function<Pounce, BehaviourJump> internal) {
			this.internal = internal;
		}
	}

	private static final JumpBehaviour[] ALL_BEHAVIOURS = JumpBehaviour.values();
	private static final IDamageCalculator dmgCalculator = AIUtils.defaultDamageCalc(75F, 300, 5000);

	private BehaviourJump behaviour;

	private IJumpProvider<Nargacuga> jumpProvider;

	public Pounce() {}

	@Override
	protected void initializeExecutionRandomness() {
		super.initializeExecutionRandomness();
		this.behaviour = ALL_BEHAVIOURS[rng().nextInt(ALL_BEHAVIOURS.length)].internal.apply(this);
		IAnimationProvider animation = behaviour.getAnimation();
		IJumpParameterProvider<Nargacuga> jumpParameters = behaviour.getJumpParameters();
		IJumpTimingProvider<Nargacuga> jumpTiming = behaviour.getJumpTiming();

		jumpProvider = new JumpAdapter<>(animation, new DamageAdapter(dmgCalculator), jumpParameters, jumpTiming);
	}

	@Override
	public void beginExecution() {
		super.beginExecution();
		Nargacuga entity = getEntity();
		entity.playSound(MHFCSoundRegistry.getRegistry().nargacugaPounce, 2.0F, 1.0F);
		MHFCMain.logger().debug("Narga jump {}", this.behaviour);
		forceNextFrame(18);
		System.out.println("Pounce is working");
	}

	@Override
	protected float computeSelectionWeight() {
		Nargacuga entity = getEntity();
		target = entity.getAttackTarget();
		if (SelectionUtils.isIdle(entity)) {
			return DONT_SELECT;
		}

		//		if (!SelectionUtils.isInDistance(0, 25, entity, target)) {return DONT_SELECT;}
		return 10F;
	}

	@Override
	protected void onUpdate() {
		super.onUpdate();
		damageCollidingEntities();
	}

	@Override
	public IJumpProvider<Nargacuga> getJumpProvider() {
		return jumpProvider;
	}
}
