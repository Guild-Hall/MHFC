package mhfc.net.common.entity.monster;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.model.util.RenderPassInformation;

import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.entity.boss.lagiacrus.LagiacrusBite;
import mhfc.net.common.ai.entity.boss.lagiacrus.LagiacrusDying;
import mhfc.net.common.ai.entity.boss.lagiacrus.LagiacrusIdle;
import mhfc.net.common.ai.entity.boss.lagiacrus.LagiacrusRoar;
import mhfc.net.common.ai.entity.boss.lagiacrus.LagiacrusWander;
import mhfc.net.common.ai.manager.builder.ActionManagerBuilder;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.entity.type.EntityMHFCPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityLagiacrus extends EntityMHFCBase<EntityLagiacrus> {

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
		actionManager.registerAction(new LagiacrusIdle());
		actionManager.registerAction(setDeathAction(new LagiacrusDying()));
		return actionManager.build(this);
	}

	@Override
	public EntityMHFCPart[] getParts() {
		return null;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		//default 13738
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(healthbaseHP(13738D, 1000D, 1400D));
	}

	@Override
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
	
	@Override
	protected String getLivingSound() {
		return "mhfc:lagiacrus.idle";
	}

}
