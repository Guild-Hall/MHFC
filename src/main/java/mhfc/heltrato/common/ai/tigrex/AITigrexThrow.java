package mhfc.heltrato.common.ai.tigrex;

import java.util.Random;

import mhfc.heltrato.common.ai.AIAnimation;
import mhfc.heltrato.common.entity.mob.EntityTigrex;
import mhfc.heltrato.common.entity.projectile.EntityTigrexBlock;
import net.minecraft.entity.EntityLivingBase;

public class AITigrexThrow extends AIAnimation{
	
	private EntityTigrex entity;
	private EntityLivingBase target;
	 private Random rand;
	public AITigrexThrow(EntityTigrex type) {
		super(type);
		entity = type;
		rand = type.getRNG();
	}
	
	public boolean shouldAnimate() {
		target = entity.getAttackTarget();
		if ((target == null) || ((!entity.onGround) && (!entity.isInWater()))) return false;
	//	if (entity.canEntityBeSeen(target)) return false;
		if(entity.getDistanceSqToEntity(target) < 2D && entity.getDistanceSqToEntity(target) > 3.5D) return false;
		return entity.getAnimID() == 0 && rand.nextInt(12) == 0;
	}

	public int getAnimID() {
		return 2;
	}

	public boolean isAutomatic() {
		return false;
	}

	public int getDuration() {
		return 30;
	}
	
	public void resetTask(){
		super.resetTask();
	}
	
	public void updateTask() {
			entity.renderYawOffset = entity.rotationYaw;
			if(entity.animTick == 12) {
			float f = 0.7F + this.rand.nextFloat() * 0.2F;
			entity.worldObj.playSoundAtEntity(entity, "mhfc:tigrex.say", 0.8f, f);
			EntityTigrexBlock block = new EntityTigrexBlock(entity.worldObj, entity);
			block.posY += 0.00000976158142D;
			block.posX += 2.9459D;
			block.posZ += 0.08D;
			double x = target.posX - block.posX;
			double y = target.posY - block.posY;
			double z = target.posZ - block.posZ;
		    double xz = Math.sqrt(x * x + z * z);
			block.setThrowableHeading(x, y + xz * 0.4000000059604645D, z, 0.9F, 1.0F);
			entity.worldObj.spawnEntityInWorld(block);
		  }
	  }

}
