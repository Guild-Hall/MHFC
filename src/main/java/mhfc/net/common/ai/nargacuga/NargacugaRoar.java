package mhfc.net.common.ai.nargacuga;

import mhfc.net.common.ai.general.actions.AIGeneralRoar;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.mob.EntityNargacuga;

public class NargacugaRoar extends AIGeneralRoar<EntityNargacuga> {
	private static final int LAST_FRAME = 71;

	public NargacugaRoar() {
		super(generateProvider());
	}

	private static IRoarProvider<EntityNargacuga> generateProvider() {
		IAnimationProvider anim = new IAnimationProvider.AnimationAdapter(
			"mhfc:models/Nargacuga/Roar.mcanm", LAST_FRAME);
		ISelectionPredicate<EntityNargacuga> select = new ISelectionPredicate.SelectAlways<EntityNargacuga>();
		IWeightProvider<EntityNargacuga> weight = new IWeightProvider.RandomWeightAdapter<>(
			1F);
		IRoarProvider<EntityNargacuga> provide = new AIGeneralRoar.RoarAdapter<>(
			anim, select, weight, "mhfc:nargacuga-roar");
		return provide;
	}

}
