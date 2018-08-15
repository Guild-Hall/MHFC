package mhfc.net.common.entity.monster.wip;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.model.util.RenderPassInformation;

import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.entity.AIBite;
import mhfc.net.common.ai.entity.AIBreathe;
import mhfc.net.common.ai.entity.AIWander;
import mhfc.net.common.ai.manager.builder.ActionManagerBuilder;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.entity.type.EntityMHFCPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityKirin extends EntityMHFCBase<EntityKirin> {

	public EntityKirin(World world) {
		super(world);
		setSize(3f, 3f);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(healthbaseHP(23111D));
		getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(21D);
	}

	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 0, true, true, null));
	}

	@Override
	protected IActionManager<EntityKirin> constructActionManager() {
		ActionManagerBuilder<EntityKirin> actionManager = new ActionManagerBuilder<>();
		actionManager.registerAction(new AIBite(this, "mhfc:models/Kirin/kirinheadbutt.mcanm", 35, 21, 5F, 10, MHFCSoundRegistry.getRegistry().kirinIdle, 10F, true, 19, 26));
		actionManager.registerAction(new AIBreathe(this, "mhfc:models/Kirin/kirinbreathe.mcanm", 160, 5F));
		actionManager.registerAction(new AIWander<EntityKirin>(this, "mhfc:models/Kirin/kirinwalk.mcanm", 90, 10F, 0.07F, 0.5F, 0, 72, 1, 25));
		return actionManager.build(this);
	}

	@Override
	public EntityMHFCPart[] getParts() {
		return null;
	}



	@Override
	public RenderPassInformation preRenderCallback(float scale, RenderPassInformation sub) {
		GL11.glScaled(2.0, 2.0, 3.3);
		return super.preRenderCallback(scale, sub);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return MHFCSoundRegistry.getRegistry().kirinIdle;
	}

}
