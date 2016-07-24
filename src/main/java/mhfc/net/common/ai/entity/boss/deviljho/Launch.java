package mhfc.net.common.ai.entity.boss.deviljho;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.entity.AIGameplayComposition;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.general.actions.AIAnimatedAction;
import mhfc.net.common.ai.general.provider.simple.ISelectionPredicate;
import mhfc.net.common.entity.monster.EntityDeviljho;
import mhfc.net.common.entity.projectile.EntityProjectileBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

public class Launch extends AIAnimatedAction<EntityDeviljho> {
	private static final String ANIMATION = "mhfc:models/Deviljho/DeviljhoLaunch.mcanm";
	private static final int LAST_FRAME = 50;
	private static final IDamageCalculator damageCalc = AIUtils.defaultDamageCalc(92f, 62f, 8888f);
	private static final double MAX_DIST = 6f;
	private static final float WEIGHT = 7F;
	private static final double HEIGHT_BLOCK = 0.50D;
	private static final double SPLIT_MULTIPLIER = 0.535;

	private static ISelectionPredicate<EntityDeviljho> selectionProvider;

	static {
		selectionProvider = new ISelectionPredicate.DistanceAdapter<>(0, MAX_DIST);
	}

	private boolean thrown;

	public Launch() {}

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
			getEntity().playSound("mhfc:deviljho.rockthrow", 2.0F, 1.0F);

			AIUtils.damageCollidingEntities(getEntity(), damageCalc);
			AIGameplayComposition.launch(entity, 0, 1.4, 0);
		}
		if (this.getCurrentFrame() >= 35) {
			Vec3 look = entity.getLookVec();
			Vec3 vec_look_var = entity.getLookVec();
			// to the right and upward.
			Vec3 vec_positive_axis = vec_look_var.crossProduct(Vec3.createVectorHelper(0, 1, 0));

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

	@Override
	public String getAnimationLocation() {
		return ANIMATION;
	}

	@Override
	public int getAnimationLength() {
		return LAST_FRAME;
	}

	@Override
	public boolean shouldSelectAttack(
			IExecutableAction<? super EntityDeviljho> attack,
			EntityDeviljho actor,
			Entity target) {
		return selectionProvider.shouldSelectAttack(attack, actor, target);
	}

	@Override
	public float getWeight(EntityDeviljho entity, Entity target) {
		return WEIGHT;
	}

}
