package mhfc.net.common.ai.entity.monsters.deviljho;

import mhfc.net.common.ai.general.SelectionUtils;
import mhfc.net.common.ai.general.actions.AnimatedAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.entity.creature.Deviljho;
import mhfc.net.common.entity.fx.FXDeviljhoLaser;

public class DragonBreath extends AnimatedAction<Deviljho> implements IHasAnimationProvider {

	protected String animLocation;
	protected int animLength;
	protected float maxDistance;
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
		Deviljho entity = this.getEntity();
		target = entity.getAttackTarget();
			if (SelectionUtils.isIdle(entity)) {
				return DONT_SELECT;
			}
			if (!SelectionUtils.isInDistance(0, 15, entity, target)) {
				return DONT_SELECT;
		}
		return 25;
	}

	@Override
	protected void onUpdate() {
		Deviljho entity = this.getEntity();
		target = entity.getAttackTarget();


		if (target != null) {
			entity.getTurnHelper().updateTurnSpeed(30F);
			entity.getTurnHelper().updateTargetPoint(target);
			entity.getLookHelper().setLookPositionWithEntity(target, 15, 15);

			if (this.getCurrentFrame() == 30) {
			FXDeviljhoLaser breath = new FXDeviljhoLaser(entity.world, entity);
				breath.setCasterID(entity.getEntityId());
				breath.setPositionAndRotation(
						entity.posX + 4F,
					entity.posY + 3F,
						entity.posZ,
						entity.rotationYawHead,
						entity.rotationPitch);
				if (breath != null) {
					breath.setPositionAndRotation(
							entity.posX + 4F,
							entity.posY + 3F,
							entity.posZ,
							entity.rotationYawHead,
							entity.rotationPitch);
				}
			if (!entity.world.isRemote)
				entity.world.spawnEntity(breath);
			}
		}
	}

}
