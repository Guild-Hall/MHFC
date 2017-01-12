package mhfc.net.common.entity.monster;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.model.util.RenderPassInformation;

import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.entity.boss.tigrex.Bite;
import mhfc.net.common.ai.entity.boss.tigrex.Death;
import mhfc.net.common.ai.entity.boss.tigrex.GroundHurl;
import mhfc.net.common.ai.entity.boss.tigrex.Idle;
import mhfc.net.common.ai.entity.boss.tigrex.Jump;
import mhfc.net.common.ai.entity.boss.tigrex.Roar;
import mhfc.net.common.ai.entity.boss.tigrex.Run;
import mhfc.net.common.ai.entity.boss.tigrex.Wander;
import mhfc.net.common.ai.entity.boss.tigrex.Whip;
import mhfc.net.common.ai.manager.builder.FollowUpManagerBuilder;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.entity.type.EntityMHFCPart;
import mhfc.net.common.item.materials.ItemTigrex.TigrexSubType;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityTigrex extends EntityMHFCBase<EntityTigrex> {

	public int rageLevel;

	public EntityTigrex(World par1World) {
		super(par1World);
		setSize(4.3f, 3.4f);
		stepHeight = 1.5f;
	}

	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		// TODO enable this when Popos are a thing again
		targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
	}

	@Override
	protected IActionManager<EntityTigrex> constructActionManager() {
		FollowUpManagerBuilder<EntityTigrex> manager = new FollowUpManagerBuilder<>();
		manager.registerAllowingAllActions(setDeathAction(new Death()));
		manager.registerAllowingAllActions(new Jump());
		manager.registerAllowingAllActions(new Run());
		manager.registerAllowingAllActions(new GroundHurl());
		manager.registerAllowingAllActions(new Bite());
		Roar tigrexRoar = new Roar();
		manager.registerAllowingAllActions(tigrexRoar);
		manager.registerAllowingAllActions(new Whip());
		
		// Living Actions 
		
		manager.registerAllowingAllActions(new Idle());
		manager.registerAllowingAllActions(new Wander());
		//	 Register roar to be the only allowed initial move on sight of an enemy
		List<IExecutableAction<? super EntityTigrex>> allowedFirstSight = new ArrayList<>();
		allowedFirstSight.add(tigrexRoar);
		
		
		return manager.build(this);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(healthbaseHP(20432D));
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
	public RenderPassInformation preRenderCallback(float subFrame, RenderPassInformation sub) {
		GL11.glScaled(2.3, 2.3, 2.3);
		return super.preRenderCallback(subFrame, sub);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return MHFCSoundRegistry.getRegistry().tigrexIdle;
	}

	@Override
	protected SoundEvent getHurtSound() {
		return MHFCSoundRegistry.getRegistry().tigrexIdle;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return MHFCSoundRegistry.getRegistry().tigrexDeath;
	}

	@Override
	public EntityMHFCPart[] getParts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block blockIn) {
		this.playSound(MHFCSoundRegistry.getRegistry().tigrexStep, 2.0F, 1.0F);
	}
}
