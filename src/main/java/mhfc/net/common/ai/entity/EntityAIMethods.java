package mhfc.net.common.ai.entity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.pathfinding.Path;
import net.minecraft.potion.PotionEffect;

public abstract class EntityAIMethods {

	private EntityAIMethods() {}

	/**
	 * TODO REMOVE ENTITYAIMETHODS AND MOVE ALL METHODS HERE TO ENTITYMHFCBASE !
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

	public static void charge(EntityCreature attacker, EntityLivingBase target, double moveSpeed) {
		Path pathentity = attacker.getNavigator().getPathToEntityLiving(target);
		if (pathentity == null) {
			return;
		}
		attacker.getNavigator().setPath(pathentity, moveSpeed);
	}

	public static void sleepRegeneration(EntityCreature entity, float amount) {
		entity.heal(amount);
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
