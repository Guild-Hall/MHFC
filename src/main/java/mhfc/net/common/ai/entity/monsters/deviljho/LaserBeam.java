package mhfc.net.common.ai.entity.monsters.deviljho;


import mhfc.net.common.ai.general.SelectionUtils;
import mhfc.net.common.ai.general.actions.AnimatedAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.entity.monster.EntityDeviljho;
import mhfc.net.common.entity.projectile.EntityBeam;

public class LaserBeam extends AnimatedAction<EntityDeviljho> implements IHasAnimationProvider {

	private EntityBeam solarBeam;

	public LaserBeam() {}

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
		return 15F;
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return new AnimationAdapter(this, "mhfc:models/Deviljho/DeviljhoFrontalBreathe.mcanm", 80);
	}

	@Override
	protected void onUpdate() {
		super.onUpdate();
		float radius1 = 1.7f;
		EntityDeviljho entity = this.getEntity();

			solarBeam = new EntityBeam(
					entity.world,
					entity,
					entity.posX + radius1 * Math.sin(-entity.rotationYaw * Math.PI / 180),
					entity.posY + 1.4,
					entity.posZ + radius1 * Math.cos(-entity.rotationYaw * Math.PI / 180),
					(float) ((entity.rotationYawHead + 90) * Math.PI / 180),
					(float) (-entity.rotationPitch * Math.PI / 180),
					55);
			entity.world.spawnEntity(solarBeam);
		if (this.getCurrentFrame() == 40 && entity.getAttackTarget() != null && !entity.world.isRemote) {

			EntityBeam set_Breathe = new EntityBeam(
					entity.world,
					entity,
					entity.posX + 1.7f * Math.sin(-entity.rotationYaw * Math.PI / 180),
					entity.posY + 1.4,
					entity.posZ + 1.7f * Math.cos(-entity.rotationYaw * Math.PI / 180),
					(float) ((entity.rotationYawHead + 90) * Math.PI / 180),
					(float) (-entity.rotationPitch * Math.PI / 180),
					55);
				entity.world.spawnEntity(set_Breathe);
		}
	}

}
