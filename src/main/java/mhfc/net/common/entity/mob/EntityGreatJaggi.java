package mhfc.net.common.entity.mob;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.model.mcanmmodel.data.RenderPassInformation;

import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.entity.type.EntityMHFCPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.world.World;

public class EntityGreatJaggi extends EntityMHFCBase<EntityGreatJaggi> {
	
	public int deathTick;
	public int rageLevel;

	public EntityGreatJaggi(World world) {
		super(world);
		
		
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this,
				EntityCow.class, 0, true));
	}

	@Override
	public EntityMHFCPart[] getParts() {
		return null;
	}
	
	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getAttributeMap().getAttributeInstance(
			SharedMonsterAttributes.followRange).setBaseValue(128d);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance)
			.setBaseValue(1.0D);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(
			healthbaseHP(1872D, 2600D, 4122D));
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(
			35D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(
			0.4D);
	}
	
	 public RenderPassInformation preRenderCallback(float scale,
			 RenderPassInformation sub){
		 GL11.glScaled(1.6, 1.6, 1.6);
		 return super.preRenderCallback(scale, sub);
				 
	 }

}
