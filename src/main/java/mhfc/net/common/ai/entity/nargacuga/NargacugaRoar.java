package mhfc.net.common.ai.entity.nargacuga;

import mhfc.net.common.ai.general.actions.AIGeneralRoar;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.monster.EntityNargacuga;

public class NargacugaRoar extends AIGeneralRoar<EntityNargacuga> {
	private static final int LAST_FRAME = 71;

	public NargacugaRoar() {
		super(NargacugaRoar.generateProvider());
	}

	private static IRoarProvider<EntityNargacuga> generateProvider() {
		IAnimationProvider anim = new IAnimationProvider.AnimationAdapter(
				"mhfc:models/Nargacuga/Roar.mcanm",
				LAST_FRAME);
		ISelectionPredicate<EntityNargacuga> select = new ISelectionPredicate.SelectAlways<EntityNargacuga>();
		IWeightProvider<EntityNargacuga> weight = new IWeightProvider.RandomWeightAdapter<>(1F);
		IRoarSoundProvider roar = new IRoarSoundProvider.RoarSoundAdapter("mhfc:narga.roar");
		IRoarProvider<EntityNargacuga> provide = new AIGeneralRoar.RoarAdapter<>(anim, select, weight, roar);
		return provide;
	}

	@Override
	protected void update() {
		super.update();
		EntityNargacuga entity = getEntity();
		if (this.getCurrentFrame() <= 10) {
			entity.getTurnHelper().updateTargetPoint(target);
			entity.getTurnHelper().updateTurnSpeed(7.0f);
		}
	}
}
