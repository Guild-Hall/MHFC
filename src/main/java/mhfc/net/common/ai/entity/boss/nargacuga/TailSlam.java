package mhfc.net.common.ai.entity.boss.nargacuga;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.general.actions.AIGeneralJumpAttack;
import mhfc.net.common.ai.general.actions.IJumpTimingProvider;
import mhfc.net.common.ai.general.provider.simple.IJumpParamterProvider;
import mhfc.net.common.ai.general.provider.simple.IJumpParamterProvider.ConstantAirTimeAdapter.ITargetResolver;
import mhfc.net.common.ai.general.provider.simple.ISelectionPredicate;
import mhfc.net.common.entity.monster.EntityNargacuga;
import mhfc.net.common.entity.projectile.EntityProjectileBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

public class TailSlam extends AIGeneralJumpAttack<EntityNargacuga> {

	private static final String ANIMATION = "mhfc:models/Nargacuga/TailSlam.mcanm";
	private static final int ANIM_LENGTH = 100;
	private static final int MAX_ANGLE = 20;
	private static final float MAX_DISTANCE = 8;
	private static final float WEIGHT = 20;
	private static final int JUMP_FRAME = 19;
	private static final int JUMP_TIME = 12;
	private static final int SPIKE_FRAME = 50;
	private static final float SPEED = 1.4f;

	private static final ISelectionPredicate<EntityNargacuga> select;
	private static final IDamageCalculator damageCalculator;
	private static final IJumpTimingProvider<EntityNargacuga> timing;
	private static final IJumpParamterProvider<EntityNargacuga> jumpParams;

	static {
		select = new ISelectionPredicate.SelectionAdapter<>(-MAX_ANGLE, MAX_ANGLE, 0, MAX_DISTANCE);
		damageCalculator = AIUtils.defaultDamageCalc(280, 500, 888880);
		jumpParams = new IJumpParamterProvider.ConstantAirTimeAdapter<>(
				JUMP_TIME,
				new ITargetResolver<EntityNargacuga>() {
					@Override
					public Vec3 getJumpTarget(EntityNargacuga entity) {
						return entity.getLookVec().addVector(entity.posX, entity.posY, entity.posZ);
					}
				});
		timing = new IJumpTimingProvider.JumpTimingAdapter<EntityNargacuga>(JUMP_FRAME, 0, 0);
	}

	public TailSlam() {}

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
		if(this.getCurrentFrame() == 10){
			nargacuga.playSound("mhfc:narga.tailjump", 2.0F, 1.0F);
		}
		
		if (nargacuga.worldObj.isRemote)
			return;
		if (getCurrentFrame() == SPIKE_FRAME) {
		
			Vec3 up = Vec3.createVectorHelper(0, 1, 0);
			Vec3 look = nargacuga.getLookVec();
			Vec3 left = look.crossProduct(up).normalize();
			Vec3 relUp = left.crossProduct(look);
			final int spikeClusters = 7;
			final int spikesPerCluster = 9;
			final float offsetScaleBack = 9;
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
		AIUtils.damageCollidingEntities(getEntity(), damageCalculator);
	}

	@Override
	public String getAnimationLocation() {
		return ANIMATION;
	}

	@Override
	public int getAnimationLength() {
		return ANIM_LENGTH;
	}

	@Override
	public boolean shouldSelectAttack(
			IExecutableAction<? super EntityNargacuga> attack,
			EntityNargacuga actor,
			Entity target) {
		return select.shouldSelectAttack(attack, actor, target);
	}

	@Override
	public float getWeight(EntityNargacuga entity, Entity target) {
		return WEIGHT;
	}

	@Override
	public IDamageCalculator getDamageCalculator() {
		return damageCalculator;
	}

	@Override
	public float getInitialUpVelocity(EntityNargacuga entity) {
		return jumpParams.getInitialUpVelocity(entity);
	}

	@Override
	public float getForwardVelocity(EntityNargacuga entity) {
		return jumpParams.getForwardVelocity(entity);
	}

	@Override
	public boolean isJumpFrame(EntityNargacuga entity, int frame) {
		return timing.isJumpFrame(entity, frame);
	}

	@Override
	public boolean isDamageFrame(EntityNargacuga entity, int frame) {
		return timing.isDamageFrame(entity, frame);
	}

	@Override
	public float getTurnRate(EntityNargacuga entity, int frame) {
		return timing.getTurnRate(entity, frame);
	}
}
