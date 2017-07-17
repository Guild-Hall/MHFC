package mhfc.net.common.entity.projectile;

import java.util.List;

import mhfc.net.common.entity.monster.EntityDeviljho;
import mhfc.net.common.entity.monster.EntityTigrex;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityProjectileBlock extends EntityThrowable {
	private final EntityFallingBlock proxy;

	public EntityProjectileBlock(World par) {
		super(par);
		setSize(1.0F, 1.0F);
		proxy = createProxy();
	}

	public EntityProjectileBlock(World world, EntityLivingBase living) {
		super(world, living);
		proxy = createProxy();
		this.posY -= living.getEyeHeight();
		Vec3d look = living.getLookVec();
		this.posX += look.xCoord * 2;
		this.posZ += look.zCoord * 2;
		rotationYaw = living.rotationYaw;
	}

	public EntityProjectileBlock(World par, EntityTigrex e) {
		this(par, (EntityLivingBase) e);
		setSize(1.3F, 1.3F);
	}

	public EntityProjectileBlock(World par, EntityDeviljho e) {
		this(par, (EntityLivingBase) e);
		setSize(1.4F, 1.4F);
	}

	public static void registerFixesThrowable(DataFixer fixer, String name) {
		EntityThrowable.registerFixesThrowable(fixer, "projectileblock");
	}

	private EntityFallingBlock createProxy() {
		return new EntityFallingBlock(world, posX, posY, posZ, Blocks.DIRT.getDefaultState());
	}

	public EntityFallingBlock getProxy() {
		return proxy;
	}

	@Override
	protected float getGravityVelocity() {
		return 0.03F;
	}

	@Override
	protected void onImpact(RayTraceResult mop) {
		List<Entity> list = this.world
				.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(3.5D, 3.0D, 3.5D));
		list.remove(getThrower());

		//	EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(
		//			this.world,
		//			this.posX,
		//			this.posY,
		//			this.posZ);
		//	entityareaeffectcloud.setOwner(this.getThrower());
		//	entityareaeffectcloud.setRadius(1.0F);
		//	entityareaeffectcloud.setDuration(100);
		//	entityareaeffectcloud
		//			.setRadiusPerTick((7.0F - entityareaeffectcloud.getRadius()) / entityareaeffectcloud.getDuration());
		//	this.world.spawnEntity(entityareaeffectcloud);

		for (Entity entity : list) {
			if (getDistanceSqToEntity(entity) > 6.25D) {
				continue;
			}
			entity.attackEntityFrom(DamageSource.causeMobDamage(getThrower()), 100 + this.rand.nextInt(17));
		}
	}


}
