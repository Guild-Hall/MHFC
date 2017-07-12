package mhfc.net.common.ai.entity.boss.tigrex;

import mhfc.net.common.ai.general.SelectionUtils;
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
		EntityTigrex entity = this.getEntity();
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
		EntityTigrex tigrex = getEntity();
		if (getCurrentFrame() < THROW_FRAME) {
			if (getCurrentFrame() < TURN_FRAMES) {
				tigrex.getTurnHelper().updateTurnSpeed(TURN_RATE);
				tigrex.getTurnHelper().updateTargetPoint(tigrex.getAttackTarget());
			}
			return;
			
		}
		if(getCurrentFrame() == THROW_FRAME){
			Vec3d look = tigrex.getLookVec();
				EntityProjectileBlock block = new EntityProjectileBlock(tigrex.world, tigrex);
				double xCo = look.xCoord;
				double yCo = look.yCoord + THROW_HEIGHT;
				double zCo = look.zCoord;
				block.setThrowableHeading(xCo, yCo, zCo, 1f, 0.5F);
				tigrex.world.spawnEntity(block);
			}
			tigrex.playSound(MHFCSoundRegistry.getRegistry().tigrexRockThrow, 2.0F, 1.0F);	
		thrown = true;
		if (tigrex.world.isRemote) {
			return;
		}
		
	}

}
