package mhfc.net.common.ai.tigrex;

import mhfc.net.common.ai.general.actions.AIGeneralRoar;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.mob.EntityTigrex;

public class RoarAttack extends AIGeneralRoar<EntityTigrex> {
	private static final int LAST_FRAME = 70;

	public RoarAttack() {
		super(generateProvider());
	}

	private static IRoarProvider<EntityTigrex> generateProvider() {
		IAnimationProvider anim = new IAnimationProvider.AnimationAdapter(
			"mhfc:models/Tigrex/rawr.mcanm", LAST_FRAME);
		ISelectionPredicate<EntityTigrex> select = new ISelectionPredicate.SelectAlways<EntityTigrex>();
		IWeightProvider<EntityTigrex> weight = new IWeightProvider.RandomWeightAdapter<>(
			1F);
		IRoarProvider<EntityTigrex> provide = new AIGeneralRoar.RoarAdapter<>(
			anim, select, weight, "mhfc:tigrex-roar");
		return provide;
	}

	@Override
	public void update() {
		super.update();
		EntityTigrex tigrex = this.getEntity();
		target = tigrex.getAttackTarget();
		if (this.getCurrentFrame() >= 18 && this.getCurrentFrame() <= 22) {
			tigrex.getTurnHelper().updateTargetPoint(target);
			tigrex.getTurnHelper().updateTurnSpeed(30.0f);
		}
	}
}
