package mhfc.net.common.ai.entity.boss.tigrex;

import mhfc.net.common.ai.general.actions.AnimatedAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.monster.EntityTigrex;
import mhfc.net.common.entity.projectile.EntityProjectileBlock;
import net.minecraft.util.math.Vec3d;

public class GroundHurl extends AnimatedAction<EntityTigrex> implements IHasAnimationProvider {
	private static final int LAST_FRAME = 60;
	private static final String ANIMATION_LOCATION = "mhfc:models/Tigrex/dirtthrow.mcanm";

	private static final int THROW_FRAME = 21;
	private static final int TURN_FRAMES = 14;

	private static final double SPLIT_MULTIPLIER = 0.125;
	private static final double THROW_HEIGHT = 0.35;
	private static final float TURN_RATE = 4;


	private final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, LAST_FRAME);
	private boolean thrown;

	public GroundHurl() {
	}


	@Override
	protected float computeSelectionWeight() {
		EntityTigrex tigrex = this.getEntity();
		target = tigrex.getAttackTarget();
		if (target == null) {
			return DONT_SELECT;
		}
		return 8f;
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return ANIMATION;
	}

	@Override
	protected void beginExecution() {
		super.beginExecution();
		EntityTigrex entity = getEntity();
		entity.playSound(MHFCSoundRegistry.getRegistry().tigrexRockThrow, 2.0F, 1.0F);
		thrown = false;
	}

	@Override
	protected void onUpdate() {
		if (thrown) {
			super.onUpdate();
			return;
		}
		EntityTigrex tigrex = getEntity();
		if (getCurrentFrame() < THROW_FRAME) {
			if (getCurrentFrame() < TURN_FRAMES) {
				tigrex.getTurnHelper().updateTurnSpeed(TURN_RATE);
				tigrex.getTurnHelper().updateTargetPoint(tigrex.getAttackTarget());
			}
			return;
		}
		thrown = true;
		if (tigrex.world.isRemote) {
			return;
		}
		Vec3d look = tigrex.getLookVec();
		Vec3d lookVec = tigrex.getLookVec();
		Vec3d rightSide = lookVec.crossProduct(new Vec3d(0, 1, 0));
		for (int i = 0; i < 3; i++) {
			EntityProjectileBlock block = new EntityProjectileBlock(tigrex.world, tigrex);
			double xCo = look.xCoord;
			double yCo = look.yCoord + THROW_HEIGHT;
			double zCo = look.zCoord;
			if (i == 0) {
				xCo += rightSide.xCoord * SPLIT_MULTIPLIER;
				zCo += rightSide.zCoord * SPLIT_MULTIPLIER;
			} else if (i == 2) {
				xCo -= rightSide.xCoord * SPLIT_MULTIPLIER;
				zCo -= rightSide.zCoord * SPLIT_MULTIPLIER;
			}
			block.setThrowableHeading(xCo, yCo, zCo, 1f, 1.5f);
			tigrex.world.spawnEntity(block);
		}
	}

}
