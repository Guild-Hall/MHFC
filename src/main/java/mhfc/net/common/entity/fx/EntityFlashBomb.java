package mhfc.net.common.entity.fx;

import java.util.List;

import mhfc.net.common.core.registry.MHFCPotionRegistry;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityFlashBomb extends EntityThrowable {

	public static final int EXPLOSIVE_TIMER = 20;
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
	
    public static void registerFixesThrowable(DataFixer fixer, String name) {
    	EntityThrowable.registerFixesThrowable(fixer, "Flashbomb");
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
	protected void onImpact(RayTraceResult traceResult) {
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
		AxisAlignedBB explosionRange = new AxisAlignedBB(
				posX - FALL_OFF_END,
				posY - FALL_OFF_END,
				posZ - FALL_OFF_END,
				posX + FALL_OFF_END,
				posY + FALL_OFF_END,
				posZ + FALL_OFF_END);
		List<Entity> entites = this.world.getEntitiesWithinAABBExcludingEntity(this, explosionRange);
		for (Entity e : entites) {
			if (!(e instanceof EntityLivingBase)) {
				continue;
			}
			EntityLivingBase living = (EntityLivingBase) e;
			Vec3d lookVec = living.getLookVec();
			Vec3d toTargetVec = WorldHelper.getVectorToTarget(this, living);
			double dot = lookVec == null ? -1 : lookVec.normalize().dotProduct(toTargetVec.normalize());
			int effectStrength = (int) (toTargetVec.lengthVector() * -dot);
			if (effectStrength > 0) {
				living.addPotionEffect(
						new PotionEffect(MHFCPotionRegistry.getRegistry().flashed, EXPLOSION_TICKS, effectStrength));
			}
		}
	}
}
