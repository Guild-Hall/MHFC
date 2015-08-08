package mhfc.net.common.entity.mob;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.model.mcanmmodel.data.RenderPassInformation;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.entity.type.EntityMHFCPart;

public class EntityNargacuga extends EntityMHFCBase<EntityNargacuga> {

	public EntityNargacuga(World world) {
		super(world);
		setSize(4,5);
		
		
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this,
				EntityPlayer.class, 0, true));
	}
	
	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getAttributeMap().getAttributeInstance(
			SharedMonsterAttributes.followRange).setBaseValue(128d);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance)
			.setBaseValue(1.3D);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(
			healthbaseHP(9000D, 13888D, 18465D));
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(
			35D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(
			0.43D);
	}

	@Override
	public EntityMHFCPart[] getParts() {
		return null;
	}
	
	 public RenderPassInformation preRenderCallback(float scale, RenderPassInformation sub){
		 GL11.glScaled(2,2, 2);
		 return super.preRenderCallback(scale, sub);
		 
	 }

}
