package mhfc.net.common.util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.IntFunction;

import mhfc.net.common.entity.projectile.EntityLightning;
import mhfc.net.common.entity.type.EntityWyvernHostile;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class Utilities {
	private static Random rand = new Random();
	
	public void addWeaponElementFX(EntityLivingBase entityLiving, ItemStack stack) {
		
	}
	
	/**
	 * For all nearby entities that are attacking the EntityLiving, resets the
	 * attack and revenge target
	 *
	 * @param living
	 */
	public static void removeAttackers(EntityLiving living) {
		@SuppressWarnings("unchecked")
		List<EntityLiving> list = living.worldObj.getEntitiesWithinAABB(EntityLiving.class,
				living.boundingBox.expand(16.0D, 10.0D, 16.0D));
		for (EntityLiving attacker : list) {
			if ((attacker != living) && (attacker.getAttackTarget() == living)) {
				attacker.setAttackTarget(null);
				attacker.setRevengeTarget(null);
			}
		}
	}

	public static void chargeMobToEntity(EntityWyvernHostile chargingEntity, Entity target, float distance,
			float moveSpeed, boolean dependsonWater) {
		PathEntity pathentity = chargingEntity.worldObj.getPathEntityToEntity(chargingEntity, target, 16, false, false,
				dependsonWater, true);
		if ((pathentity != null) && (distance < 12.0F)) {
			chargingEntity.setPathToEntity(pathentity);
			chargingEntity.speed = moveSpeed;
		}
		if ((target != null) && ((dependsonWater = true))) {
			chargingEntity.speed = moveSpeed / 2D;
		}
	}

	/**
	 * Counts the number of players in a world // FIXME: actually useful?
	 *
	 * @param worldObj
	 * @return
	 */
	public static int countPlayers(WorldServer worldObj) {
		return worldObj.playerEntities.size();
	}

	public static void spawnLightnings(double Lx, double Ly, double Lz, int many, World world) {
		for (int i = 0; i < many; i++) {
			EntityLightning l = new EntityLightning(world);
			l.setPosition(Lx, Ly, Lz);
			world.spawnEntityInWorld(l);
		}
	}

	/*
	 * TODO nullDamage is chance that all incoming singe target (projectiles ,
	 * not aoe) has a 1 in 5 chance to be block and will be feature on update on
	 * lance. - `Heltrato
	 */
	public static float nullifyDamage(DamageSource source, float damage) {
		if (rand.nextInt(5) == 0 && source.isProjectile()) {
			return 0;
		}
		return damage;
	}

	public static void knockBack(EntityLivingBase attacker, EntityLivingBase entity, float knockback) {

		int knockBackModifier = EnchantmentHelper.getKnockbackModifier(attacker, entity);
		if (attacker.isSprinting()) {
			knockBackModifier++;
		}
		// float f2 = 1F / 0.4F;

		// attackEntityFrom part
		double dx = attacker.posX - entity.posX;
		double dz = attacker.posZ - entity.posZ;

		for (; dx * dx + dz * dz < 1E-4D; dz = (Math.random() - Math.random()) * 0.01D) {
			dx = (Math.random() - Math.random()) * 0.01D;
		}

		entity.attackedAtYaw = (float) ((Math.atan2(dz, dx) * 180D) / Math.PI) - entity.rotationYaw;

		// knockBack part
		float f = MathHelper.sqrt_double(dx * dx + dz * dz);
		entity.motionX -= (dx / f) * knockback;
		entity.motionY += knockback;
		entity.motionZ -= (dz / f) * knockback;
		if (entity.motionY > 0.4D) {
			entity.motionY = 0.4D;
		}

		if (knockBackModifier > 0) {
			dx = -Math.sin(Math.toRadians(attacker.rotationYaw)) * knockBackModifier * 0.5F;
			dz = Math.cos(Math.toRadians(attacker.rotationYaw)) * knockBackModifier * 0.5F;
			entity.addVelocity(dx, 0.1D, dz);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T, R> T[] mapAll(Function<? super R, T> func, R[] holders, IntFunction<T[]> arrNew) {
		return (T[]) Arrays.stream(holders).sequential().map(func).toArray(arrNew);
	}
}
