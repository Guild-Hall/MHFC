package mhfc.net.common.ai.entity.monsters.deviljho;

import mhfc.net.common.ai.general.actions.RoarAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.RoarAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.ai.general.provider.simple.IRoarProvider;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.creature.Deviljho;

public class Roar extends RoarAction<Deviljho> implements IHasAnimationProvider {


	public Roar() {}


	@Override
	public IAnimationProvider getAnimProvider() {
		return new AnimationAdapter(this, "mhfc:models/Deviljho/DeviljhoRoar.mcanm", 70);
	}

	@Override
	public IRoarProvider provideRoarBehaviour() {
		return new RoarAdapter(MHFCSoundRegistry.getRegistry().deviljhoRoar, true);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		Deviljho entity = this.getEntity();
		target = entity.getAttackTarget();
		if (this.getCurrentFrame() >= 18 && this.getCurrentFrame() <= 22) {
			entity.getTurnHelper().updateTargetPoint(target);
			entity.getTurnHelper().updateTurnSpeed(30.0f);
		}
	}
}
