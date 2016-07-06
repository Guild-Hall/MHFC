package mhfc.net.common.ai.entity;

import java.util.List;
import java.util.Random;

import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;

public class AIGameplayComposition {
	
	/** 
	 *  So I have an idea that this will be the place where all AI methods well technically common 
	 *  graphics materials like for example : The Stomp Particles from Deviljho AI will be placed.
	 *  
	 *  This will be the class that where all common gameplay of the AI will be placed. Example 
	 *  
	 *  The Stomp Effect from Deviljho stomp. of course other monsters would use that too. so aside 
	 *  from adding some updates in the General AI's which may not be final and can cause conflicts 
	 *  soon this will be a place for this methods to avoid bad casualties...
	 *  
	 *  
	 *  If you have Suggestions just post issue
	 *  
	 *  @author Heltrato
	 *  
	 *  TODO: Add all the basic variations
	 * 
	 * 
	 * 
	 **/
	
	
	public static void AIStompCrackGameplay(Entity entity, int incrementLength)  {
		Random random = new Random();
		int a = MathHelper.floor_double(entity.posX);
		int b = MathHelper.floor_double(entity.posY);
		int c = MathHelper.floor_double(entity.posZ);
		Block block = entity.worldObj.getBlock(a, b - 1, c);
		if (block != Blocks.air) {
			block = Blocks.dirt;
		}
		for (int x = 0; x < incrementLength; x++) {
			for (int z = 0; z < incrementLength; z++) {
				entity.worldObj.spawnParticle(
						"blockcrack_" + Block.getIdFromBlock(block) + "_0",
						entity.posX - 5.0D + x,
						entity.posY + 0.5D,
						entity.posZ - 5.0D + z,
						random.nextGaussian(),
						random.nextGaussian(),
						random.nextGaussian());
			}
		}
	}
	
	public static void AIChargeGameplay(EntityCreature entity, EntityLivingBase entityLivingBase, double moveSpeed, boolean inWater) {
		PathEntity pathentity = entityLivingBase.worldObj.getPathEntityToEntity(entityLivingBase, entityLivingBase, 30F, false, false, inWater, true);
		entity.setPathToEntity(pathentity);
	}
	
	public static void AILaunchGameply(Entity entity, double x, double y, double z) {
		List<Entity> collidingEnts = WorldHelper.collidingEntities(entity);
		if (!entity.worldObj.isRemote) {
			for (Entity collider : collidingEnts) {
				collider.addVelocity(x, y, z);
			}
		}
	}
	
	public static void AIRoarEffectGameplay(EntityLivingBase target) {
		if (target instanceof EntityPlayer && ((EntityPlayer) target).capabilities.isCreativeMode)
			return;
		target.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 80, 10));
		target.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 80, 10));
	}

}
