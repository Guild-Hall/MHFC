package mhfc.net.common.ai.tigrex;

import mhfc.net.common.ai.AttackAdapter;
import mhfc.net.common.entity.mob.EntityTigrex;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import com.github.worldsender.mcanm.client.model.mhfcmodel.animation.stored.AnimationRegistry;

public class RunAttack extends AttackAdapter<EntityTigrex> {
	private PathEntity path;

	public RunAttack() {
		setAnimation(AnimationRegistry.loadAnimation(new ResourceLocation(
				"mhfc:models/Tigrex/run.mcanm")));
	}

	@Override
	public float getWeight() {
		EntityLivingBase target = this.entity.getAttackTarget();
		if (target == null)
			return 0.0F;
		Vec3 pos = this.entity.getPosition(1.0F);
		Vec3 entityToTarget = target.getPosition(1.0F);
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
	public void beginExecution() {
		this.entity.getNavigator().setPath(path, 1.5f);
	}

	@Override
	public void update() {}

	@Override
	public boolean shouldContinue() {
		return !entity.getNavigator().noPath();
	}

	@Override
	public void finishExecution() {
		this.entity.setTarget(null);
	}

	@Override
	public byte mutexBits() {
		return 1;
	}

	@Override
	public int getNextFrame(int frame) {
		return (++frame % 80);
	}
}
