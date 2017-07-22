package mhfc.net.common.ai.entity.monsters.deviljho;

import mhfc.net.common.ai.general.SelectionUtils;
import mhfc.net.common.ai.general.actions.AnimatedAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.monster.EntityDeviljho;
import mhfc.net.common.entity.projectile.EntityBreathe;

public class FrontalBreathe extends AnimatedAction<EntityDeviljho> implements IHasAnimationProvider {


	public FrontalBreathe() {}

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
		return 6F;
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return new AnimationAdapter(this, "mhfc:models/Deviljho/DeviljhoFrontalBreathe.mcanm", 80);
	}

	@Override
	protected void onUpdate() {
		EntityDeviljho entity = this.getEntity();
		if (this.getCurrentFrame() == 18) {
			entity.playSound(MHFCSoundRegistry.getRegistry().deviljhoDragonBreath, 2.0F, 1.0F);
		}
		if (this.getCurrentFrame() == 40 && entity.getAttackTarget() == null) {
			for (int i = 0; i < 3; ++i) {
				EntityBreathe set_Breathe = new EntityBreathe(entity.world, entity, true);
				entity.world.spawnEntity(set_Breathe);
			}
		}
	}

}
