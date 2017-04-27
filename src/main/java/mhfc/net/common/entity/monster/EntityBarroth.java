package mhfc.net.common.entity.monster;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.model.util.RenderPassInformation;

import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.entity.boss.barroth.Death;
import mhfc.net.common.ai.entity.boss.barroth.HeadSlam;
import mhfc.net.common.ai.entity.boss.barroth.Idle;
import mhfc.net.common.ai.entity.boss.barroth.Ram;
import mhfc.net.common.ai.entity.boss.barroth.RamRun;
import mhfc.net.common.ai.entity.boss.barroth.Roar;
import mhfc.net.common.ai.entity.boss.barroth.Stomp;
import mhfc.net.common.ai.entity.boss.barroth.Wander;
import mhfc.net.common.ai.manager.builder.FollowUpManagerBuilder;
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
		
		
		FollowUpManagerBuilder<EntityBarroth>	followUpManager = new FollowUpManagerBuilder<>();
		followUpManager.registerAction(setDeathAction(new Death()));
		followUpManager.registerAction(new Wander());
		followUpManager.registerAction(new Stomp());
		followUpManager.registerAction(new HeadSlam());
		followUpManager.registerAllowingAllActions(new RamRun());
		followUpManager.registerAllowingAllActions(new Ram());
		followUpManager.registerAllowingAllActions(new Idle());
		
		
		
		Roar roar = new Roar();
		followUpManager.registerAllowingAllActions(roar);
		
		List<IExecutableAction<? super EntityBarroth>> allowedFirstSight = new ArrayList<>();
		allowedFirstSight.add(roar);
		
		
		return followUpManager.build(this);
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
