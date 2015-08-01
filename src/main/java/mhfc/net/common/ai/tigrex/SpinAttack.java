package mhfc.net.common.ai.tigrex;

import java.util.List;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.entity.mob.EntityTigrex;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;

public class SpinAttack extends ActionAdapter<EntityTigrex> {
	private static final int MAX_FRAME = 40;
	private static final double MAX_DISTANCE = 5d;
	private static final double MAX_ANGLE_DOT = -0.2;

	public SpinAttack() {
		setAnimation("mhfc:models/Tigrex/tailswipe.mcanm");
		setLastFrame(MAX_FRAME);
		dmgHelper.setMemoryDamageCalculator(64f, 62f, 500f);
	}

	@Override
	public float getWeight() {
		EntityTigrex tigrex = this.getEntity();
		target = tigrex.getAttackTarget();
		if (target == null)
			return DONT_SELECT;
		Vec3 toTarget = WorldHelper.getVectorToTarget(tigrex, target);
		Vec3 lookVec = tigrex.getLookVec();
		Vec3 rightSide = lookVec.crossProduct(Vec3.createVectorHelper(0, 1, 0));
		if (rightSide.dotProduct(toTarget) < MAX_ANGLE_DOT)
			return DONT_SELECT;
		return (float) (MAX_DISTANCE - toTarget.lengthVector()) * 2;

	}

	@Override
	public void beginExecution() {
		getEntity().getNavigator().clearPathEntity();
	}

	@Override
	public void update() {
		EntityTigrex tigrex = this.getEntity();
		List<Entity> collidingEntities = WorldHelper.collidingEntities(tigrex);
		for (Entity trgt : collidingEntities) {
			trgt.attackEntityFrom(DamageSource.causeMobDamage(tigrex),
				dmgHelper.getCalculator().accept(trgt));
		}
	}
}
