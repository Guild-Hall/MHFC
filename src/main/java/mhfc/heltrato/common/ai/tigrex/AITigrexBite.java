package mhfc.heltrato.common.ai.tigrex;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import mhfc.heltrato.common.ai.AIAnimation;
import mhfc.heltrato.common.entity.mob.EntityTigrex;
import mhfc.heltrato.common.interfaces.iMHFC;

public class AITigrexBite extends AIAnimation {
	
	private EntityTigrex tigrex;
	private EntityLivingBase target;

	public AITigrexBite(EntityTigrex type) {
		super(type);
		tigrex = type;
		target = null;
	}

	public int getAnimID() {
		return 1;
	}

	public boolean isAutomatic() {
		return true;
	}

	public int getDuration() {
		return 30;
	}
	
	public void startExecuting() {
		super.startExecuting();
		target = tigrex.getAttackTarget();
	}
	
	public void updateTask() {
		if(tigrex.getAnimTick() < 7)
			tigrex.getLookHelper().setLookPositionWithEntity(target, 30F, 30F);
		if(tigrex.getAnimTick() == 18 && target != null)
			target.attackEntityFrom(DamageSource.causeMobDamage(tigrex), 8 + tigrex.getRNG().nextInt(17));
	}

}
