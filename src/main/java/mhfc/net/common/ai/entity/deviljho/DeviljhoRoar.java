package mhfc.net.common.ai.entity.deviljho;

import mhfc.net.common.ai.general.actions.AIGeneralRoar;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.mob.EntityDeviljho;

public class DeviljhoRoar extends AIGeneralRoar<EntityDeviljho> {
	private static final int LAST_FRAME = 70;

	public DeviljhoRoar() {
		super(generateProvider());
	}

	private static IRoarProvider<EntityDeviljho> generateProvider() {
		IAnimationProvider anim = new IAnimationProvider.AnimationAdapter(
			"mhfc:models/Deviljho/DeviljhoRoar.mcanm", LAST_FRAME);
		ISelectionPredicate<EntityDeviljho> select = new ISelectionPredicate.SelectAlways<EntityDeviljho>();
		IWeightProvider<EntityDeviljho> weight = new IWeightProvider.RandomWeightAdapter<>(
			1F);
		IRoarProvider<EntityDeviljho> provide = new AIGeneralRoar.RoarAdapter<>(
			anim, select, weight, "mhfc:tigrex-roar");
		return provide;
	}

	@Override
	public void update() {
		super.update();
		EntityDeviljho entity = this.getEntity();
		target = entity.getAttackTarget();
		if (this.getCurrentFrame() >= 18 && this.getCurrentFrame() <= 22) {
			entity.getTurnHelper().updateTargetPoint(target);
			entity.getTurnHelper().updateTurnSpeed(30.0f);
		}
	}
}
