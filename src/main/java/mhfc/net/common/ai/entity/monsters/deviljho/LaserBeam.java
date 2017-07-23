package mhfc.net.common.ai.entity.monsters.deviljho;

import mhfc.net.common.ai.general.SelectionUtils;
import mhfc.net.common.ai.general.actions.AnimatedAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.monster.EntityDeviljho;
import mhfc.net.common.entity.projectile.EntityDeviljhoLaserBeam;

public class LaserBeam extends AnimatedAction<EntityDeviljho> implements IHasAnimationProvider {

	protected String animLocation;
	protected int animLength;
	protected float maxDistance;
	protected float weight;

	public LaserBeam(String animLocation, int animLength, float maxDistance, float weight) {
		this.animLocation = animLocation;
		this.animLength = animLength;
		this.maxDistance = maxDistance;
		this.weight = weight;

	}


	@Override
	public IAnimationProvider getAnimProvider() {
		return new AnimationAdapter(this, "mhfc:models/deviljho/deviljhofrontalbreathe.mcanm", 80);
	}

	@Override
	protected float computeSelectionWeight() {
		EntityDeviljho entity = this.getEntity();
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
		EntityDeviljho entity = this.getEntity();
		target = entity.getAttackTarget();
		if (target != null) {
			entity.getTurnHelper().updateTurnSpeed(30F);
			entity.getTurnHelper().updateTargetPoint(target);
		}
		if (this.getCurrentFrame() == 18) {
			entity.playSound(MHFCSoundRegistry.getRegistry().deviljhoDragonBreath, 2.0F, 1.0F);
		}
		if (this.getCurrentFrame() == 40 && entity.getAttackTarget() == null) {
			for (int i = 0; i < 3; ++i) {
				EntityDeviljhoLaserBeam beam = new EntityDeviljhoLaserBeam(entity.world);
				entity.world.spawnEntity(beam);
			}
		}
	}

}
