package mhfc.net.common.ai.entity.boss.nargacuga;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.actions.JumpAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.ConstantAirTimeAdapter;
import mhfc.net.common.ai.general.provider.adapters.DamageAdapter;
import mhfc.net.common.ai.general.provider.adapters.JumpAdapter;
import mhfc.net.common.ai.general.provider.adapters.JumpTimingAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.composite.IJumpProvider;
import mhfc.net.common.ai.general.provider.impl.IHasJumpProvider;
import mhfc.net.common.ai.general.provider.simple.IDamageCalculator;
import mhfc.net.common.ai.general.provider.simple.IJumpParameterProvider;
import mhfc.net.common.ai.general.provider.simple.IJumpTimingProvider;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.monster.EntityNargacuga;
import mhfc.net.common.entity.projectile.EntityProjectileBlock;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.entity.MoverType;
import net.minecraft.util.math.Vec3d;

public class TailSlam extends JumpAction<EntityNargacuga> implements IHasJumpProvider<EntityNargacuga> {

	private static final int ANIM_LENGTH = 100;
	private static final String ANIMATION = "mhfc:models/Nargacuga/TailSlam.mcanm";
	private static final float MAX_DISTANCE = 15F;
	private static final int JUMP_FRAME = 19;
	private static final int JUMP_TIME = 12;
	private static final int SPIKE_FRAME = 50;
	private static final float SPEED = 1.f;

	private static final float WEIGHT = 150F;

	private final IJumpProvider<EntityNargacuga> JUMP;
	{
		final IAnimationProvider animation = new AnimationAdapter(this, ANIMATION, ANIM_LENGTH);
		final IDamageCalculator damageCalculator = AIUtils.defaultDamageCalc(280, 1000, 888880);
		final IJumpParameterProvider<EntityNargacuga> jumpParams = new ConstantAirTimeAdapter<>(
				JUMP_TIME,
				entity -> entity.getLookVec().addVector(entity.posX, entity.posY, entity.posZ));
		final IJumpTimingProvider<EntityNargacuga> timing = new JumpTimingAdapter<>(JUMP_FRAME, 0, 0);
		JUMP = new JumpAdapter<>(animation, new DamageAdapter(damageCalculator), jumpParams, timing);
	}

	public TailSlam() {}


	@Override
	protected float computeSelectionWeight() {
		EntityNargacuga e = this.getEntity();
		target = e.getAttackTarget();
		if (target == null) {
			return DONT_SELECT;
		}
		Vec3d toTarget = WorldHelper.getVectorToTarget(e, target);
		double dist = toTarget.lengthVector();
		if (dist > MAX_DISTANCE) {
			return DONT_SELECT;
		}

		return WEIGHT;
	}

	@Override
	public IJumpProvider<EntityNargacuga> getJumpProvider() {
		return JUMP;
	}

	@Override
	protected void finishExecution() {
		super.finishExecution();
		EntityNargacuga nargacuga = getEntity();
		nargacuga.rotationYaw = AIUtils.normalizeAngle(nargacuga.rotationYaw + 180);
		nargacuga.addVelocity(10e-4, 0, 10e-4);
	}

	@Override
	public void onUpdate() {
		damageCollidingEntities();
		EntityNargacuga nargacuga = getEntity();
		if (this.getCurrentFrame() == 10) {
			nargacuga.playSound(MHFCSoundRegistry.getRegistry().nargacugaTailSlam, 2.0F, 1.0F);
		}

		if (nargacuga.world.isRemote) {
			return;
		}
		if (getCurrentFrame() == SPIKE_FRAME) {
			damageCollidingEntities();
			Vec3d up = new Vec3d(0, 1, 0);
			Vec3d look = nargacuga.getLookVec();
			Vec3d left = look.crossProduct(up).normalize();
			Vec3d relUp = left.crossProduct(look);
			final int spikeClusters = 4;
			final int spikesPerCluster = 6;
			final float offsetScaleBack = 6;
			for (int i = 0; i < spikeClusters * spikesPerCluster; i++) {
				int cluster = i / spikesPerCluster;
				int spike = i % spikesPerCluster;
				// FIXME replace with narga spikes once they are done
				EntityProjectileBlock spikeEntity = new EntityProjectileBlock(nargacuga.world, nargacuga);
				spikeEntity.move(MoverType.SELF,
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
				nargacuga.world.spawnEntity(spikeEntity);
			}
		}
	}
}
