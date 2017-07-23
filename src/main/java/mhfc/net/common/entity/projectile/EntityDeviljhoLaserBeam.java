package mhfc.net.common.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityDeviljhoLaserBeam extends Entity {

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



}
