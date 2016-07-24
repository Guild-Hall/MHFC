package mhfc.net.common.entity.monster;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.model.util.RenderPassInformation;

import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.entity.boss.kirin.Idle;
import mhfc.net.common.ai.manager.builder.ActionManagerBuilder;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.entity.type.EntityMHFCPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityKirin extends EntityMHFCBase<EntityKirin> {

	public EntityKirin(World world) {
		super(world);
		this.height = 3F;
		this.width = 3F;
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
	}

	@Override
	public IActionManager<EntityKirin> constructActionManager() {
		ActionManagerBuilder<EntityKirin> actionManager = new ActionManagerBuilder<>();
		actionManager.registerAction(new Idle());
		return actionManager.build(this);
	}

	@Override
	public EntityMHFCPart[] getParts() {
		return null;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		//default 13738
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(healthbaseHP(20D));
	}

	@Override
	public RenderPassInformation preRenderCallback(float scale, RenderPassInformation sub) {
		GL11.glScaled(1.5, 1.5, 1.5);
		return super.preRenderCallback(scale, sub);
	}

	@Override
	protected String getLivingSound() {

		return "mhfc:kirin.idle";
	}
	
	@Override
	public void entityInit() {
		super.entityInit();
		// if(this.isInWater())
		dataWatcher.addObject(16, Byte.valueOf((byte) 0));
		dataWatcher.addObject(17, Byte.valueOf((byte) 0));
	}

}
