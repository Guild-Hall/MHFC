package mhfc.net.common.ai;

import java.util.List;

import mhfc.net.common.entity.type.EntityWyvernHostile;
import mhfc.net.common.entity.type.EntityWyvernPeaceful;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;

public class AIUtils {
	/**
	 * A constant to convert from radiant to degrees.<br>
	 * <code>rad*RAD2DEG == deg</code>
	 */
	public static final double RAD2DEG = 180d / Math.PI;
	/**
	 * A constant to convert from degrees to radiant.<br>
	 * <code>rad*DEG2RAD == deg</code>
	 */
	public static final double DEG2RAD = Math.PI / 180d;

	public static interface IDamageCalculator {
		/**
		 * Given an entity e calculates the damage done to it.
		 *
		 * @param e
		 * @return
		 */
		public float accept(Entity e);
	}
	/**
	 * A default implementation for {@link IDamageCalculator}. It decides
	 *
	 * @author WorldSEnder
	 *
	 */
	private static class DefDamageCalculator implements IDamageCalculator {
		private float player;
		private float wyvern;
		private float rest;

		public DefDamageCalculator(float player, float wyvern, float rest) {
			this.player = player;
			this.wyvern = wyvern;
			this.rest = rest;
		}

		@Override
		public float accept(Entity trgt) {
			if (trgt instanceof EntityPlayer) {
				return player;
			} else if (trgt instanceof EntityWyvernHostile
					|| trgt instanceof EntityWyvernPeaceful) {
				return wyvern;
			}
			return rest;
		}
	}
	/**
	 * Damages all entities who collide with the given Entity
	 *
	 * @param ai
	 *            the entity to check collision against
	 */
	public static void damageCollidingEntities(EntityLivingBase ai,
			final float damage) {
		damageCollidingEntities(ai, new IDamageCalculator() {
			@Override
			public float accept(Entity e) {
				return damage;
			}
		});
	}
	/**
	 * The same as {@link #damageCollidingEntities(EntityLivingBase, float)} but
	 * with an {@link IDamageCalculator} that returns the damage that is
	 * inflicted for every entity.
	 *
	 * @param ai
	 *            the entity to collide with
	 * @param damageCalc
	 *            a damage calculator
	 * @see IDamageCalculator
	 */
	public static void damageCollidingEntities(EntityLivingBase ai,
			IDamageCalculator damageCalc) {
		List<Entity> collidingEntities = WorldHelper.collidingEntities(ai);
		for (Entity trgt : collidingEntities) {
			float damage = damageCalc.accept(trgt);
			trgt.attackEntityFrom(DamageSource.causeMobDamage(ai), damage);
		}

	}

	public static IDamageCalculator defaultDamageCalc(final float player,
			final float wyvern, final float rest) {
		return new DefDamageCalculator(player, wyvern, rest);
	}

	public static float lookVecToYaw(Vec3 vec) {
		double cos = Math.acos(vec.xCoord);
		if (vec.zCoord < 0)
			cos *= -1;
		cos *= RAD2DEG;
		return (float) cos;
	}

}
