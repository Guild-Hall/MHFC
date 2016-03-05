package mhfc.net.common.ai.entity.deviljho;

import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.general.actions.AIAnimatedAction;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.monster.EntityDeviljho;
import mhfc.net.common.entity.projectile.EntityProjectileBlock;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

public class DeviljhoLaunch extends AIAnimatedAction<EntityDeviljho> {
	private static final String ANIMATION = "mhfc:models/Deviljho/DeviljhoLaunch.mcanm";
	private static final int LAST_FRAME = 50;
	private static final IDamageCalculator damageCalc = AIUtils.defaultDamageCalc(92f, 62f, 8888f);
	private static final double MAX_DIST = 6f;
	private static final float WEIGHT = 7F;
	private static final double HEIGHT_BLOCK = 0.50D;
	private static final double SPLIT_MULTIPLIER = 0.535; // from TigrexGroundHurl (C) Andreas.

	private boolean thrown;

	public DeviljhoLaunch() {
		super(generateProvider());
	}

	private static IAnimatedActionProvider<EntityDeviljho> generateProvider() {
		IAnimationProvider animationProvider = new IAnimationProvider.AnimationAdapter(ANIMATION, LAST_FRAME);
		ISelectionPredicate<EntityDeviljho> selectionProvider = new ISelectionPredicate.DistanceAdapter<>(0, MAX_DIST);
		IWeightProvider<EntityDeviljho> weightProvider = new IWeightProvider.SimpleWeightAdapter<>(WEIGHT);
		return new AnimatedActionAdapter<EntityDeviljho>(animationProvider, selectionProvider, weightProvider);
	}

	@Override
	public void update() {
		if (thrown) {
			return;
		}
		EntityDeviljho entity = this.getEntity();
		if (this.getCurrentFrame() == 28) {
			if (entity.getAttackTarget() == null) {
				return;
			}
			getEntity().playSound("mhfc:deviljho.rockthrow", 1.0F, 1.0F);

			AIUtils.damageCollidingEntities(getEntity(), damageCalc);
			List<Entity> collidingEnts = WorldHelper.collidingEntities(entity);
			if (!entity.worldObj.isRemote) {
				for (Entity collider : collidingEnts) {
					collider.addVelocity(0, 1.4, 0);
					MHFCMain.logger.debug(entity.getAttackTarget().motionY);
				}
			}
		}
		if (this.getCurrentFrame() >= 35) {
			Vec3 look = entity.getLookVec();
			Vec3 vec_look_var = entity.getLookVec();
			Vec3 vec_positive_axis = vec_look_var.crossProduct(Vec3.createVectorHelper(0, 1, 0)); // to the right and
																									// upward.

			for (int i = 0; i < 5; i++) {
				EntityProjectileBlock block = new EntityProjectileBlock(entity.worldObj, entity);
				double xCo = look.xCoord;
				double yCo = look.yCoord + HEIGHT_BLOCK;
				double zCo = look.zCoord;
				if (i == 0) {
					xCo += vec_positive_axis.xCoord * SPLIT_MULTIPLIER;
					zCo += vec_positive_axis.zCoord * SPLIT_MULTIPLIER;
				} else if (i == 2) {
					xCo -= vec_positive_axis.xCoord * SPLIT_MULTIPLIER;
					zCo -= vec_positive_axis.zCoord * SPLIT_MULTIPLIER;
				} else if (i == 3) {
					xCo += vec_positive_axis.xCoord * SPLIT_MULTIPLIER * 0.3D;
					zCo += vec_positive_axis.zCoord * SPLIT_MULTIPLIER * 0.3D;
				} else if (i == 4) {
					xCo -= vec_positive_axis.xCoord * SPLIT_MULTIPLIER * 0.3D;
					zCo -= vec_positive_axis.zCoord * SPLIT_MULTIPLIER * 0.3D;
				}

				block.setThrowableHeading(xCo, yCo, zCo, 2f, 1.5f);
				entity.worldObj.spawnEntityInWorld(block);
			}

			thrown = true;
		}
		if (isMoveForwardFrame(getCurrentFrame())) {
			EntityDeviljho e = getEntity();
			e.moveForward(1, false);
		}
	}

	@Override
	protected void beginExecution() {
		super.beginExecution();
		thrown = false;
	}

	private boolean isMoveForwardFrame(int frame) {
		return (frame > 20 && frame < 30);
	}

}
