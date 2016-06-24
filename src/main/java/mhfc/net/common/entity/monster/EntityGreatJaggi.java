package mhfc.net.common.entity.monster;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.model.util.RenderPassInformation;

import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.entity.boss.greatjaggi.GJaggiBite;
import mhfc.net.common.ai.entity.boss.greatjaggi.GJaggiIdle;
import mhfc.net.common.ai.entity.boss.greatjaggi.GJaggiRoar;
import mhfc.net.common.ai.entity.boss.greatjaggi.GJaggiRun;
import mhfc.net.common.ai.entity.boss.greatjaggi.GJaggiWander;
import mhfc.net.common.ai.entity.boss.greatjaggi.GJaggiWhip;
import mhfc.net.common.ai.manager.builder.ActionManagerBuilder;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.entity.type.EntityMHFCPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityGreatJaggi extends EntityMHFCBase<EntityGreatJaggi> {

	public int deathTick;
	public int rageLevel;

	public EntityGreatJaggi(World world) {
		super(world);

		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityAnimal.class, 0, true));
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
