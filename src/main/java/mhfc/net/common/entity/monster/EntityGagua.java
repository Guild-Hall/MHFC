package mhfc.net.common.entity.monster;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.model.util.RenderPassInformation;

import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.entity.nonboss.gagua.GaguaDeath;
import mhfc.net.common.ai.entity.nonboss.gagua.GaguaIdle;
import mhfc.net.common.ai.entity.nonboss.gagua.GaguaIdleLook;
import mhfc.net.common.ai.entity.nonboss.gagua.GaguaPeck;
import mhfc.net.common.ai.entity.nonboss.gagua.GaguaSleep;
import mhfc.net.common.ai.entity.nonboss.gagua.GaguaWander;
import mhfc.net.common.ai.manager.builder.ActionManagerBuilder;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.entity.type.EntityMHFCPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.world.World;

public class EntityGagua extends EntityMHFCBase<EntityGagua> {

	public EntityGagua(World world) {
		super(world);
		this.height = 2f;
		this.width = 2f;
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
	}

	@Override
	public IActionManager<EntityGagua> constructActionManager() {
		ActionManagerBuilder<EntityGagua> actionManager = new ActionManagerBuilder<>();
	//	actionManager.registerAction(new GaguaPeck());
		actionManager.registerAction(new GaguaIdle());
		actionManager.registerAction(new GaguaIdleLook());
		actionManager.registerAction(new GaguaSleep());
		actionManager.registerAction(setDeathAction(new GaguaDeath()));
		actionManager.registerAction(new GaguaWander());
		return actionManager.build(this);
	}

	@Override
	public EntityMHFCPart[] getParts() {
		return null;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getAttributeMap().getAttributeInstance(SharedMonsterAttributes.followRange).setBaseValue(128d);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.3D);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(healthbaseHP(150D, 800D, 1200D));
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(35D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
	}

	@Override
	public RenderPassInformation preRenderCallback(float scale, RenderPassInformation sub) {
		GL11.glScaled(1.6, 1.6, 1.6);
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
