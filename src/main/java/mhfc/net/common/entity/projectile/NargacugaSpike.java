package mhfc.net.common.entity.projectile;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class NargacugaSpike extends EntityThrowable {

	IDamageCalculator damageHelper = AIUtils.defaultDamageCalc(15, 500, 9999999f);

	public NargacugaSpike(World world) {
		super(world);
		setSize(1.2f, 1.2f);
	}

	public NargacugaSpike(World world, EntityLivingBase entityLiving) {
		super(world, entityLiving);
		setSize(1.2f, 1.2f);
	}

	@Override
	protected float getGravityVelocity() {
		return 0.01f;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (this.ticksExisted == 30) {
			setDead();
		}
	}

	@Override
	protected void onImpact(MovingObjectPosition impact) {
		setDead();
		if (this.worldObj.isRemote)
			return;
		if (impact.entityHit == null)
			return;
		if (!(impact.entityHit instanceof EntityLivingBase))
			return;
		damageHelper.accept(impact.entityHit);
	}

}
