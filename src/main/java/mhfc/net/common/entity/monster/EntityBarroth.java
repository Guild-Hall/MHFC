package mhfc.net.common.entity.monster;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.model.util.RenderPassInformation;

import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.entity.boss.barroth.Death;
import mhfc.net.common.ai.entity.boss.barroth.Idle;
import mhfc.net.common.ai.entity.boss.barroth.Ram;
import mhfc.net.common.ai.entity.boss.barroth.RamRun;
import mhfc.net.common.ai.entity.boss.barroth.Roar;
import mhfc.net.common.ai.entity.boss.barroth.Stomp;
import mhfc.net.common.ai.entity.boss.barroth.Wander;
import mhfc.net.common.ai.manager.builder.ActionManagerBuilder;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.entity.type.EntityMHFCPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityBarroth extends EntityMHFCBase<EntityBarroth> {

	public int deathTick;
	public int rageLevel;

	public EntityBarroth(World WORLD) {
		super(WORLD);
		this.height = 4f;
		this.width = 5f;
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
	}

	@Override
	public IActionManager<EntityBarroth> constructActionManager() {
		ActionManagerBuilder<EntityBarroth> actionManager = new ActionManagerBuilder<>();
		actionManager.registerAction(setDeathAction(new Death()));
		actionManager.registerAction(new Wander());
		actionManager.registerAction(new Stomp());
		actionManager.registerAction(new RamRun());
		actionManager.registerAction(new Ram());
		actionManager.registerAction(new Idle());
		actionManager.registerAction(new Roar());
		return actionManager.build(this);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		//default 8112
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(healthbaseHP(14655D));
	}

	@Override
	public EntityMHFCPart[] getParts() {
		return null;
	}

	@Override
	public RenderPassInformation preRenderCallback(float scale, RenderPassInformation sub) {
		GL11.glScaled(4, 4.2, 4);
		return super.preRenderCallback(scale, sub);
	}

	@Override
	protected String getLivingSound() {

		return "mhfc:barroth.idle";
	}

	@Override
	public void entityInit() {
		super.entityInit();
		// if(this.isInWater())
		dataWatcher.addObject(16, Byte.valueOf((byte) 0));
		dataWatcher.addObject(17, Byte.valueOf((byte) 0));
	}

}
