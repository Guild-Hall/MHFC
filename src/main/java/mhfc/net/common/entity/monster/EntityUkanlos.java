package mhfc.net.common.entity.monster;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.model.mcanmmodel.data.RenderPassInformation;

import mhfc.net.common.ai.AIActionManager;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.entity.type.EntityMHFCPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityUkanlos extends EntityMHFCBase<EntityUkanlos> {

	public EntityUkanlos(World world) {
		super(world);
		this.height = 9f;
		this.width = 9f;
		AIActionManager<EntityUkanlos> attackManager = getAIActionManager();
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
	}
	
	

	@Override
	public EntityMHFCPart[] getParts() {
		return null;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.followRange).setBaseValue(128d);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance)
			.setBaseValue(1.3D);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(
			healthbaseHP(95000D, 140000D, 175000D));
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(
			35D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(
			0.32D);
	}
	
	 public RenderPassInformation preRenderCallback(float scale, RenderPassInformation sub){
		 GL11.glScaled(5,5, 5);
		 return super.preRenderCallback(scale, sub);
		 
	 }
	 
		@Override
		public void entityInit() {
			super.entityInit();
			//if(this.isInWater())
			dataWatcher.addObject(16, Byte.valueOf((byte) 0));
			dataWatcher.addObject(17, Byte.valueOf((byte) 0));
		}
	
}
