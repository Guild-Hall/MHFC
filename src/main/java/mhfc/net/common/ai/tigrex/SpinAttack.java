package mhfc.net.common.ai.tigrex;

import java.util.List;

import mhfc.net.common.ai.AttackAdapter;
import mhfc.net.common.entity.mob.EntityTigrex;
import mhfc.net.common.entity.type.EntityWyvernHostile;
import mhfc.net.common.entity.type.EntityWyvernPeaceful;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;

public class SpinAttack extends AttackAdapter<EntityTigrex> {
	private static final int MAX_FRAME = 40;
	private static final double MAX_DISTANCE = 5d;

	public SpinAttack() {
		setAnimation("mhfc:models/Tigrex/tailswipe.mcanm");
		setLastFrame(MAX_FRAME);
	}

	@Override
	public float getWeight() {
		EntityTigrex tigrex = this.getEntity();
		target = tigrex.getAttackTarget();
		if (target == null)
			return DONT_SELECT;
		Vec3 toTarget = WorldHelper.getVectorToTarget(tigrex, target);
		return (float) (MAX_DISTANCE - toTarget.lengthVector());

	}

	@Override
	public void beginExecution() {
		getEntity().getNavigator().noPath();
	}

	@Override
	public void update() {
		EntityTigrex tigrex = this.getEntity();
		List<Entity> collidingEntities = WorldHelper.collidingEntities(tigrex);
		for (Entity trgt : collidingEntities) {
			if (trgt instanceof EntityPlayer) {
				trgt.attackEntityFrom(DamageSource.causeMobDamage(tigrex), 4F);
			} else if (trgt instanceof EntityWyvernHostile
					|| trgt instanceof EntityWyvernPeaceful) {
				trgt.attackEntityFrom(DamageSource.causeMobDamage(tigrex), 62F);
			} else {
				trgt.attackEntityFrom(DamageSource.causeMobDamage(tigrex),
						70F * 5 + 100);
			}
		}
	}

	@Override
	public void finishExecution() {}

}
