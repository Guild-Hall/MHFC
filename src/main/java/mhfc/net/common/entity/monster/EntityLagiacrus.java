package mhfc.net.common.entity.monster;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.model.util.RenderPassInformation;

import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.entity.deviljho.DeviljhoDying;
import mhfc.net.common.ai.entity.lagiacrus.LagiacrusBite;
import mhfc.net.common.ai.entity.lagiacrus.LagiacrusDying;
import mhfc.net.common.ai.entity.lagiacrus.LagiacrusRoar;
import mhfc.net.common.ai.entity.lagiacrus.LagiacrusWander;
import mhfc.net.common.ai.entity.tigrex.TigrexDying;
import mhfc.net.common.ai.manager.builder.ActionManagerBuilder;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.entity.type.EntityMHFCPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityLagiacrus extends EntityMHFCBase<EntityLagiacrus> {

	private LagiacrusDying deathAI;
	
	public EntityLagiacrus(World world) {
		super(world);
		this.height = 12f;
		this.width = 12f;
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
	}

	@Override
	public IActionManager<EntityLagiacrus> constructActionManager() {
		ActionManagerBuilder<EntityLagiacrus> actionManager = new ActionManagerBuilder<>();
		actionManager.registerAction(new LagiacrusWander());
		actionManager.registerAction(new LagiacrusRoar());
		actionManager.registerAction(new LagiacrusBite());
		actionManager.registerAction(deathAI = new LagiacrusDying());
		return actionManager.build(this);
	}
	
	@Override
	protected void onDeath() {
		getActionManager().switchToAction(deathAI);
	}

	@Override
	public EntityMHFCPart[] getParts() {
		return null;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.followRange).setBaseValue(128d);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.3D);
		//default 13738
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(healthbaseHP(20D, 1000D, 1400D));
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(35D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.32D);
	}

	public RenderPassInformation preRenderCallback(float scale, RenderPassInformation sub) {
		GL11.glScaled(5.1, 5.1, 5.1);
		return super.preRenderCallback(scale, sub);
	}

	@Override
	public void entityInit() {
		super.entityInit();
		// if(this.isInWater())
		dataWatcher.addObject(16, Byte.valueOf((byte) 0));
		dataWatcher.addObject(17, Byte.valueOf((byte) 0));
	}

}
