package mhfc.net.common.entity.monster;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.model.util.RenderPassInformation;

import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.entity.AIAngleWhip;
import mhfc.net.common.ai.entity.AIBite;
import mhfc.net.common.ai.entity.AIBreathe;
import mhfc.net.common.ai.entity.AIDeath;
import mhfc.net.common.ai.entity.AIIdle;
import mhfc.net.common.ai.entity.AIWander;
import mhfc.net.common.ai.entity.monsters.tigrex.BackOff;
import mhfc.net.common.ai.entity.monsters.tigrex.Charge;
import mhfc.net.common.ai.entity.monsters.tigrex.GroundHurl;
import mhfc.net.common.ai.entity.monsters.tigrex.Jump;
import mhfc.net.common.ai.entity.monsters.tigrex.Roar;
import mhfc.net.common.ai.manager.builder.ActionManagerBuilder;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.entity.type.EntityMHFCPart;
import mhfc.net.common.item.materials.ItemMaterial.MaterialSubType;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.passive.EntityVillager;
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
		targetTasks.addTask(6, new EntityAIHurtByTarget(this, true));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityVillager.class, true));
		targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));

	}

	@Override
	protected IActionManager<EntityTigrex> constructActionManager() {
		ActionManagerBuilder<EntityTigrex> manager = new ActionManagerBuilder<>();
		manager.registerAction(
				setDeathAction(
						new AIDeath(
								this,
								"mhfc:models/Tigrex/dying.mcanm",
								MHFCSoundRegistry.getRegistry().tigrexDeath)));

		manager.registerAction(new Charge());
		manager.registerAction(
				new AIBite(
						this,
						"mhfc:models/Tigrex/bite.mcanm",
						55,
						12,
						70,
						10F,
						MHFCSoundRegistry.getRegistry().tigrexBite));
		manager.registerAction(new AIBreathe(this, "mhfc:models/Tigrex/breathe.mcanm", 60, 5F));
		manager.registerAction(
				new AIAngleWhip<>(
						"mhfc:models/Tigrex/clawswipe.mcanm",
						41,
						5,
						71,
						10,
						MHFCSoundRegistry.getRegistry().tigrexTailWhip,
						7,
						6,
						2F,
						120,
						10));
		manager.registerAction(
				new AIAngleWhip<>(
						"mhfc:models/Tigrex/tailswipe.mcanm",
						60,
						12,
						85,
						10F,
						MHFCSoundRegistry.getRegistry().tigrexTailWhip,
						9,
						5,
						1,
						180,
						10));
		manager.registerAction(new Jump());
		manager.registerAction(new Roar());
		manager.registerAction(new BackOff());

		manager.registerAction(new AIIdle(this, "mhfc:models/Tigrex/idle.mcanm", 160, 2F));
		manager.registerAction(new AIIdle(this, "mhfc:models/Tigrex/idle3.mcanm", 260, 1F));
		manager.registerAction(
				new AIWander<EntityTigrex>(this, "mhfc:models/Tigrex/walk.mcanm", 122, 3F, 0.08F, 0.4F, 21, 85, 1, 30));

		manager.registerAction(new GroundHurl());

		//	 Register roar to be the only allowed initial move on sight of an enemy
		List<IExecutableAction<? super EntityTigrex>> allowedFirstSight = new ArrayList<>();
		allowedFirstSight.add(new Roar());

		return manager.build(this);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();// def hp 18432D
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(healthbaseHP(18432D));
	}

	@Override
	protected void dropFewItems(boolean par1, int par2) {
		dropItemRand(SubTypedItem.fromSubItem(MaterialSubType.TIGREXSKULLSHELL, 1));
		dropItemRand(SubTypedItem.fromSubItem(MaterialSubType.TIGREXTAIL, 1));
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
		this.playSound(MHFCSoundRegistry.getRegistry().tigrexStep, 0.7F, 1.0F);
	}
}
