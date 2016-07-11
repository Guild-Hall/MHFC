package mhfc.net.common.entity.projectile;

import java.util.List;

import mhfc.net.common.core.registry.MHFCPotionRegistry;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityFlashBomb extends EntityThrowable {

	public static final int EXPLOSIVE_TIMER = 40;
	public static final float REACH = 20f;
	public static final int EXPLOSION_TICKS = 40;

	public static final int FALL_OFF_BEGIN = 90;
	public static final int FALL_OFF_END = 200;

	private int ticksToExplode;
	private boolean exploded;

	public EntityFlashBomb(World par1World, double x, double y, double z) {
		super(par1World, x, y, z);
		ticksToExplode = EXPLOSIVE_TIMER;
		exploded = false;
	}

	public EntityFlashBomb(World par1World) {
		super(par1World);
		ticksToExplode = EXPLOSIVE_TIMER;
		exploded = false;
	}

	public EntityFlashBomb(World par1World, EntityLivingBase thrower) {
		super(par1World, thrower);
		ticksToExplode = EXPLOSIVE_TIMER;
		exploded = false;
	}

	@Override
	protected void onImpact(MovingObjectPosition var1) {
		explode();
	}

	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		ticksToExplode--;
		if (ticksToExplode == 0) {
			explode();
		}
	}

	private void explode() {
		if (exploded) {
			return;
		}
		exploded = true;
		setDead();
		List<Entity> entites = this.worldObj.getEntitiesWithinAABBExcludingEntity(
				this,
				this.boundingBox.expand(FALL_OFF_END, FALL_OFF_END, FALL_OFF_END));
		for (Entity e : entites) {
			if (!(e instanceof EntityLivingBase)) {
				continue;
			}
			EntityLivingBase living = (EntityLivingBase) e;
			Vec3 lookVec = living.getLookVec();
			Vec3 toTargetVec = WorldHelper.getVectorToTarget(this, living);
			double dot = lookVec == null ? -1 : lookVec.normalize().dotProduct(toTargetVec.normalize());
			int effectStrength = (int) (toTargetVec.lengthVector() * -dot);
			if (effectStrength > 0) {
				living.addPotionEffect(
						new PotionEffect(MHFCPotionRegistry.getRegistry().flashed.id, EXPLOSION_TICKS, effectStrength));
			}
		}
	}
}
