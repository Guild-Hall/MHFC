package mhfc.net.common.entity.creature.incomplete;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.model.util.RenderPassInformation;

import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.entity.AIBite;
import mhfc.net.common.ai.entity.AIBreathe;
import mhfc.net.common.ai.entity.AIDeath;
import mhfc.net.common.ai.entity.AIIdle;
import mhfc.net.common.ai.entity.AIWander;
import mhfc.net.common.ai.entity.monsters.akuravashimu.Jump1;
import mhfc.net.common.ai.entity.monsters.akuravashimu.Move1;
import mhfc.net.common.ai.manager.builder.ActionManagerBuilder;
import mhfc.net.common.entity.CreatureAttributes;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class AkuraVashimu extends CreatureAttributes<AkuraVashimu> {
	public int deathTick;
	public int rageLevel;

	public AkuraVashimu(World world) {
		super(world);
		setSize(2.5f, 2.5f);
	}

	@Override
	protected IActionManager<AkuraVashimu> constructActionManager() {
		ActionManagerBuilder<AkuraVashimu>manager = new ActionManagerBuilder<>();
		manager.registerAction(new AIBreathe(this, "mhfc:models/akuravashimu/breath.mcanm", 60, 2f));
		manager.registerAction(new AIIdle(this, "mhfc:models/akuravashimu/look1.mcanm", 40, 0.5F));
		manager.registerAction(new AIWander<AkuraVashimu>(this,"mhfc:models/akuravashimu/walk1.mcanm",160,1,0.1F,0.2F,20,139,1,5));
		manager.registerAction(new AIBite(this, "mhfc:models/akuravashimu/damage1.mcanm", 70, 32, 80, 10, null, 12F, true, 32, 36));
		manager.registerAction(new Jump1());
		manager.registerAction(new Move1());
		manager.registerAction(new AIDeath(this, "mhfc:models/akuravashimu/death.mcanm", null));
		//Wander i added + 10 ticks to the animation length to make sure that there is allowance on loop.
	//	followUpManager.registerAction(new AIWander<AkuraVashimu>(this,"mhfc:models/akuravashimu/walk.mcanm",110,1,0.12F,0.7F,0,89,1,10));
		return manager.build(this);
	}

	@Override
	public MultiPartEntityPart[] getParts() {
		return null;
	}
	
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8113D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(20D);

	}
	
	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 0, true, true, null));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityVillager.class, 0, true, true, null));
		targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
	}
	
	@Override
	public RenderPassInformation preRenderCallback(float scale, RenderPassInformation sub) {
		GL11.glScaled(1.8,1.8, 1.8);
		return super.preRenderCallback(scale, sub);
	}
}
