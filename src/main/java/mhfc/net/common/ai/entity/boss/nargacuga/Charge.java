package mhfc.net.common.ai.entity.boss.nargacuga;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.SelectionUtils;
import mhfc.net.common.ai.general.actions.AnimatedAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.SwitchLoopAdvancer;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.ai.general.provider.simple.IDamageCalculator;
import mhfc.net.common.ai.general.provider.simple.IFrameAdvancer;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.monster.EntityNargacuga;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;

public class Charge extends AnimatedAction<EntityNargacuga> implements IHasAnimationProvider {

	private static final int ANIMATION_LENGTH = 80;
	private static final String ANIMATION_LOCATION = "mhfc:models/Nargacuga/Charge.mcanm";

	private static final float MIN_DISTANCE = 10;
	private static final float MAX_DISTANCE = 40;
	private static final float MAX_ANGLE = 40;
	private static final int LOOP_START = 21;
	private static final int LOOP_END = 180;
	private static final float WEIGHT = 0;
	private static final IDamageCalculator damageCalc = AIUtils.defaultDamageCalc(280f, 50F, 99999F);

	private final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, ANIMATION_LENGTH);
	private SwitchLoopAdvancer frameAdvancer;

	public Charge() {}

	private boolean shouldSelect() {
		return SelectionUtils.isInDistance(MIN_DISTANCE, MAX_DISTANCE, getEntity(), target)
				&& SelectionUtils.isInViewAngle(-MAX_ANGLE, MAX_ANGLE, getEntity(), target);
	}

	@Override
	protected float computeSelectionWeight() {
		return shouldSelect() ? WEIGHT : DONT_SELECT;
	}

	@Override
	public IFrameAdvancer provideFrameAdvancer() {
		return frameAdvancer = new SwitchLoopAdvancer(LOOP_START, LOOP_END);
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return ANIMATION;
	}

	@Override
	protected void onUpdate() {
		EntityNargacuga nargacuga = getEntity();
		EntityLivingBase target = nargacuga.getAttackTarget();
		if (this.getCurrentFrame() == 5) {
			nargacuga.playSound(MHFCSoundRegistry.getRegistry().nargacugaCharge, 2.0F, 1.0F);
		}
		Vec3d distanceVec = WorldHelper.getVectorToTarget(nargacuga, target);
		if (distanceVec.lengthVector() < MIN_DISTANCE) {
			frameAdvancer.setLoopActive(false);
		}
	}

}
