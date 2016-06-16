package mhfc.net.common.entity.monster;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.model.util.RenderPassInformation;

import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.entity.greatjaggi.GJaggiBite;
import mhfc.net.common.ai.entity.greatjaggi.GJaggiIdle;
import mhfc.net.common.ai.entity.greatjaggi.GJaggiRoar;
import mhfc.net.common.ai.entity.greatjaggi.GJaggiRun;
import mhfc.net.common.ai.entity.greatjaggi.GJaggiWander;
import mhfc.net.common.ai.entity.greatjaggi.GJaggiWhip;
import mhfc.net.common.ai.manager.builder.ActionManagerBuilder;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.entity.type.EntityMHFCPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.world.World;

public class EntityGreatJaggi extends EntityMHFCBase<EntityGreatJaggi> {

	public int deathTick;
	public int rageLevel;

	public EntityGreatJaggi(World world) {
		super(world);

		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityCow.class, 0, true));
	}

	@Override
	public IActionManager<EntityGreatJaggi> constructActionManager() {
		ActionManagerBuilder<EntityGreatJaggi> actionManager = new ActionManagerBuilder<>();
		actionManager.registerAction(new GJaggiBite());
		actionManager.registerAction(new GJaggiIdle());
		actionManager.registerAction(new GJaggiRoar());
		actionManager.registerAction(new GJaggiRun());
		actionManager.registerAction(new GJaggiWhip());
		actionManager.registerAction(new GJaggiWander());
		return actionManager.build(this);
	}

	@Override
	public EntityMHFCPart[] getParts() {
		return null;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getAttributeMap().getAttributeInstance(SharedMonsterAttributes.followRange).setBaseValue(128d);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(healthbaseHP(2653D, 5200D, 10400D));
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(35D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.4D);
	}

	@Override
	public RenderPassInformation preRenderCallback(float scale, RenderPassInformation sub) {
		GL11.glScaled(1.6, 1.6, 1.6);
		return super.preRenderCallback(scale, sub);
	}

}
