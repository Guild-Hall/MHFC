package mhfc.net.common.entity.projectile;

import java.util.List;

import mhfc.net.common.weapon.stats.ElementalType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

/**
 * ~~ This will be base of all Entity Breathe of the monsters. Nothing special thou just a base projectile .. Make a
 * constructor and include your specified monster. add special attributes base on the methods and functions below..
 * Simple @author Heltrato
 *
 */
public class EntityBreathe extends EntityThrowable {

	protected boolean flameable;

	public EntityBreathe(World world) {
		super(world);
	}

	public EntityBreathe(World world, EntityLivingBase thrower, boolean isFire) {
		super(world, thrower);
		flameable = isFire;
	}

	@Override
	protected float getGravityVelocity() {
		// Wish this exists for all entities...
		return 0.01F;
	}

	@Override
	protected void onImpact(RayTraceResult rayTrace) {
		List<Entity> list = this.world
				.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(3.5D, 3.0D, 3.5D));
		list.remove(getThrower());

		for (Entity entity : list) {
			if (getDistanceSqToEntity(entity) > 6.25D) {
				continue;
			}
			entity.setFire(5);
			if (flameable && entity instanceof EntityLivingBase) {
				ElementalType.Fire.applyTo((EntityLivingBase) entity, 115, getThrower());
			}
		}
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		for (int i = 0; i < 20; i++) {
			double semiRandomSeed = (ticksExisted / 2 + (i * 40)) / 10;
			double offsetX = Math.cos(semiRandomSeed) * 2;
			double offsetY = Math.sin(semiRandomSeed) * 0.2;
			double offsetZ = Math.sin(semiRandomSeed) * 2;
			world.spawnParticle(EnumParticleTypes.FLAME, posX + offsetX, posY + offsetY, posZ + offsetZ, 0, 0, 0);
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tagcompound) {
		super.writeEntityToNBT(tagcompound);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tagcompound) {
		super.readEntityFromNBT(tagcompound);
	}

}
