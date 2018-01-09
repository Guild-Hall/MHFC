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

public abstract class EntityAIMethods {

	private EntityAIMethods() {}

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

	public static void circleEntity(
			EntityCreature entity,
			Entity target,
			float radius,
			float speed,
			boolean direction,
			int circleFrame,
			float offset,
			float moveSpeedMultiplier) {
		int directionInt = direction ? 1 : -1;
		entity.getNavigator().tryMoveToXYZ(
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


	public static void camShake(Entity e, float dist, float intensity) {

		if (e.world.isRemote) {
			boolean camshaker = false;
			float camshakerintensity;
			List<EntityPlayer> players = e.world
					.getEntitiesWithinAABB(EntityPlayer.class, e.getEntityBoundingBox().expand(dist, 4, dist));
			camshaker = (camshaker == false) ? true : false;
			camshakerintensity = (camshaker) ? intensity : -intensity;
			for (EntityPlayer player : players) {
				player.rotationPitch = camshakerintensity;
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

	public static List<EntityLivingBase> getEntityLivingBaseNearby(
			Entity entity,
			double distanceX,
			double distanceY,
			double distanceZ,
			double radius) {
		return getEntitiesNearby(entity, EntityLivingBase.class, distanceX, distanceY, distanceZ, radius);
	}

	public static <T extends Entity> List<T> getEntitiesNearby(Entity entity, Class<T> entityClass, double r) {
		return entity.world.getEntitiesWithinAABB(
				entityClass,
				entity.getEntityBoundingBox().expand(r, r, r),
				e -> e != entity && entity.getDistanceToEntity(e) <= r);
	}

	public static <T extends Entity> List<T> getEntitiesNearby(
			Entity entity,
			Class<T> entityClass,
			double dX,
			double dY,
			double dZ,
			double r) {
		return entity.world.getEntitiesWithinAABB(
				entityClass,
				entity.getEntityBoundingBox().expand(dX, dY, dZ),
				e -> e != entity && entity.getDistanceToEntity(e) <= r && e.posY <= entity.posY + dY);
	}

	public static double getAngleBetEntities(Entity first, Entity second) {
		return Math.atan2(second.posZ - first.posZ, second.posX - first.posX) * (180 / Math.PI) + 90;
	}

}
