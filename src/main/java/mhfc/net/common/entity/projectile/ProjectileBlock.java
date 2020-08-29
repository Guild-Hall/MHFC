package mhfc.net.common.entity.projectile;

import mhfc.net.common.entity.creature.Deviljho;
import mhfc.net.common.entity.creature.Tigrex;
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

import java.util.List;

public class ProjectileBlock extends EntityThrowable {
	private final EntityFallingBlock proxy;

	public ProjectileBlock(World par) {
		super(par);
		setSize(1.0F, 1.0F);
		proxy = createProxy();
	}

	public ProjectileBlock(World world, EntityLivingBase living) {
		super(world, living);
		proxy = createProxy();
		this.posY -= living.getEyeHeight();
		Vec3d look = living.getLookVec();
		this.posX += look.x * 2;
		this.posZ += look.z * 2;
		rotationYaw = living.rotationYaw;
	}

	public ProjectileBlock(World par, Tigrex e) {
		this(par, (EntityLivingBase) e);
		setSize(1.3F, 1.3F);
		this.thrower = e;
	}

	public ProjectileBlock(World par, Deviljho e) {
		this(par, (EntityLivingBase) e);
		setSize(1.4F, 1.4F);
		this.thrower = e;
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
		return 0.14f;
	}

	@Override
	protected void onImpact(RayTraceResult mop) {
		List<Entity> list = this.world
				.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(8.5D, 6.0D, 8.5D));
		list.remove(getThrower());
		for (Entity entity : list) {
			if (getDistanceSq(entity) > 6.25D) {
				continue;
			}
			entity.attackEntityFrom(DamageSource.causeMobDamage(this.thrower), 80F + this.rand.nextInt(17));
		}
	}
	
	public float setProjectileGravityRange(float gravityVelocity) {
		return gravityVelocity;
	}
	


}
