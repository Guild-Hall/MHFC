package mhfc.net.common.ai.entity.monsters.nargacuga;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.actions.JumpAction;
import mhfc.net.common.ai.general.provider.adapters.*;
import mhfc.net.common.ai.general.provider.composite.IJumpProvider;
import mhfc.net.common.ai.general.provider.impl.IHasJumpProvider;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.CreatureAttributes;
import mhfc.net.common.entity.creature.Nargacuga;
import mhfc.net.common.entity.projectile.ProjectileBlock;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.entity.MoverType;
import net.minecraft.util.math.Vec3d;

public class TailSlam extends JumpAction<Nargacuga> implements IHasJumpProvider<Nargacuga> {

	public TailSlam() {}

	@Override
	protected float computeSelectionWeight() {
		Nargacuga e = this.getEntity();
		target = e.getAttackTarget();
		if (target == null) {
			return DONT_SELECT;
		}
		Vec3d toTarget = WorldHelper.getVectorToTarget(e, target);
		double dist = toTarget.length();
		if (dist > 15F) {
			return DONT_SELECT;
		}

		return 150F;
	}

	@Override
	public IJumpProvider<Nargacuga> getJumpProvider() {
		return new JumpAdapter<Nargacuga>(
				new AnimationAdapter(this, "mhfc:models/Nargacuga/TailSlam.mcanm", 105),
				new DamageAdapter(AIUtils.defaultDamageCalc(120F, 300, 888880)),
				new ConstantAirTimeAdapter<Nargacuga>(
						12,
						entity -> entity.getLookVec().add(entity.posX, entity.posY, entity.posZ)),
				new JumpTimingAdapter<Nargacuga>(19, 0, 0));
	}

	@Override
	protected void finishExecution() {
		super.finishExecution();
		Nargacuga nargacuga = getEntity();
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
		Nargacuga nargacuga = getEntity();

		if (this.getCurrentFrame() == 10) {
			nargacuga.playSound(MHFCSoundRegistry.getRegistry().nargacugaTailSlam, 2.0F, 1.0F);
		}
		if (!nargacuga.onGround || thrown || this.getCurrentFrame() < 30) {
			return;
		}
		CreatureAttributes.spawnCracks(nargacuga, 250);
		thrown = true;
		if (nargacuga.world.isRemote) {
			return;
		}


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
				ProjectileBlock spikeEntity = new ProjectileBlock(nargacuga.world, nargacuga);
				spikeEntity.move(MoverType.SELF,
						offsetScaleBack * look.x,
						offsetScaleBack * look.y - 2.5,
						offsetScaleBack * look.z);
				float weightRelUp = (float) Math.sin(Math.PI / (spikesPerCluster - 1) * spike);
				float weightLeft = (float) Math.cos(Math.PI / (spikesPerCluster - 1) * spike);
				float weightLook = (float) Math.cos(Math.PI / spikeClusters * cluster) * 0.6f;
				spikeEntity.shoot(
						weightLeft * left.x + weightRelUp * relUp.x + weightLook * look.x,
						weightLeft * left.y + weightRelUp * relUp.y + weightLook * look.y,
						weightLeft * left.z + weightRelUp * relUp.z + weightLook * look.z,
					2F,
						0);
				nargacuga.world.spawnEntity(spikeEntity);
			}
	}
}
