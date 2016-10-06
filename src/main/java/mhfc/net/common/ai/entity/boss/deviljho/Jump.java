package mhfc.net.common.ai.entity.boss.deviljho;

import mhfc.net.common.ai.entity.AIGameplayComposition;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.SelectionUtils;
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
import mhfc.net.common.ai.general.provider.simple.IDamageProvider;
import mhfc.net.common.ai.general.provider.simple.IJumpParameterProvider;
import mhfc.net.common.ai.general.provider.simple.IJumpTimingProvider;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.monster.EntityDeviljho;

public class Jump extends JumpAction<EntityDeviljho> implements IHasJumpProvider<EntityDeviljho> {

	private static final int FRAMES = 60;
	private static final String ANIMATION_LOCATION = "mhfc:models/Deviljho/DeviljhoJump.mcanm";
	private static final int JUMPFRAME = 20;

	private static final float TURNRATE = 14;
	private static final float JUMPDURATION = 12f;

	private static final IDamageCalculator DAMAGEBASE = AIUtils.defaultDamageCalc(105f, 2000f, 999999F);
	private static final double DISTANCEMIN = 6F;
	private static final double DISTANCEMAX = 15F;
	private static final float ANGLE_DEGREES = 140f;
	private static final float WEIGHT = 1f;

	private final IJumpProvider<EntityDeviljho> JUMP_PROVIDER;
	{
		IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, FRAMES);
		IDamageProvider DAMAGE = new DamageAdapter(DAMAGEBASE);
		IJumpParameterProvider<EntityDeviljho> PARAMETERS = new AttackTargetAdapter<>(JUMPDURATION);
		IJumpTimingProvider<EntityDeviljho> TIMING_PARAMS = new JumpTimingAdapter<>(JUMPFRAME, TURNRATE, 0);
		JUMP_PROVIDER = new JumpAdapter<>(ANIMATION, DAMAGE, PARAMETERS, TIMING_PARAMS);
	}

	private boolean thrown = false;

	public Jump() {}

	private boolean shouldSelection() {
		return SelectionUtils.isInDistance(DISTANCEMIN, DISTANCEMAX, getEntity(), target)
				&& SelectionUtils.isInViewAngle(-ANGLE_DEGREES, ANGLE_DEGREES, getEntity(), target);
	}

	@Override
	protected float computeSelectionWeight() {
		return shouldSelection() ? WEIGHT : DONT_SELECT;
	}

	@Override
	public IJumpProvider<EntityDeviljho> getJumpProvider() {
		return JUMP_PROVIDER;
	}

	@Override
	public void onUpdate() {
		EntityDeviljho entity = this.getEntity();
		if (this.getCurrentFrame() == 5) {
			entity.playSound(MHFCSoundRegistry.getRegistry().deviljhoLeap, 2.0F, 1.0F);
		}
		if (!entity.onGround || thrown || this.getCurrentFrame() < 30) {
			return;
		}
		AIGameplayComposition.stompCracks(entity, 200);
		thrown = true;
	}
}
