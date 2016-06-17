package mhfc.net.common.entity.particle;

import io.netty.buffer.ByteBuf;
import mhfc.net.common.entity.type.EntityParticleEmitter;
import mhfc.net.common.helper.MHFCParticleHelper;
import mhfc.net.common.item.ItemColor;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityPaintParticleEmitter extends EntityParticleEmitter {

	public ItemColor color;
	private Entity victim;

	public EntityPaintParticleEmitter(World worldIn) {
		this(worldIn, DurationType.LONG, ItemColor.PINK, null);
	}

	public EntityPaintParticleEmitter(World worldIn, DurationType duration, ItemColor color, Entity affected) {
		super(worldIn);
		this.maxLife = duration.ticks;
		this.victim = affected;

		double x, y, z;

		if (this.victim == null) {
			x = 0;
			y = 0;
			z = 0;
		} else {
			x = this.victim.posX;
			y = this.victim.posY;
			z = this.victim.posZ;
		}

		this.setPosition(x, y, z);
		this.color = color;
	}

	@Override
	public void onUpdate() {
		if (this.ticksExisted > 0 && this.ticksExisted % 5 == 0 && worldObj.isRemote) {
			EntityPaintFX particle = new EntityPaintFX(worldObj, color, this.posX, this.posY, this.posZ);

			MHFCParticleHelper.spawnParticleFromEntity(particle);
		}

		if (this.ticksExisted > maxLife || this.teleportDirection > ABSOLUTE_MAX) {
			this.setDead();
		}
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		nbt.setInteger("color", color.getMetadata());
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		color = ItemColor.byMetadata(nbt.getInteger("color"));
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeInt(color.getMetadata());
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		color = ItemColor.byMetadata(additionalData.readInt());
	}

	@Override
	protected void entityInit() {

	}

}
