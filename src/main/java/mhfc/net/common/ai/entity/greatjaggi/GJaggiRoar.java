package mhfc.net.common.ai.entity.greatjaggi;

import mhfc.net.common.ai.general.actions.AIGeneralRoar;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.monster.EntityGreatJaggi;
import mhfc.net.common.entity.monster.EntityGreatJaggi;

public class GJaggiRoar extends AIGeneralRoar<EntityGreatJaggi> {
	private static final int LAST_FRAME = 64;

	public GJaggiRoar() {
		super(GJaggiRoar.generateProvider());
	}

	private static IRoarProvider<EntityGreatJaggi> generateProvider() {
		IAnimationProvider anim = new IAnimationProvider.AnimationAdapter(
				"mhfc:models/GreatJaggi/GreatJaggiRoar.mcanm",
				LAST_FRAME);
		ISelectionPredicate<EntityGreatJaggi> select = new ISelectionPredicate.SelectAlways<EntityGreatJaggi>();
		IWeightProvider<EntityGreatJaggi> weight = new IWeightProvider.RandomWeightAdapter<>(1F);
		IRoarSoundProvider roar = new IRoarSoundProvider.RoarSoundAdapter("mhfc:greatjaggi.roar");
		IRoarProvider<EntityGreatJaggi> provide = new AIGeneralRoar.RoarAdapter<>(anim, select, weight, roar);
		return provide;
	}

	@Override
	public void update() {
		super.update();
		EntityGreatJaggi entity = this.getEntity();
		target = entity.getAttackTarget();
		if (this.getCurrentFrame() >= 18 && this.getCurrentFrame() <= 22) {
			entity.getTurnHelper().updateTargetPoint(target);
			entity.getTurnHelper().updateTurnSpeed(30.0f);
		}
	}
}
