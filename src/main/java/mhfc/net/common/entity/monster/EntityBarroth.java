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
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.entity.type.EntityMHFCPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityBarroth extends EntityMHFCBase<EntityBarroth> {

	public int deathTick;
	public int rageLevel;

	public EntityBarroth(World world) {
		super(world);
		setSize(5f, 4f);
	}

	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 0, true, true, null));
	}

	@Override
	protected IActionManager<EntityBarroth> constructActionManager() {
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
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		//default 8112
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(healthbaseHP(14655D));
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
	protected SoundEvent getAmbientSound() {
		return MHFCSoundRegistry.getRegistry().barrothIdle;
	}

}
