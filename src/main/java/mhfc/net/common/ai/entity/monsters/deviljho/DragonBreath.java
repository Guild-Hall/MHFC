package mhfc.net.common.ai.entity.monsters.deviljho;

import mhfc.net.common.ai.general.actions.AnimatedAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.entity.fx.EntityDeviljhoLaserBreath;
import mhfc.net.common.entity.monster.EntityDeviljho;
import mhfc.net.common.entity.projectile.EntityDeviljhoBeam1;

public class DragonBreath extends AnimatedAction<EntityDeviljho> implements IHasAnimationProvider {

	protected String animLocation;
	protected int animLength;
	protected float maxDistance;
	private EntityDeviljhoBeam1 beam;
	protected float weight;

	public DragonBreath(String animLocation, int animLength, float maxDistance, float weight) {
		this.animLocation = animLocation;
		this.animLength = animLength;
		this.maxDistance = maxDistance;
		this.weight = weight;

	}


	@Override
	public IAnimationProvider getAnimProvider() {
		return new AnimationAdapter(this, "mhfc:models/deviljho/laserfront.mcanm", 71);
	}

	@Override
	protected float computeSelectionWeight() {
		EntityDeviljho entity = this.getEntity();
		target = entity.getAttackTarget();
		/*	if (SelectionUtils.isIdle(entity)) {
				return DONT_SELECT;
			}
			if (!SelectionUtils.isInDistance(0, 15, entity, target)) {
				return DONT_SELECT;
			} */
		return 25;
	}

	@Override
	protected void onUpdate() {
		EntityDeviljho entity = this.getEntity();
		target = entity.getAttackTarget();


		if (target != null) {
			entity.getTurnHelper().updateTurnSpeed(30F);
			entity.getTurnHelper().updateTargetPoint(target);
		}
		if (this.getCurrentFrame() == 30 /*&& entity.getAttackTarget() != null*/) {
			EntityDeviljhoLaserBreath breath = new EntityDeviljhoLaserBreath(entity.world, entity);
				breath.setPositionAndRotation(
						entity.posX + 4F,
					entity.posY + 3F,
						entity.posZ,
						entity.rotationYawHead,
						entity.rotationPitch);
			if (!entity.world.isRemote)
				entity.world.spawnEntity(breath);
			}
	}

}
