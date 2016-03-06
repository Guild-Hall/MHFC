package mhfc.net.common.ai.entity.nargacuga;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.general.actions.AIGeneralJumpAttack;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.IDamageProvider;
import mhfc.net.common.ai.general.provider.IJumpParamterProvider;
import mhfc.net.common.ai.general.provider.IJumpParamterProvider.ConstantAirTimeAdapter.ITargetResolver;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.monster.EntityNargacuga;
import mhfc.net.common.entity.projectile.EntityProjectileBlock;
import net.minecraft.util.Vec3;

public class TailSlam extends AIGeneralJumpAttack<EntityNargacuga> {

	private static final int MAX_ANGLE = 20;
	private static final float MAX_DISTANCE = 6;
	private static final float WEIGHT = 8000;
	private static final int JUMP_FRAME = 19;
	private static final int JUMP_TIME = 12;
	private static final int ANIM_LENGTH = 100;
	private static final int SPIKE_FRAME = 50;
	private static final float SPEED = 1.f;

	private static final IAnimationProvider animation = new IAnimationProvider.AnimationAdapter(
			"mhfc:models/Nargacuga/TailSlam.mcanm",
			ANIM_LENGTH);
	private static final ISelectionPredicate<EntityNargacuga> select = new ISelectionPredicate.SelectionAdapter<>(
			-MAX_ANGLE,
			MAX_ANGLE,
			0,
			MAX_DISTANCE);
	private static final IWeightProvider<EntityNargacuga> weight = new IWeightProvider.SimpleWeightAdapter<>(WEIGHT);
	private static final IDamageCalculator damageCalculator = AIUtils.defaultDamageCalc(150, 1000, 888880);

	public TailSlam() {
		super(generateProvider());
	}

	private static IJumpProvider<EntityNargacuga> generateProvider() {
		IDamageProvider damage = new IDamageProvider.DamageAdapter(damageCalculator);
		IJumpParamterProvider<EntityNargacuga> jumpParams = new IJumpParamterProvider.ConstantAirTimeAdapter<>(
				JUMP_TIME,
				new ITargetResolver<EntityNargacuga>() {
					@Override
					public Vec3 getJumpTarget(EntityNargacuga entity) {
						return entity.getLook(1.0f).addVector(entity.posX, entity.posY, entity.posZ);
					}
				});
		IJumpTimingProvider<EntityNargacuga> timing = new IJumpTimingProvider.JumpTimingAdapter<EntityNargacuga>(
				JUMP_FRAME,
				0,
				0);
		JumpAdapter<EntityNargacuga> adapter = new JumpAdapter<EntityNargacuga>(
				animation,
				select,
				weight,
				damage,
				jumpParams,
				timing);
		return adapter;
	}

	@Override
	protected void finishExecution() {
		super.finishExecution();
		EntityNargacuga nargacuga = getEntity();
		nargacuga.rotationYaw = AIUtils.normalizeAngle(nargacuga.rotationYaw + 180);
		nargacuga.addVelocity(10e-4, 0, 10e-4);
	}

	@Override
	public void update() {
		EntityNargacuga nargacuga = getEntity();
		if (nargacuga.worldObj.isRemote)
			return;
		if (getCurrentFrame() == SPIKE_FRAME) {
			Vec3 up = Vec3.createVectorHelper(0, 1, 0);
			Vec3 look = nargacuga.getLookVec();
			Vec3 left = look.crossProduct(up).normalize();
			Vec3 relUp = left.crossProduct(look);
			final int spikeClusters = 4;
			final int spikesPerCluster = 6;
			final float offsetScaleBack = 6;
			for (int i = 0; i < spikeClusters * spikesPerCluster; i++) {
				int cluster = i / spikesPerCluster;
				int spike = i % spikesPerCluster;
				// FIXME replace with narga spikes once they are done
				EntityProjectileBlock spikeEntity = new EntityProjectileBlock(nargacuga.worldObj, nargacuga);
				spikeEntity.moveEntity(
						offsetScaleBack * look.xCoord,
						offsetScaleBack * look.yCoord - 2.5,
						offsetScaleBack * look.zCoord);
				float weightRelUp = (float) Math.sin(Math.PI / (spikesPerCluster - 1) * spike);
				float weightLeft = (float) Math.cos(Math.PI / (spikesPerCluster - 1) * spike);
				float weightLook = (float) Math.cos(Math.PI / spikeClusters * cluster) * 0.6f;
				spikeEntity.setThrowableHeading(
						weightLeft * left.xCoord + weightRelUp * relUp.xCoord + weightLook * look.xCoord,
						weightLeft * left.yCoord + weightRelUp * relUp.yCoord + weightLook * look.yCoord,
						weightLeft * left.zCoord + weightRelUp * relUp.zCoord + weightLook * look.zCoord,
						SPEED,
						0);
				nargacuga.worldObj.spawnEntityInWorld(spikeEntity);
			}
		}
	}
}
