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
import net.minecraft.init.MobEffects;
import net.minecraft.pathfinding.Path;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public abstract class EntityAIMethods extends EntityCreature {

	public EntityAIMethods(World worldIn) {
		super(worldIn);
	}

	/**
	 * So I have an idea that this will be the place where all AI methods well technically common graphics materials
	 * like for example : The Stomp Particles from Deviljho AI will be placed.
	 *
	 * This will be the class that where all common gameplay of the AI will be placed. Example
	 *
	 * The Stomp Effect from Deviljho stomp. of course other monsters would use that too. so aside from adding some
	 * updates in the General AI's which may not be final and can cause conflicts soon this will be a place for this
	 * methods to avoid bad casualties...
	 *
	 *
	 * If you have Suggestions just post issue
	 *
	 * @author Heltrato
	 *
	 *         TODO: Add all the basic variations
	 *
	 *
	 *
	 **/

	public void circleEntity(
			Entity target,
			float radius,
			float speed,
			boolean direction,
			int circleFrame,
			float offset,
			float moveSpeedMultiplier) {
		int directionInt = direction ? 1 : -1;
		this.getNavigator().tryMoveToXYZ(
				target.posX + radius * Math.cos(directionInt * circleFrame * 0.5 * speed / radius + offset),
				target.posY,
				target.posZ + radius * Math.sin(directionInt * circleFrame * 0.5 * speed / radius + offset),
				speed * moveSpeedMultiplier);
	}

	public static void stompCracks(Entity entity, int incrementLength) {
		Random random = entity.world.rand;
		Block block = entity.world.getBlockState(entity.getPosition().down()).getBlock();
		if (block != Blocks.AIR) {
			block = Blocks.DIRT;
		}
		for (int x = 0; x < incrementLength; x++) {
			for (int z = 0; z < incrementLength; z++) {
				entity.world.spawnParticle(
						EnumParticleTypes.BLOCK_CRACK,
						entity.posX - 5.0D + x,
						entity.posY + 0.5D,
						entity.posZ - 5.0D + z,
						random.nextGaussian(),
						random.nextGaussian(),
						random.nextGaussian(),
						Block.getIdFromBlock(block));
			}
		}
	}

	public static void charge(EntityCreature attacker, EntityLivingBase target, double moveSpeed) {
		Path pathentity = attacker.getNavigator().getPathToEntityLiving(target);
		if (pathentity == null) {
			return;
		}
		attacker.getNavigator().setPath(pathentity, moveSpeed);
	}

	public static void launch(Entity entity, double x, double y, double z) {
		List<Entity> collidingEnts = WorldHelper.collidingEntities(entity);
		if (!entity.world.isRemote) {
			for (Entity collider : collidingEnts) {
				collider.addVelocity(x, y, z);
			}
		}
	}

	public static void sleepRegeneration(EntityCreature entity, float amount) {
		entity.heal(amount);
	}

	static boolean camShake = false;
	static float CamShakeIntensity;

	public static void camShake(EntityLivingBase e, Entity target, float dist, float intensity) {
		if (e.world.isRemote) {
			List<EntityPlayer> player = target.world
					.getEntitiesWithinAABB(EntityPlayer.class, target.getEntityBoundingBox().expand(dist, 4, dist));
			camShake = (camShake == false) ? true : false;
			CamShakeIntensity = (camShake) ? intensity : -intensity;
			for (EntityPlayer players : player) {
				players.turn(0, CamShakeIntensity);
			}
		}
	}

	public static void roarEffect(EntityLivingBase target) {
		if (target instanceof EntityPlayer && ((EntityPlayer) target).capabilities.isCreativeMode) {
			return;
		}
		target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 80, 10));
		target.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 80, 10));
	}

}
