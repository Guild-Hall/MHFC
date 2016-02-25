package mhfc.net.common.ai.entity.tigrex;

import mhfc.net.common.ai.general.actions.AIGeneralRoar;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.monster.EntityTigrex;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

public class TigrexRoar extends AIGeneralRoar<EntityTigrex> {
	private static final int LAST_FRAME = 70;

	Vec3 targetPoint;

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
	public void beginExecution() {
		super.beginExecution();
		Entity target = getEntity().getAttackTarget();
		if (target != null) {
			targetPoint = WorldHelper.getEntityPositionVector(target);
		}
	}

	@Override
	public void update() {
		super.update();
		EntityTigrex entity = this.getEntity();
		if (this.getCurrentFrame() <= 10) {
			entity.getTurnHelper().updateTargetPoint(targetPoint);
			entity.getTurnHelper().updateTurnSpeed(6.0f);
		}
	}
}
