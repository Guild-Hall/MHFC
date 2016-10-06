package mhfc.net.common.ai.entity.boss.deviljho;

import mhfc.net.common.ai.general.SelectionUtils;
import mhfc.net.common.ai.general.actions.AnimatedAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.monster.EntityDeviljho;
import mhfc.net.common.entity.projectile.EntityBreathe;

public class FrontalBreathe extends AnimatedAction<EntityDeviljho> implements IHasAnimationProvider {

	private static final int LAST_FRAME = 80;
	private static final String ANIMATION_LOCATION = "mhfc:models/Deviljho/DeviljhoFrontalBreathe.mcanm";
	private static final double MAX_DISTANCE = 15f;
	private static final float WEIGHT = 6F;

	private final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, LAST_FRAME);

	public FrontalBreathe() {}

	@Override
	protected float computeSelectionWeight() {
		return SelectionUtils.isInDistance(0, MAX_DISTANCE, getEntity(), target) ? WEIGHT : DONT_SELECT;
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return ANIMATION;
	}

	@Override
	protected void onUpdate() {
		EntityDeviljho entity = this.getEntity();
		if (this.getCurrentFrame() == 18) {
			entity.playSound(MHFCSoundRegistry.getRegistry().deviljhoDragonBreath, 2.0F, 1.0F);
		}
		if (this.getCurrentFrame() == 40 && entity.getAttackTarget() == null) {
			for (int i = 0; i < 3; ++i) {
				EntityBreathe set_Breathe = new EntityBreathe(entity.worldObj, entity, true);
				entity.worldObj.spawnEntityInWorld(set_Breathe);
			}
		}
	}

}
