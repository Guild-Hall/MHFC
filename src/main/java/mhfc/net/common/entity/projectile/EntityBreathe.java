package mhfc.net.common.entity.projectile;

import java.util.List;

import mhfc.net.common.entity.monster.EntityDeviljho;
import mhfc.net.common.entity.type.EntityMHFCBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * ~~ This will be base of all Entity Breathe of the monsters. Nothing special thou just a base projectile .. Make a
 * constructor and include your specified monster. add special attributes base on the methods and functions below..
 * Simple @author Heltrato
 * 
 */
public class EntityBreathe extends EntityThrowable {

	protected boolean flameable;

	public EntityBreathe(World the_World) {
		super(the_World);
	}

	public EntityBreathe(World the_World, EntityDeviljho the_Entity, boolean isFire) {
		super(the_World);
		flameable = isFire;
	}

	protected float getGravityVelocity() {
		return 0.01F;
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {
		@SuppressWarnings("unchecked")
		List<Entity> list = this.worldObj
				.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(3.5D, 3.0D, 3.5D));
		list.remove(getThrower());

		for (Entity entity : list) {
			if (getDistanceSqToEntity(entity) <= 6.25D) {
				if (flameable && entity instanceof EntityPlayer)
					entity.attackEntityFrom(DamageSource.causeMobDamage(getThrower()), 90);
				entity.setFire(5);
				if (entity instanceof EntityMHFCBase) {
					entity.attackEntityFrom(DamageSource.causeMobDamage(getThrower()), 300);

				} else {
					entity.attackEntityFrom(DamageSource.causeMobDamage(getThrower()), 9999999f);
				}

			}
		}
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		for (int i = 0; i < 20; i++) {
			worldObj.spawnParticle(
					"lava",
					posX,
					posY,
					posZ,
					rand.nextGaussian(),
					rand.nextGaussian(),
					rand.nextGaussian());
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
