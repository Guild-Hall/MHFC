package mhfc.net.common.ai.entity.monsters.tigrex;

import mhfc.net.common.ai.general.SelectionUtils;
import mhfc.net.common.ai.general.actions.AnimatedAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.entity.creature.Tigrex;
import mhfc.net.common.entity.projectile.ProjectileBlock;
import net.minecraft.util.math.Vec3d;

public class GroundHurl extends AnimatedAction<Tigrex> implements IHasAnimationProvider {
	private static final int LAST_FRAME = 60;
	private static final String ANIMATION_LOCATION = "mhfc:models/Tigrex/dirtthrow.mcanm";

	private static final int THROW_FRAME = 21;
	private static final int TURN_FRAMES = 14;

	private static final double SPLIT_MULTIPLIER = 0.125;
	private static final double THROW_HEIGHT = 0.45;
	private static final float TURN_RATE = 50;

	private final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, LAST_FRAME);
	private boolean thrown;

	public GroundHurl() {}

	@Override
	protected float computeSelectionWeight() {
		Tigrex entity = this.getEntity();
		target = entity.getAttackTarget();
		if (SelectionUtils.isIdle(entity)) {
			return DONT_SELECT;
		}
		return 7F;
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return ANIMATION;
	}

	@Override
	protected void beginExecution() {
		super.beginExecution();

		thrown = false;
	}

	@Override
	protected void onUpdate() {
		if (thrown) {
			return;
		}
		Tigrex tigrex = getEntity();
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
			ProjectileBlock block = new ProjectileBlock(tigrex.world, tigrex);
			double xCo = look.x;
			double yCo = look.y + THROW_HEIGHT;
			double zCo = look.z;
			if (i == 0) {
				xCo += rightSide.x * SPLIT_MULTIPLIER;
				zCo += rightSide.z * SPLIT_MULTIPLIER;
			} else if (i == 2) {
				xCo -= rightSide.x * SPLIT_MULTIPLIER;
				zCo -= rightSide.z * SPLIT_MULTIPLIER;
			}
			block.shoot(xCo, yCo, zCo, 2.3F, 0f);
			tigrex.world.spawnEntity(block);
		}
	}

}
