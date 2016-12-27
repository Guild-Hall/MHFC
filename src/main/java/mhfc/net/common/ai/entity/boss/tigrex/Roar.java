package mhfc.net.common.ai.entity.boss.tigrex;

import mhfc.net.common.ai.general.actions.RoarAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.RoarAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.ai.general.provider.simple.IRoarProvider;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.monster.EntityTigrex;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;

public class Roar extends RoarAction<EntityTigrex> implements IHasAnimationProvider {

	private static final int LAST_FRAME = 70;
	private static final String ANIMATION_LOCATION = "mhfc:models/Tigrex/rawr.mcanm";

	private final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, LAST_FRAME);
	protected Vec3d targetPoint;

	public Roar() {}

	@Override
	protected float computeSelectionWeight() {
		return 0.05f;
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return ANIMATION;
	}

	@Override
	public IRoarProvider provideRoarBehaviour() {
		return new RoarAdapter(MHFCSoundRegistry.getRegistry().tigrexRoar, true);
	}

	@Override
	public void beginExecution() {
		super.beginExecution();
		Entity target = getEntity().getAttackTarget();
		targetPoint = target != null ? target.getPositionVector() : null;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		EntityTigrex entity = this.getEntity();
		if (this.getCurrentFrame() <= 10) {
			entity.getTurnHelper().updateTargetPoint(targetPoint);
			entity.getTurnHelper().updateTurnSpeed(6.0f);
		}
	}
}
