package mhfc.net.common.entity.mob;

import mhfc.net.common.ai.AIAttackManager;
import mhfc.net.common.ai.IExecutableAttack;
import mhfc.net.common.ai.IMangedAttacks;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.entity.type.EntityWyvernHostile;
import mhfc.net.common.implement.iMHFC;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.world.World;

import com.github.worldsender.mcanm.client.model.mcanmmodel.data.RenderPassInformation;
import com.github.worldsender.mcanm.client.renderer.IAnimatedObject;

public class EntityRathalos extends EntityWyvernHostile
		implements
			IAnimatedObject,
			IMangedAttacks<EntityRathalos>,
			iMHFC {

	public int currentAttackID;
	public int animTick;
	public int deathTick;
	public int rageLevel;

	public EntityRathalos(World par1World) {
		super(par1World);
		width = 4F;
		height = 5F;
		isImmuneToFire = true;
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this,
				EntityAnimal.class, 0, true));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this,
				EntityMob.class, 0, true));
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (currentAttackID != 0)
			animTick++;
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
	@Override
	protected String getLivingSound() {
		return MHFCReference.mob_rathalos_sound_say;
	}
	@Override
	protected String getHurtSound() {
		return MHFCReference.mob_rathalos_sound_hurt;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		applyMonsterAttributes(1.3D, 4600D, 6100D, 7200D, 35D, 0.3D);
	}

	public void sendAttackPacket(int id) {
		if (this.worldObj.isRemote) // !isServerWorld(), actually
			return;
		currentAttackID = id;
		// MHFCMain.packetPipeline
		// .sendToAll(new PacketAIRathalos((byte) id, this));
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		if (!worldObj.isRemote) {
			if (currentAttackID == 0) {
				sendAttackPacket(1);
			}
		}
		return true;
	}

	@Override
	protected void dropFewItems(boolean par1, int par2) {
		int var4;
		for (var4 = 0; var4 < 13; ++var4) {
			dropItemRand(MHFCItemRegistry.mhfcitemrathalosshell, 2);
		}
		for (var4 = 0; var4 < 8; ++var4) {
			dropItemRand(MHFCItemRegistry.mhfcitemflamesac, 2);
			dropItemRand(MHFCItemRegistry.mhfcitemrathaloswebbing, 2);
			dropItemRand(MHFCItemRegistry.mhfcitemrathaloswing, 1);
		}
		for (var4 = 0; var4 < 1; ++var4) {
			dropItemRand(MHFCItemRegistry.mhfcitemwyvernmarrow, 1);
		}
		dropItemRand(MHFCItemRegistry.mhfcitemrathalosplate, 1);

	}

	@Override
	public void onAttackStart(IExecutableAttack<EntityRathalos> newAttack) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAttackEnd(IExecutableAttack<EntityRathalos> oldAttack) {
		// TODO Auto-generated method stub

	}

	@Override
	public AIAttackManager<EntityRathalos> getAttackManager() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RenderPassInformation preRenderCallback(float subFrame,
			RenderPassInformation info) {
		// TODO Auto-generated method stub
		return info;
	}

}
