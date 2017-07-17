package mhfc.net.common.ai.entity.monsters.nargacuga;

import mhfc.net.common.ai.entity.EntityAIMethods;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.actions.JumpAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.ConstantAirTimeAdapter;
import mhfc.net.common.ai.general.provider.adapters.DamageAdapter;
import mhfc.net.common.ai.general.provider.adapters.JumpAdapter;
import mhfc.net.common.ai.general.provider.adapters.JumpTimingAdapter;
import mhfc.net.common.ai.general.provider.composite.IJumpProvider;
import mhfc.net.common.ai.general.provider.impl.IHasJumpProvider;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.monster.EntityNargacuga;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.util.math.Vec3d;

public class TailSlam extends JumpAction<EntityNargacuga> implements IHasJumpProvider<EntityNargacuga> {

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
		if (dist > 15F) {
			return DONT_SELECT;
		}

		return 150F;
	}

	@Override
	public IJumpProvider<EntityNargacuga> getJumpProvider() {
		return new JumpAdapter<EntityNargacuga>(
				new AnimationAdapter(this, "mhfc:models/Nargacuga/TailSlam.mcanm", 100),
				new DamageAdapter(AIUtils.defaultDamageCalc(280, 1000, 888880)),
				new ConstantAirTimeAdapter<EntityNargacuga>(
						12,
						entity -> entity.getLookVec().addVector(entity.posX, entity.posY, entity.posZ)),
				new JumpTimingAdapter<EntityNargacuga>(19, 0, 0));
	}

	@Override
	protected void finishExecution() {
		super.finishExecution();
		EntityNargacuga nargacuga = getEntity();
		nargacuga.rotationYaw = AIUtils.normalizeAngle(nargacuga.rotationYaw + 180);
		nargacuga.addVelocity(10e-4, 0, 10e-4);
	}

	private boolean thrown = false;

	@Override
	public void beginExecution() {
		super.beginExecution();
		thrown = false;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		damageCollidingEntities();
		EntityNargacuga nargacuga = getEntity();

		if (this.getCurrentFrame() == 10) {
			nargacuga.playSound(MHFCSoundRegistry.getRegistry().nargacugaTailSlam, 2.0F, 1.0F);
		}
		if (!nargacuga.onGround || thrown || this.getCurrentFrame() < 30) {
			return;
		}
		EntityAIMethods.stompCracks(nargacuga, 250);
		thrown = true;
		if (nargacuga.world.isRemote) {
			return;
		}
		if (getCurrentFrame() == 50) {

		}
		//TODO
		/*if (getCurrentFrame() == SPIKE_FRAME) {
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
		}*/
	}
}
