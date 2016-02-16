package mhfc.net.common.ai.entity.tigrex;

import mhfc.net.common.ai.general.actions.AIGeneralRoar;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.monster.EntityTigrex;

public class TigrexRoar extends AIGeneralRoar<EntityTigrex> {
	private static final int LAST_FRAME = 70;

	public TigrexRoar() {
		super(TigrexRoar.generateProvider());
	}

	private static IRoarProvider<EntityTigrex> generateProvider() {
		IAnimationProvider anim = new IAnimationProvider.AnimationAdapter("mhfc:models/Tigrex/rawr.mcanm", LAST_FRAME);
		ISelectionPredicate<EntityTigrex> select = new ISelectionPredicate.SelectAlways<EntityTigrex>();
		IWeightProvider<EntityTigrex> weight = new IWeightProvider.RandomWeightAdapter<>(1F);
		IRoarSoundProvider roar = new IRoarSoundProvider.RoarSoundAdapter("mhfc:tigrex.roar");
		IRoarProvider<EntityTigrex> provide = new AIGeneralRoar.RoarAdapter<>(anim, select, weight, roar);
		return provide;
	}

	@Override
	public void update() {
		super.update();
		EntityTigrex entity = this.getEntity();
		target = entity.getAttackTarget();
		if (this.getCurrentFrame() >= 18 && this.getCurrentFrame() <= 22) {
			entity.getTurnHelper().updateTargetPoint(target);
			entity.getTurnHelper().updateTurnSpeed(30.0f);
		}
	}
}
