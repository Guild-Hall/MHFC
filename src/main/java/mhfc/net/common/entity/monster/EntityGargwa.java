package mhfc.net.common.entity.monster;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.model.util.RenderPassInformation;

import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.entity.nonboss.gargwa.Death;
import mhfc.net.common.ai.entity.nonboss.gargwa.Idle1;
import mhfc.net.common.ai.entity.nonboss.gargwa.Idle2;
import mhfc.net.common.ai.entity.nonboss.gargwa.Sleep;
import mhfc.net.common.ai.entity.nonboss.gargwa.Wander;
import mhfc.net.common.ai.manager.builder.ActionManagerBuilder;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.entity.type.EntityMHFCPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class EntityGargwa extends EntityMHFCBase<EntityGargwa> {

	public EntityGargwa(World world) {
		super(world);
		this.height = 2f;
		this.width = 2f;
//		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
	}
	

	@Override
	public IActionManager<EntityGargwa> constructActionManager() {
		ActionManagerBuilder<EntityGargwa> actionManager = new ActionManagerBuilder<>();
	//	actionManager.registerAction(new GaguaPeck());
		actionManager.registerAction(new Idle1());
		actionManager.registerAction(new Idle2());
		actionManager.registerAction(new Sleep());
		actionManager.registerAction(setDeathAction(new Death()));
		actionManager.registerAction(new Wander());
		return actionManager.build(this);
	}

	@Override
	public EntityMHFCPart[] getParts() {
		return null;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(healthbaseHP(300D));
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
	@Override
	protected String getLivingSound() {
		return "mhfc:gagua.idle";
	}

}
