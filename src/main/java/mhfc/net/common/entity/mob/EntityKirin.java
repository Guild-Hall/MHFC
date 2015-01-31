package mhfc.net.common.entity.mob;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCRegItem;
import mhfc.net.common.entity.type.EntityWyvernHostile;
import mhfc.net.common.implement.iMHFC;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.world.World;

public class EntityKirin extends EntityWyvernHostile implements iMHFC {

	public int currentAttackID;
	public int animTick;
	public int deathTick;

	public EntityKirin(World par1World) {
		super(par1World);
		width = 3f;
		height = 3f;
		animTick = 0;
		getArmor = 15;
		isImmuneToFire = true;
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));

	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		applyMonsterAttributes(2.5D, 1700D, 2600D, 3500D, 0.32D, 25D);
	}

	@Override
	protected String getLivingSound() {
		return MHFCReference.mob_kirin_sound_say;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (currentAttackID != 0) {
			animTick++;
		}
		worldObj.spawnParticle("cloud", this.posX + this.rand.nextFloat()
				* 2.0F - 1.0D, this.posY + this.rand.nextFloat() * 3.0F + 1.0D,
				this.posZ + this.rand.nextFloat() * 2.0F - 1.0D, 0.0D, 0.0D,
				0.0D);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(16, Byte.valueOf((byte) 0));
	}

	@Override
	public void setAnimID(int id) {
		currentAttackID = id;
	}

	@Override
	public void setAnimTick(int tick) {
		animTick = tick;
	}

	@Override
	public int getAnimID() {
		return currentAttackID;
	}

	@Override
	public int getAnimTick() {
		return animTick;
	}

	public void attackEntityAtDistSq(EntityLivingBase living, float f) {
		if (!worldObj.isRemote) {
			if (currentAttackID == 0 && onGround && rand.nextInt(20) == 0) {
				sendAttackPacket(1);
			}
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		if (!worldObj.isRemote) {
			if (currentAttackID == 0 && onGround) {
				sendAttackPacket(1);
			}
		}
		return true;
	}

	public void sendAttackPacket(int id) {
		if (MHFCMain.isClient())
			return;
		this.currentAttackID = id;
		// MHFCMain.packetPipeline.sendToAll(new PacketAIKirin((byte) id,
		// this));
	}

	@Override
	protected void dropFewItems(boolean par1, int par2) {
		int var4;
		for (var4 = 0; var4 < 16; ++var4) {
			dropItemRand(MHFCRegItem.mhfcitemkirinmane, 2);
		}
		for (var4 = 0; var4 < 9; ++var4) {
			dropItemRand(MHFCRegItem.mhfcitemkiringem, 2);
		}
		for (var4 = 0; var4 < 5; ++var4) {
			dropItemRand(MHFCRegItem.mhfcitemkirinthundertail, 1);
			dropItemRand(MHFCRegItem.mhfcitemlightcrystal, 1);
		}
		for (var4 = 0; var4 < 2; ++var4) {
			dropItemRand(MHFCRegItem.mhfcitempurecrystal, 1);
			dropItemRand(MHFCRegItem.mhfcitemplatinummane, 1);
		}

	}

}
