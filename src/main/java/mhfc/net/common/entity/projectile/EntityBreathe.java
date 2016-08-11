package mhfc.net.common.entity.projectile;

import java.util.List;

import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.weapon.stats.ElementalType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFlameFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
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

	public EntityBreathe(World the_World, EntityLivingBase the_Entity, boolean isFire) {
		super(the_World, the_Entity);
		flameable = isFire;
	}

	@Override
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
				if (flameable && entity instanceof EntityPlayer) {
					entity.attackEntityFrom(ElementalType.Fire.damageSource, 115);
				}
				entity.setFire(5);
				if (entity instanceof EntityMHFCBase) {
					entity.attackEntityFrom(ElementalType.Fire.damageSource, 335);

				} else {
					entity.attackEntityFrom(ElementalType.Fire.damageSource, 9999999);
				}

			}
		}
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		for (int i = 0; i < 20; i++) {

			double timePassed = (ticksExisted / 2 + (i * 40)) / 10;
			double particlePositionX = Math.cos(timePassed) * 2;
			double particlePositionY = Math.sin(timePassed) * 0.2;
			double particlePositionZ = Math.sin(timePassed) * 2;
			EntityFlameFX particle = new EntityFlameFX(
					worldObj,
					posX + particlePositionX,
					posY + particlePositionY,
					posZ + particlePositionZ,
					0,
					0,
					0); // Flame particle constructor
			particle.setRBGColorF(0.3F, 0.1F, 0.6F); // Sets the color.

			Minecraft.getMinecraft().effectRenderer.addEffect(particle);
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
