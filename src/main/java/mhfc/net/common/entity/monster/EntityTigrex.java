package mhfc.net.common.entity.monster;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.model.mcanmmodel.data.RenderPassInformation;

import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.entity.tigrex.TigrexBite;
import mhfc.net.common.ai.entity.tigrex.TigrexGroundHurl;
import mhfc.net.common.ai.entity.tigrex.TigrexIdle;
import mhfc.net.common.ai.entity.tigrex.TigrexJump;
import mhfc.net.common.ai.entity.tigrex.TigrexRoar;
import mhfc.net.common.ai.entity.tigrex.TigrexRun;
import mhfc.net.common.ai.entity.tigrex.TigrexWander;
import mhfc.net.common.ai.entity.tigrex.TigrexWhip;
import mhfc.net.common.ai.general.TurnAttack;
import mhfc.net.common.ai.manager.builder.FollowUpManagerBuilder;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.entity.type.EntityMHFCPart;
import mhfc.net.common.item.materials.ItemTigrex.TigrexSubType;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityTigrex extends EntityMHFCBase<EntityTigrex> {

	public int deathTick;
	public int rageLevel;

	public EntityTigrex(World par1World) {
		super(par1World);
		height = 3.4f;
		width = 4.3f;
		stepHeight = 1f;
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		// TODO enable this when Popos are a thing again
		// targetTasks.addTask(1, new EntityAINearestAttackableTarget(this,
		// EntityPopo.class, 0, true));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
	}

	@Override
	public IActionManager<EntityTigrex> constructActionManager() {
		FollowUpManagerBuilder<EntityTigrex> manager = new FollowUpManagerBuilder<>();
		manager.registerAllowingAllActions(new TurnAttack(110, 180, 5f, 12f, 20));
		manager.registerAllowingAllActions(new TigrexJump());
		manager.registerAllowingAllActions(new TigrexRun());
		manager.registerAllowingAllActions(new TigrexGroundHurl());
		manager.registerAllowingAllActions(new TigrexBite());
		TigrexRoar tigrexRoar = new TigrexRoar();
		manager.registerAllowingAllActions(tigrexRoar);
		manager.registerAllowingAllActions(new TigrexIdle());
		// Register roar to be the only allowed initial move on sight of an enemy
		List<IExecutableAction<? super EntityTigrex>> allowedFirstSight = new ArrayList<>();
		allowedFirstSight.add(tigrexRoar);
		manager.registerActionWithFollowUps(new TigrexWander(), allowedFirstSight);
		manager.registerAllowingAllActions(new TigrexWhip());
		return manager.build(this);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getAttributeMap().getAttributeInstance(SharedMonsterAttributes.followRange).setBaseValue(128d);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.3D);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(healthbaseHP(9807D, 12000D, 24000D));
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(35D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, Byte.valueOf((byte) 0));
		dataWatcher.addObject(17, Byte.valueOf((byte) 0));
	}

	@Override
	protected void dropFewItems(boolean par1, int par2) {
		int var4;
		for (var4 = 0; var4 < 13; ++var4) {
			dropItemRand(SubTypedItem.fromSubItem(TigrexSubType.SCALE, 2));
		}
		for (var4 = 0; var4 < 8; ++var4) {
			dropItemRand(SubTypedItem.fromSubItem(TigrexSubType.SHELL, 1));
			dropItemRand(SubTypedItem.fromSubItem(TigrexSubType.FANG, 1));
			dropItemRand(SubTypedItem.fromSubItem(TigrexSubType.CLAW, 1));
		}
		for (var4 = 0; var4 < 1; ++var4) {
			dropItemRand(SubTypedItem.fromSubItem(TigrexSubType.TAIL, 2));
		}
		dropItemRand(SubTypedItem.fromSubItem(TigrexSubType.SKULLSHELL, 1));
	}

	@Override
	public RenderPassInformation preRenderCallback(float scale, RenderPassInformation sub) {
		GL11.glScaled(2.3, 2.3, 2.3);
		return super.preRenderCallback(scale, sub);

	}

	@Override
	protected String getLivingSound() {
		return "mhfc:tigrex.idle";
	}

	@Override
	protected String getHurtSound() {
		return null;
	}

	@Override
	protected String getDeathSound() {
		return null;
	}

	@Override
	public EntityMHFCPart[] getParts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_) {
		this.playSound("mhfc:tigrex.step", 1.0F, 1.0F);
	}
}
