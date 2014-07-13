package mhfc.net.common.ai.tigrex;

import mhfc.net.common.ai.AIAnimation;
import mhfc.net.common.entity.mob.EntityTigrex;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class AITigrexBite extends AIAnimation<EntityTigrex> {

	private EntityTigrex tigrex;
	private EntityLivingBase target;

	public AITigrexBite(EntityTigrex type) {
		super(type);
		tigrex = type;
		target = null;
	}

	@Override
	public int getAnimID() {
		return 1;
	}

	@Override
	public boolean isAutomatic() {
		return true;
	}

	@Override
	public int getDuration() {
		return 30;
	}

	@Override
	public void startExecuting() {
		super.startExecuting();
		target = tigrex.getAttackTarget();
	}

	@Override
	public void updateTask() {
		if (tigrex.getAnimTick() < 7)
			tigrex.getLookHelper().setLookPositionWithEntity(target, 30F, 30F);
		if (tigrex.getAnimTick() == 18 && target != null)
			target.attackEntityFrom(DamageSource.causeMobDamage(tigrex),
					8 + tigrex.getRNG().nextInt(17));
	}

}
