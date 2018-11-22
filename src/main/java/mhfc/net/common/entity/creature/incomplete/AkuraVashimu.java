package mhfc.net.common.entity.creature.incomplete;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.model.util.RenderPassInformation;

import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.entity.AIBreathe;
import mhfc.net.common.ai.entity.AIWander;
import mhfc.net.common.ai.manager.builder.FollowUpManagerBuilder;
import mhfc.net.common.entity.CreatureAttributes;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class AkuraVashimu extends CreatureAttributes<AkuraVashimu> {
	public int deathTick;
	public int rageLevel;

	public AkuraVashimu(World world) {
		super(world);
		setSize(6.3f, 6.3f);
	}

	@Override
	protected IActionManager<AkuraVashimu> constructActionManager() {
		FollowUpManagerBuilder<AkuraVashimu>followUpManager = new FollowUpManagerBuilder<>();
		followUpManager.registerAction(new AIBreathe(this, "mhfc:models/akuravashimu/breath.mcanm", 40, 2f));
		//Wander i added + 10 ticks to the animation length to make sure that there is allowance on loop.
		followUpManager.registerAction(new AIWander<AkuraVashimu>(this,"mhfc:models/akuravashimu/walk.mcanm",110,1,0.12F,0.7F,0,89,1,10));
		return followUpManager.build(this);
	}

	@Override
	public MultiPartEntityPart[] getParts() {
		return null;
	}
	
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8113D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(60D);

	}
	
	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 0, true, true, null));
		targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
	}
	
	@Override
	public RenderPassInformation preRenderCallback(float scale, RenderPassInformation sub) {
		GL11.glScaled(1, 1, 1);
		return super.preRenderCallback(scale, sub);
	}
}
