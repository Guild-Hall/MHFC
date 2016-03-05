package mhfc.net.common.entity.monster;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.model.mcanmmodel.data.RenderPassInformation;

import mhfc.net.common.ai.AIActionManager;
import mhfc.net.common.ai.entity.delex.DelexIdle;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.entity.type.EntityMHFCPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityDelex extends EntityMHFCBase<EntityDelex> {

	public EntityDelex(World world) {
		super(world);
		this.height = 1f;
		this.width = 2f;
		AIActionManager<EntityDelex> attackManager = getAIActionManager();
		attackManager.registerAttack(new DelexIdle());
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
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.3D);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(healthbaseHP(416D, 800D, 1200D));
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(35D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.32D);
	}

	public RenderPassInformation preRenderCallback(float scale, RenderPassInformation sub) {
		GL11.glScaled(1.4, 1.4, 1.4);
		return super.preRenderCallback(scale, sub);

	}

	@Override
	public void entityInit() {
		super.entityInit();
		// if(this.isInWater())
		dataWatcher.addObject(16, Byte.valueOf((byte) 0));
		dataWatcher.addObject(17, Byte.valueOf((byte) 0));
	}

}
