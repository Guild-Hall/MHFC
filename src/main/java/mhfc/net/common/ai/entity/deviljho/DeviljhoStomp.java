package mhfc.net.common.ai.entity.deviljho;

import java.util.List;
import java.util.Random;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.entity.monster.EntityDeviljho;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class DeviljhoStomp extends ActionAdapter<EntityDeviljho> {

	
	private static final int LAST_FRAME = 55;
	private static final IDamageCalculator damageCalc = AIUtils.defaultDamageCalc(60f, 50F,9999999f);
	private static final double MAX_DIST = 9f;
	private static final float WEIGHT = 7;
	
	public DeviljhoStomp() {
		setAnimation("mhfc:models/Deviljho/DeviljhoStomp.mcanm");
		setLastFrame(LAST_FRAME);
	}
	
	
	

	@Override
	public float getWeight() {
		EntityDeviljho entity = this.getEntity();
		target = entity.getAttackTarget();
		if (target == null) {
			return DONT_SELECT;
		}
		Vec3 toTarget = WorldHelper.getVectorToTarget(entity, target);
		double dist = toTarget.lengthVector();
		if (dist > MAX_DIST) {
			return DONT_SELECT;
		}
		return WEIGHT;
	}
	
	@Override
	protected void update() {
		EntityDeviljho entity = this.getEntity();
		target = entity.getAttackTarget();
		
		if (this.getCurrentFrame() == 26) {
			List list = entity.worldObj.getEntitiesWithinAABBExcludingEntity(entity, entity.boundingBox.expand(9.0D, 1.0D, 9.0D));
			if(!entity.worldObj.isRemote){
			if (entity.onGround) {
				
				// Entity PARTICLE FX !!
				Random random = new Random();
				 int j = MathHelper.floor_double(entity.posX);
				 int i = MathHelper.floor_double(entity.posY - 0.20000000298023224D - entity.yOffset);
				 int k = MathHelper.floor_double(entity.posZ);
				 Block block = entity.worldObj.getBlock(j, i, k);
				 if (block != Blocks.air) {
					 for (int i1 = 0; i1 < 3000; i1++) {
						 entity.worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(block) + "_" + entity.worldObj.getBlockMetadata(j, i1, k), entity.posX - 5.0D + random.nextInt(1000) / 100, entity.posY - 1.4D, entity.posZ - 5.0D + random.nextInt(1000) / 100, 0.0D, 0.0D, 0.0D);
					 }
				 }
				 
				 //ENTITY MOTION DAMAGE  
				 if (list != null){
					 for (int p = 0; p < list.size(); p++)
					 {
						 if ((list.get(p) instanceof EntityLivingBase)){  //hope this should work	
							 if (((EntityLivingBase)list.get(p)).onGround) {
								 ((EntityLivingBase)list.get(p)).attackEntityFrom(DamageSource.causeMobDamage(entity), 60.0F);
								 ((EntityLivingBase)list.get(p)).motionY = 0.4D;
								 ((EntityLivingBase)list.get(p)).motionX = 0.0D;
								 ((EntityLivingBase)list.get(p)).motionZ = 0.0D;
							 }
							 
						 }
					 }
				 }
				 
			}
			
			}
			//DAMAGE AT FEET STOMP 
			AIUtils.damageCollidingEntities(getEntity(), damageCalc);
			getEntity().playSound("mhfc:deviljho-bite", 1.0F, 1.0F);
			
		}
		if (isMoveForwardFrame(getCurrentFrame())) {
			EntityDeviljho e = getEntity();
			e.moveForward(1, false);
		}
		
	}
	
	
	private boolean isMoveForwardFrame(int frame) {
		return (frame > 20 && frame < 30);
	}
	

}
