package mhfc.net.common.ai.entity.boss.nargacuga;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.actions.JumpAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.AttackTargetAdapter;
import mhfc.net.common.ai.general.provider.adapters.DamageAdapter;
import mhfc.net.common.ai.general.provider.adapters.JumpAdapter;
import mhfc.net.common.ai.general.provider.adapters.JumpTimingAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.composite.IJumpProvider;
import mhfc.net.common.ai.general.provider.impl.IHasJumpProvider;
import mhfc.net.common.ai.general.provider.simple.IDamageCalculator;
import mhfc.net.common.ai.general.provider.simple.IJumpParameterProvider;
import mhfc.net.common.ai.general.provider.simple.IJumpTimingProvider;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.monster.EntityNargacuga;

public class BackOff extends JumpAction<EntityNargacuga> implements IHasJumpProvider<EntityNargacuga> {

	private static final int ANIMATION_LENGTH = 50;
	private static final String ANIMATION_LOCATION = "mhfc:models/Nargacuga/JumpBack.mcanm";

	private static final int JUMP_FRAME = 23;
	private static final float JUMP_TIME = 12;
	private static final float TURN_RATE = 2.5f;
	private static final float TURN_RATE_AIR = 1.5f;
	private static final float BACK_OFF_SPEED = -1.7f;

	private static final float WEIGHT = 1;
	private static final IDamageCalculator DAMAGE_CALC = AIUtils.defaultDamageCalc(50, 250, 70);

	private final JumpAdapter<EntityNargacuga> JUMP_ADAPTER;

	{
		IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, ANIMATION_LENGTH);
		IJumpParameterProvider<EntityNargacuga> jumpProvider = new AttackTargetAdapter<EntityNargacuga>(JUMP_TIME) {
			@Override
			public float getForwardVelocity(EntityNargacuga entity) {
				return BACK_OFF_SPEED;
			}
		};
		IJumpTimingProvider<EntityNargacuga> jumpTiming = new JumpTimingAdapter<>(JUMP_FRAME, TURN_RATE, TURN_RATE_AIR);
		JUMP_ADAPTER = new JumpAdapter<>(ANIMATION, new DamageAdapter(DAMAGE_CALC), jumpProvider, jumpTiming);
	}

	public BackOff() {}

	@Override
	protected float computeSelectionWeight() {
		EntityNargacuga entity = this.getEntity();
		target = entity.getAttackTarget();
		if(target == null){
			return DONT_SELECT;
		}
		return WEIGHT;
	}

	@Override
	public IJumpProvider<EntityNargacuga> getJumpProvider() {
		return JUMP_ADAPTER;
	}

	@Override
	public void onUpdate() {
		EntityNargacuga entity = getEntity();
		if (this.getCurrentFrame() == 5) {
			entity.playSound(MHFCSoundRegistry.getRegistry().nargacugaBackOff, 2.0F, 1.0F);
		}
	}

}
