package mhfc.net.common.ai.entity.boss.tigrex;

import mhfc.net.common.ai.general.SelectionUtils;
import mhfc.net.common.ai.general.actions.AnimatedAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.monster.EntityTigrex;
import mhfc.net.common.entity.projectile.EntityProjectileBlock;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.util.math.Vec3d;

public class GroundHurl extends AnimatedAction<EntityTigrex> implements IHasAnimationProvider {
	private static final int LAST_FRAME = 60;
	private static final String ANIMATION_LOCATION = "mhfc:models/Tigrex/dirtthrow.mcanm";

	private static final int THROW_FRAME = 21;
	private static final int TURN_FRAMES = 14;

	private static final double SPLIT_MULTIPLIER = 0.125;
	private static final double THROW_HEIGHT = 0.35;
	private static final float TURN_RATE = 4;

	private static final float MIN_DIST = 0f;
	private static final float MAX_DIST = 2f;
	private static final float MAX_ANGLE = 0.155f;

	private final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, LAST_FRAME);
	private boolean thrown;
	private int weightFactor;

	public GroundHurl() {
		weightFactor = 1;
	}

	private boolean shouldSelect() {
		return SelectionUtils.isInDistance(MIN_DIST, MAX_DIST, getEntity(), target)
				&& SelectionUtils.isInViewAngle(-MAX_ANGLE, MAX_ANGLE, getEntity(), target);
	}

	@Override
	protected float computeSelectionWeight() {
		if (!shouldSelect()) {
			return DONT_SELECT;
		}
		Vec3d toTarget = WorldHelper.getVectorToTarget(getEntity(), target);
		double dist = toTarget.lengthVector();
		int weight = weightFactor;
		if (weightFactor > 1) {
			weightFactor--;
		}
		return (float) (dist - MIN_DIST) / (weight);
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
		weightFactor *= 3;
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
		if (tigrex.worldObj.isRemote) {
			return;
		}
		Vec3d look = tigrex.getLookVec();
		Vec3d lookVec = tigrex.getLookVec();
		Vec3d rightSide = lookVec.crossProduct(new Vec3d(0, 1, 0));
		for (int i = 0; i < 3; i++) {
			EntityProjectileBlock block = new EntityProjectileBlock(tigrex.worldObj, tigrex);
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
			block.setThrowableHeading(xCo, yCo, zCo, 1f, 0.5f);
			tigrex.worldObj.spawnEntityInWorld(block);
		}
	}

}
