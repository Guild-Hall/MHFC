package mhfc.net.common.entity.monster;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.model.util.RenderPassInformation;

import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.entity.boss.greatjaggi.Bite;
import mhfc.net.common.ai.entity.boss.greatjaggi.Death;
import mhfc.net.common.ai.entity.boss.greatjaggi.Idle;
import mhfc.net.common.ai.entity.boss.greatjaggi.Roar;
import mhfc.net.common.ai.entity.boss.greatjaggi.Run;
import mhfc.net.common.ai.entity.boss.greatjaggi.Wander;
import mhfc.net.common.ai.entity.boss.greatjaggi.Whip;
import mhfc.net.common.ai.manager.builder.ActionManagerBuilder;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.entity.type.EntityMHFCPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityGreatJaggi extends EntityMHFCBase<EntityGreatJaggi> {

	public int deathTick;
	public int rageLevel;

	public EntityGreatJaggi(World world) {
		super(world);
		setSize(2F,2F);
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
	}

	@Override
	public IActionManager<EntityGreatJaggi> constructActionManager() {
		ActionManagerBuilder<EntityGreatJaggi> actionManager = new ActionManagerBuilder<>();
		actionManager.registerAction(new Bite());
		actionManager.registerAction(new Idle());
		actionManager.registerAction(new Roar());
		actionManager.registerAction(new Run());
		actionManager.registerAction(new Whip());
		actionManager.registerAction(new Wander());
		
		actionManager.registerAction(setDeathAction(new Death()));
		return actionManager.build(this);
	}

	@Override
	public EntityMHFCPart[] getParts() {
		return null;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		// default 2653
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(healthbaseHP(8553D));
	}

	@Override
	public RenderPassInformation preRenderCallback(float scale, RenderPassInformation sub) {
		GL11.glScaled(1.6, 1.6, 1.6);
		return super.preRenderCallback(scale, sub);
	}

}
