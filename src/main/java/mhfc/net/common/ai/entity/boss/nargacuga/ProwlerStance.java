package mhfc.net.common.ai.entity.boss.nargacuga;

import mhfc.net.common.ai.general.actions.AnimatedAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.entity.monster.EntityNargacuga;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.util.math.Vec3d;

public class ProwlerStance extends AnimatedAction<EntityNargacuga> implements IHasAnimationProvider {

	private static final String ANIMATION_LOCATION = "mhfc:models/Nargacuga/Pounce.mcanm";
	private static final int ANIMATION_LENGTH = 18;

	private static final float MAX_ANGLE = 40;
	private static final float MAX_DISTANCE = 40;
	private static final float WEIGHT = 15;

	private final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, ANIMATION_LENGTH);

	public ProwlerStance() {}


	@Override
	protected float computeSelectionWeight() {
		EntityNargacuga e = this.getEntity();
		target = e.getAttackTarget();
		if (target == null) {
			return DONT_SELECT;
		}
		Vec3d toTarget = WorldHelper.getVectorToTarget(e, target);
		double dist = toTarget.lengthVector();
		if (dist > MAX_DISTANCE) {
			return DONT_SELECT;
		}
		return WEIGHT;
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return ANIMATION;
	}
}
