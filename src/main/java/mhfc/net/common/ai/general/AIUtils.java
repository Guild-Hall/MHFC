package mhfc.net.common.ai.general;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.google.common.base.Preconditions;

import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.helper.DamageHelper;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
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

	public static abstract class DecisiveDamageCalculator implements IDamageCalculator {

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
			if (shouldDamage(e)) {
				return damage(e);
			}
			return 0f;
		}
	}

	/**
	 * This special damage calculator remembers which entities were damaged by it and only damages entities once until
	 * it is reset.
	 */
	public static class MemoryDamageCalculator extends DecisiveDamageCalculator {

		private final Set<Entity> damagedEntities = new HashSet<>();
		private IDamageCalculator forward;

		public MemoryDamageCalculator(IDamageCalculator otherCalculator) {
			forward = Objects.requireNonNull(otherCalculator);
		}

		@Override
		public boolean shouldDamage(Entity e) {
			return !damagedEntities.contains(e);
		}

		@Override
		public float damage(Entity e) {
			damagedEntities.add(e);
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
			} else if (trgt instanceof EntityMHFCBase) {
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
			if (calculator == null) {
				type = Type.NONE;
			} else {
				type = Type.UNKNOWN;
			}
		}

		public void setDefaultDamageCalculator(float player, float wyvern, float rest) {
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
		 * Create a new MemoryDamageCalculator using a default calculator with the provided damage values.
		 */
		public void setMemoryDamageCalculator(float player, float wyvern, float rest) {
			calculator = new MemoryDamageCalculator(AIUtils.defaultDamageCalc(player, wyvern, rest));
			type = Type.MEMORY;
		}

		/**
		 * Resets the damage calculator if it is of memory type. Does nothing if not.
		 */
		public void reset() {
			if (calculator == null) {
				return;
			}
			if (type == Type.MEMORY) {
				((MemoryDamageCalculator) calculator).reset();
			}
			if (type == Type.UNKNOWN) {
				if (calculator instanceof MemoryDamageCalculator) {
					((MemoryDamageCalculator) calculator).reset();
				}
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
	public static void damageCollidingEntities(EntityLivingBase ai, final float damage) {
		damageCollidingEntities(ai, e -> damage);
	}

	/**
	 * The same as {@link #damageCollidingEntities(EntityLivingBase, float)} but with an {@link IDamageCalculator} that
	 * returns the damage that is inflicted for every entity.
	 *
	 * @param ai
	 *            the entity to collide with
	 * @param damageCalc
	 *            a damage calculator
	 * @see IDamageCalculator
	 */
	public static void damageCollidingEntities(EntityLivingBase ai, IDamageCalculator damageCalc) {
		List<Entity> collidingEntities = WorldHelper.collidingEntities(ai);
		
		for (Entity trgt : collidingEntities) {
			float damage = damageCalc.accept(trgt);
			if(trgt instanceof EntityPlayer || trgt instanceof EntityMHFCBase){
			
			trgt.attackEntityFrom(DamageSource.causeMobDamage(ai), damage);
		}
			else{
				trgt.attackEntityFrom(DamageHelper.anti	, damage);
			}
		}
			
	}

	public static IDamageCalculator defaultDamageCalc(final float player, final float wyvern, final float rest) {
		return new DefDamageCalculator(player, wyvern, rest);
	}

	/**
	 * Gives the yaw of a vector, or throws an exception of no yaw exists
	 *
	 * @param vec
	 * @return
	 * @see #lookVecToYawUnsafe(Vec3)
	 */
	public static float lookVecToYaw(Vec3 vec) {
		float unsafeResult = lookVecToYawUnsafe(vec);
		if (Float.isNaN(unsafeResult)) {
			throw new IllegalArgumentException("The vector may not have zero length");
		}
		return unsafeResult;
	}

	/**
	 * Gives the yaw of a vector, or NaN if the no yaw exists.
	 *
	 * @see #lookVecToYaw(Vec3)
	 */
	public static float lookVecToYawUnsafe(Vec3 vec) {
		Objects.requireNonNull(vec);
		if (vec.lengthVector() == 0) {
			return Float.NaN;
		}
		vec = vec.normalize();
		if (vec.xCoord == 0 && vec.zCoord == 0) {
			return Float.NaN;
		}
		double pitch_rad = Math.asin(vec.yCoord);
		double cos_pitch = Math.cos(pitch_rad);
		double adjusted_z = vec.zCoord / cos_pitch;
		// Corrects rounding issues
		adjusted_z = Math.max(-1.0, Math.min(1.0, adjusted_z));
		double yaw_rad = Math.acos(adjusted_z);
		yaw_rad *= Math.signum(-vec.xCoord);
		return normalizeAngle((float) Math.toDegrees(yaw_rad));
	}

	/**
	 * Returns the yaw that gives the direction of the target but with a maximum absolute value of max
	 *
	 * @param look
	 *            A normalized look vector
	 * @param target
	 *            A normalized vector, the target for the look
	 * @param maxAbsoluteChange
	 *            The maximum allowed change of the look in degrees. Must be greater than zero
	 * @return Float.NaN if look doesn't represent a vector with yaw, the current yaw if target can't be turned to
	 *         (maybe right above), else the new yaw.
	 */
	public static float modifyYaw(Vec3 look, Vec3 target, float maxAbsoluteChange) {
		Preconditions.checkArgument(maxAbsoluteChange >= 0, "max change must be greater than 0");

		float yaw = lookVecToYaw(look);
		float tarYaw = lookVecToYaw(target);
		if (Float.isNaN(yaw)) {
			return Float.NaN;
		} else if (Float.isNaN(tarYaw)) {
			return yaw;
		}

		float diff = tarYaw - yaw;
		diff = normalizeAngle(diff);
		diff = Math.signum(diff) * Math.min(Math.abs(diff), maxAbsoluteChange);
		return normalizeAngle(yaw + diff);
	}

	/**
	 * Transforms an angle into the Minecraft specific angle between -180 and 180 degrees.
	 */
	public static float normalizeAngle(float yaw) {
		yaw = yaw % 360;
		if (yaw > 180) {
			return yaw - 360;
		} else if (yaw < -180) {
			return yaw + 360;
		} else {
			return yaw;
		}
	}

	/**
	 * Determines if the length of the direction vector lies between the arguments. Both sides of the range are
	 * inclusive.
	 */
	public static boolean isInDistance(Vec3 direction, double minDistance, double maxDistance) {
		double distance = direction.lengthVector();
		return distance >= minDistance && distance <= maxDistance;
	}

	/**
	 * Returns the yaw under which the given target is viewed by the actor. Negative values represent the left side of
	 * the actor, positive ones the right.
	 */
	public static float getViewingAngle(EntityLiving actor, Entity target) {
		Vec3 targetVector = WorldHelper.getVectorToTarget(actor, target);
		return getViewing(actor, targetVector);
	}

	public static float getViewingAngle(EntityLiving actor, Vec3 point) {
		Vec3 pos = WorldHelper.getEntityPositionVector(actor);
		Vec3 targetVector = pos.subtract(point);
		return getViewing(actor, targetVector);
	}

	/**
	 * @param actor
	 * @param toTarget
	 * @return the yaw the target is viewed at, or NaN if no such yaw exists.
	 */
	private static float getViewing(EntityLiving actor, Vec3 toTarget) {
		Vec3 lookVector = actor.getLookVec();
		float yaw = lookVecToYawUnsafe(lookVector);
		if (Float.isNaN(yaw)) {
			return yaw;
		}
		float tarYaw = lookVecToYawUnsafe(toTarget.normalize());
		if (Float.isNaN(tarYaw)) {
			return tarYaw;
		}
		return normalizeAngle(tarYaw - yaw);
	}

	public static List<AxisAlignedBB> gatherOverlappingBounds(AxisAlignedBB bounds, Entity entity) {

		int minX = (int) Math.floor(bounds.minX), //
				maxX = (int) Math.ceil(bounds.maxX);
		int minY = (int) Math.floor(bounds.minY), //
				maxY = (int) Math.ceil(bounds.maxY);
		int minZ = (int) Math.floor(bounds.minZ), //
				maxZ = (int) Math.ceil(bounds.maxZ);

		List<AxisAlignedBB> list = new ArrayList<>();

		for (int xC = minX; xC <= maxX; xC++) {
			for (int yC = minY; yC <= maxY; yC++) {
				for (int zC = minZ; zC <= maxZ; zC++) {
					entity.worldObj.getBlock(xC, yC, zC)
							.addCollisionBoxesToList(entity.worldObj, xC, yC, zC, bounds, list, entity);
				}
			}
		}

		return list;

	}

}
