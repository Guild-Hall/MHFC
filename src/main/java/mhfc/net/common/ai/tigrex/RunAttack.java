package mhfc.net.common.ai.tigrex;

import mhfc.net.common.ai.AttackAdapter;
import mhfc.net.common.entity.mob.EntityTigrex;
import mhfc.net.common.entity.type.EntityWyvernHostile;
import mhfc.net.common.entity.type.EntityWyvernPeaceful;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import com.github.worldsender.mcanm.client.model.mhfcmodel.animation.stored.AnimationRegistry;

public class RunAttack extends AttackAdapter<EntityTigrex> {
	private PathEntity path;
	private Entity target;

	public RunAttack() {
		setAnimation(AnimationRegistry.loadAnimation(new ResourceLocation(
				"mhfc:models/Tigrex/run.mcanm")));
	}

	@Override
	public float getWeight() { // The bigger the value the more likely to get
								// executed
		EntityLivingBase trgt = this.entity.getAttackTarget();
		if (trgt == null)
			return 0.0F;
		this.target = trgt;
		Vec3 pos = Vec3.createVectorHelper(this.entity.posX, this.entity.posY,
				this.entity.posZ);
		Vec3 entityToTarget = Vec3.createVectorHelper(trgt.posX, trgt.posY,
				trgt.posZ);
		entityToTarget = pos.subtract(entityToTarget);
		Vec3 destination = entityToTarget;
		destination.xCoord *= 1.2d;
		destination.zCoord *= 1.2d;
		destination = destination.addVector(pos.xCoord, pos.yCoord, pos.zCoord);

		this.path = this.entity.getNavigator().getPathToXYZ(destination.xCoord,
				destination.yCoord, destination.zCoord);
		if (path == null)
			return 0.0F;
		double dist = entityToTarget.lengthVector();
		return (float) Math.log(dist); // More likely the farer away
	}

	@Override
	public void beginExecution() { // Beginning the attack
		this.entity.getNavigator().setPath(path, 1.5f);
	}

	@Override
	public void update() {
		if (this.entity.ticksExisted % 20 != 0)
			return;
		if (target instanceof EntityPlayer) {
			target.attackEntityFrom(DamageSource.causeMobDamage(entity), 2.2F);
		} else if (target instanceof EntityWyvernHostile
				|| target instanceof EntityWyvernPeaceful) {
			target.attackEntityFrom(DamageSource.causeMobDamage(entity), 62F);
		} else {
			target.attackEntityFrom(DamageSource.causeMobDamage(entity),
					60F * 5 + 100);
		}
	} // Called each tick

	@Override
	public boolean shouldContinue() { // To determine if to cancel
		return !entity.getNavigator().noPath();
	}

	@Override
	public void finishExecution() { // When finished
		this.entity.setTarget(null);
	}

	@Override
	public byte mutexBits() { // Well known mutex bits
		return 1;
	}

	@Override
	public int getNextFrame(int frame) { // For the animation
		return (++frame % 80);
	}
}
