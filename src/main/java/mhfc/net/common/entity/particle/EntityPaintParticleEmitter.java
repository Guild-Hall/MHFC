package mhfc.net.common.entity.particle;

import io.netty.buffer.ByteBuf;
import mhfc.net.MHFCMain;
import mhfc.net.common.entity.type.EntityParticleEmitter;
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
			x = this.victim.posX + this.victim.width / 2;
			y = this.victim.posY + this.victim.height / 2;
			z = this.victim.posZ + this.victim.width / 2;
		}

		this.setPosition(x, y, z);
		this.color = color == null ? ItemColor.WHITE : color;
	}

	@Override
	public void onUpdate() {
		if (this.worldObj.isRemote && this.ticksExisted > 0 && this.ticksExisted % 10 == 0) {
			MHFCMain.getSidedProxy().spawnParticle(EnumParticleType.PAINT, this);
		}

		if (this.ticksExisted > maxLife || this.ticksExisted > ABSOLUTE_MAX) {
			this.setDead();
			//this.attackEntityFrom(DamageSource.generic, this.maxLife);
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		nbt.setInteger("color", color.getMetadata());
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
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
