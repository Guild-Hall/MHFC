package mhfc.net.common.ai.entity.barroth;

import mhfc.net.common.ai.general.actions.AIGeneralRoar;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.mob.EntityBarroth;

public class BarrothRoar extends AIGeneralRoar<EntityBarroth>{
	private static final int LAST_FRAME = 105;

	public BarrothRoar() {
		super(generateProvider());
	}

	private static IRoarProvider<EntityBarroth> generateProvider() {
		IAnimationProvider anim = new IAnimationProvider.AnimationAdapter(
			"mhfc:models/Barroth/BarrothRoar.mcanm", LAST_FRAME);
		ISelectionPredicate<EntityBarroth> select = new ISelectionPredicate.SelectAlways<EntityBarroth>();
		IWeightProvider<EntityBarroth> weight = new IWeightProvider.RandomWeightAdapter<>(
			1F);
		IRoarSoundProvider roar = new IRoarSoundProvider.RoarSoundAdapter(
			"");
		IRoarProvider<EntityBarroth> provide = new AIGeneralRoar.RoarAdapter<>(
			anim, select, weight, roar);
		return provide;
	}

	@Override
	public void update() {
		super.update();
		EntityBarroth entity = this.getEntity();
		target = entity.getAttackTarget();
		if (this.getCurrentFrame() >= 18 && this.getCurrentFrame() <= 22) {
			entity.getTurnHelper().updateTargetPoint(target);
			entity.getTurnHelper().updateTurnSpeed(30.0f);
		}
	}
}
