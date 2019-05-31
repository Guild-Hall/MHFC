package mhfc.net.common.entity.creature;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.model.util.RenderPassInformation;

import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.entity.AIBreathe;
import mhfc.net.common.ai.entity.AIWander;
import mhfc.net.common.ai.entity.monsters.kirin.HeadButt;
import mhfc.net.common.ai.entity.monsters.kirin.LightningStrike;
import mhfc.net.common.ai.manager.builder.ActionManagerBuilder;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.CreatureAttributes;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class Kirin extends CreatureAttributes<Kirin> {

	public Kirin(World world) {
		super(world);
		setSize(3f, 3f);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH)
				.setBaseValue(healthbaseHP(10D));
		getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(21D);
	}

	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		targetTasks.addTask(6, new EntityAIHurtByTarget(this, true));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this,
				EntityPlayer.class, 0, true, true, null));
	}

	@Override
	protected IActionManager<Kirin> constructActionManager() {
		ActionManagerBuilder<Kirin> actionManager = new ActionManagerBuilder<>();
		actionManager.registerAction(new HeadButt());
		actionManager.registerAction(new LightningStrike());
		actionManager.registerAction(new AIBreathe(this,
				"mhfc:models/Kirin/kirinbreathe.mcanm", 160, 5F));
		actionManager.registerAction(
				new AIWander<Kirin>(this, "mhfc:models/Kirin/kirinwalk.mcanm",
						90, 10F, 0.07F, 0.5F, 0, 72, 1, 25));
		return actionManager.build(this);
	}

	@Override
	public MultiPartEntityPart[] getParts() {
		return null;
	}

	@Override
	public RenderPassInformation preRenderCallback(float scale,
			RenderPassInformation sub) {
		GL11.glScaled(2.0, 2.0, 3.3);
		return super.preRenderCallback(scale, sub);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return MHFCSoundRegistry.getRegistry().kirinIdle;
	}

}
