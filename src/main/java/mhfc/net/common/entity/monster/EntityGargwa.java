package mhfc.net.common.entity.monster;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.model.util.RenderPassInformation;

import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.entity.nonboss.gargwa.Death;
import mhfc.net.common.ai.entity.nonboss.gargwa.Idle;
import mhfc.net.common.ai.entity.nonboss.gargwa.Sleep;
import mhfc.net.common.ai.entity.nonboss.gargwa.Wander;
import mhfc.net.common.ai.manager.builder.ActionManagerBuilder;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.entity.type.EntityMHFCPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityGargwa extends EntityMHFCBase<EntityGargwa> {

	public EntityGargwa(World world) {
		super(world);
		this.height = 2f;
		this.width = 2f;
	}

	@Override
	protected IActionManager<EntityGargwa> constructActionManager() {
		ActionManagerBuilder<EntityGargwa> actionManager = new ActionManagerBuilder<>();
		//	actionManager.registerAction(new GaguaPeck());
		actionManager.registerAction(new Idle());
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
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(healthbaseHP(300D));
	}

	@Override
	public RenderPassInformation preRenderCallback(float scale, RenderPassInformation sub) {
		GL11.glScaled(1.6, 1.6, 1.6);
		return super.preRenderCallback(scale, sub);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return MHFCSoundRegistry.getRegistry().gargwaIdle;
	}

}
