package mhfc.heltrato.common.ai.tigrex;

import java.util.List;
import java.util.Random;

import mhfc.heltrato.common.ai.AIAnimation;
import mhfc.heltrato.common.entity.mob.EntityTigrex;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class AITigrexRoar extends AIAnimation{
	
	private EntityTigrex entity;
	private EntityLivingBase target;
	private Random rand;

	public AITigrexRoar(EntityTigrex type) {
		super(type);
		entity = type;
		target = null;
		rand = type.getRNG();
	}
	
	public boolean shouldAnimate() {
		target = entity.getAttackTarget();
		if((target == null) && (!entity.onGround))return false;
		return rand.nextFloat() * 100.0F < 1D;
		
	}

	public int getAnimID() {
		return 4;
	}

	public boolean isAutomatic() {
		return false;
	}

	public int getDuration() {
		return 100;
	}
	
	public void updateTask(){
		
		List list = entity.worldObj.getEntitiesWithinAABBExcludingEntity(entity, entity.boundingBox.expand(8D, 6.0D, 8D));
		list.remove(entity);
		
		for (int i = 0; i < list.size(); i++) {
			Entity e = (Entity)list.get(i);
		if(entity.getAnimTick() == 5){
			if(e instanceof EntityLivingBase){
				((EntityLivingBase) e).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 60, 5));
			}
		}
		if(entity.getAnimTick() < 85){
			
			if (entity.getDistanceSqToEntity(e) <= 196.0D) {
			double x = e.posX - this.entity.posX;
			double z = entity.posZ - this.entity.posZ;
			double d = Math.sqrt(x * x + z * z);
			e.motionX = (x / d * 0.699999988079071D);
			e.motionY = 0.300000011920929D;
			e.motionZ = (z / d * 0.699999988079071D);
			
		}
		}
		}
		
		if(entity.getAnimTick() == 10){
			
			float f = 0.7F + this.rand.nextFloat() * 0.2F;
			entity.worldObj.playSoundAtEntity(entity, "mhfc:tigrex.roar", 0.8f, f);
			
		}
	}
	}
