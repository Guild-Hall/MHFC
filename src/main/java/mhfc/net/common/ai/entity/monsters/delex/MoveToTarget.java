package mhfc.net.common.ai.entity.monsters.delex;

import mhfc.net.common.ai.general.WeightUtils;
import mhfc.net.common.ai.general.actions.AnimatedAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.CountLoopAdvancer;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.ai.general.provider.simple.IFrameAdvancer;
import mhfc.net.common.entity.monster.EntityDelex;

public class MoveToTarget extends AnimatedAction<EntityDelex> implements IHasAnimationProvider {

	protected float speedln;

	private final IAnimationProvider ANIMATION = new AnimationAdapter(
			this,
			"mhfc:models/delex/delexmovetotarget.mcanm",
			100);

	public MoveToTarget(float speed) {
		this.speedln = speed;

	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return ANIMATION;
	}

	@Override
	public IFrameAdvancer provideFrameAdvancer() {
		return new CountLoopAdvancer(0, 94, 1);
	}

	@Override
	protected float computeSelectionWeight() {
		EntityDelex entity = getEntity();
		target = entity.getAttackTarget();

		if (entity.getAttackTarget() == null) {
			return DONT_SELECT;
			}
			float targetpoint = entity.getDistanceToEntity(target);
		if (targetpoint > 35F) {
				return DONT_SELECT;
			}
		return WeightUtils.random(rng(), 7f);
	}

	@Override
	protected void onUpdate() {
		EntityDelex entity = getEntity();
		target = entity.getAttackTarget();
		if (target != null) {
			entity.getTurnHelper().updateTurnSpeed(15F);
			entity.getTurnHelper().updateTargetPoint(target);
			entity.getNavigator().tryMoveToXYZ(target.posX + 4, target.posY, target.posZ + 4, this.speedln);
		}
	}

}
