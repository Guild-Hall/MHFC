package mhfc.net.common.ai;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

	public static abstract class DecisiveDamageCalculator
		implements
			IDamageCalculator {

		/**
		 * Decides whether the given Entity should be damaged
		 */
		public abstract boolean shouldDamage(Entity e);

		/**
		 * Returns the damage that should be dealt to the entity e.
		 */
		public abstract float damage(Entity e);

		@Override
		public float accept(Entity e) {
			if (shouldDamage(e))
				return damage(e);
			return 0f;
		}
	}

	public static class MemoryDamageCalculator extends DecisiveDamageCalculator {

		private final Set<Entity> damagedEntities = new HashSet<Entity>();
		private IDamageCalculator forward;

		public MemoryDamageCalculator(IDamageCalculator otherCalculator) {
			forward = otherCalculator;
		}

		@Override
		public boolean shouldDamage(Entity e) {
			return !damagedEntities.contains(e);
		}

		@Override
		public float damage(Entity e) {
			return forward.accept(e);
		}

		public void reset() {
			damagedEntities.clear();
		}

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

	public static class DamageCalculatorHelper {
		private enum Type {
			MEMORY,
			DEFAULT,
			NONE,
			UNKNOWN;
		}

		private IDamageCalculator calculator;
		private Type type;

		public DamageCalculatorHelper() {
			type = Type.NONE;
		}

		public void setDamageCalculator(IDamageCalculator dmg) {
			calculator = dmg;
			if (calculator == null)
				type = Type.NONE;
			else
				type = Type.UNKNOWN;
		}

		public void setDefaultDamageCalculator(float player, float wyvern,
			float rest) {
			calculator = AIUtils.defaultDamageCalc(player, wyvern, rest);
			type = Type.DEFAULT;
		}

		/**
		 * Create a new MemoryDamageCalculator using the provided calculator
		 */
		public void setMemoryDamageCalculator(IDamageCalculator dmg) {
			calculator = new MemoryDamageCalculator(dmg);
			type = Type.MEMORY;
		}

		/**
		 * Create a new MemoryDamageCalculator using a default calculator with
		 * the provided damage values.
		 */
		public void setMemoryDamageCalculator(float player, float wyvern,
			float rest) {
			calculator = new MemoryDamageCalculator(AIUtils.defaultDamageCalc(
				player, wyvern, rest));
			type = Type.MEMORY;
		}

		/**
		 * Resets the damage calculator if it is of memory type. Does nothing if
		 * not.
		 */
		public void reset() {
			if (type == Type.MEMORY)
				((MemoryDamageCalculator) calculator).reset();
			if (type == Type.UNKNOWN) {
				if (calculator instanceof MemoryDamageCalculator)
					((MemoryDamageCalculator) calculator).reset();
			}
		}

		public IDamageCalculator getCalculator() {
			return calculator;
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
		double tan = Math.atan(vec.xCoord / vec.zCoord);
		tan *= -RAD2DEG;
		if (vec.zCoord < 0)
			tan += 180;
		if (tan > 180)
			tan -= 360;
		return (float) tan;
	}

	public static float modifyYaw(Vec3 look, Vec3 target, float max) {
		float yaw = lookVecToYaw(look);
		float tarYaw = lookVecToYaw(target);
		float diff = tarYaw - yaw;
		if (diff > 180)
			diff -= 360;
		else if (diff < -180)
			diff += 360;
		if (diff < 0) {
			diff = diff < -max ? -max : diff;
		} else {
			diff = diff > max ? max : diff;
		}
		if (yaw + diff > 180)
			diff -= 360;
		else if (yaw + diff < -180)
			diff += 360;
		return yaw + diff;
	}

}
