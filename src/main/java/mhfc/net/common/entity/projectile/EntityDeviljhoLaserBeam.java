package mhfc.net.common.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityDeviljhoLaserBeam extends Entity implements IProjectile {

	private static final DataParameter<Integer> caster = EntityDataManager
			.<Integer>createKey(EntityDeviljhoLaserBeam.class, DataSerializers.VARINT);

	public EntityDeviljhoLaserBeam(World worldIn) {
		super(worldIn);
		noClip = true;
		setSize(0, 0);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		float yaw = (float) Math.toRadians(-rotationYaw);
		float pitch = (float) Math.toRadians(-rotationPitch);
		float spread = 0.25f;
		float speed = 0.56f;
		for (int i = 0; i < 55; i++) {
			this.world.spawnParticle(
					EnumParticleTypes.LAVA,
					this.posX * spread,
					this.posY * spread,
					this.posZ * spread,
					3 * yaw,
					3 * pitch,
					3 * speed,
					50);

		}
	}

	@Override
	protected void entityInit() {
		getDataManager().register(caster, -1);
	}

	public int getCasterID() {
		return getDataManager().get(caster);
	}

	public void setCasterID(int id) {
		getDataManager().set(caster, id);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {

	}

	@Override
	public void setThrowableHeading(double x, double y, double z, float velocity, float inaccuracy) {
		float f2 = MathHelper.sqrt(x * x + y * y + z * z);
		x /= f2;
		y /= f2;
		z /= f2;
		x *= velocity;
		y *= velocity;
		z *= velocity;
		this.motionX = x;
		this.motionY = y;
		this.motionY = z;
		float f3 = MathHelper.sqrt(x * x + z * z);
		this.prevRotationYaw = (this.rotationYaw = (float) (Math.atan2(x, z) * 180.0D / 3.141592653589793D));
		this.prevRotationPitch = (this.rotationPitch = (float) (Math.atan2(y, f3) * 180.0D / 3.141592653589793D));
	}


}
