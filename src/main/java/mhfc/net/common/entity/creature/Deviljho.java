package mhfc.net.common.entity.creature;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.model.util.RenderPassInformation;

import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.entity.AIAngleWhip;
import mhfc.net.common.ai.entity.AIBite;
import mhfc.net.common.ai.entity.AIBreathe;
import mhfc.net.common.ai.entity.AIDeath;
import mhfc.net.common.ai.entity.monsters.deviljho.Charge;
import mhfc.net.common.ai.entity.monsters.deviljho.DragonBreath;
import mhfc.net.common.ai.entity.monsters.deviljho.Jump;
import mhfc.net.common.ai.entity.monsters.deviljho.Launch;
import mhfc.net.common.ai.entity.monsters.deviljho.Roar;
import mhfc.net.common.ai.entity.monsters.deviljho.Stomp;
import mhfc.net.common.ai.manager.builder.ActionManagerBuilder;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.CollisionParts;
import mhfc.net.common.entity.CreatureAttributes;
import mhfc.net.common.item.materials.ItemMaterial.MaterialSubType;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Deviljho extends CreatureAttributes<Deviljho> {

	public Deviljho(World world) {
		super(world);
		setSize(7.8F, 7F);
		stepHeight = 2.0F;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8600D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(50D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(30D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(80D);
	}

	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 0, true, true, null));
		targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
	}

	@Override
	protected IActionManager<Deviljho> constructActionManager() {
		ActionManagerBuilder<Deviljho> attackManager = new ActionManagerBuilder<>();
		attackManager.registerAction(
				setDeathAction(
						new AIDeath(
								this,
								"mhfc:models/Deviljho/DeviljhoDeath.mcanm",
								MHFCSoundRegistry.getRegistry().deviljhoDeath)));
		attackManager.registerAction(new AIBreathe(this, "mhfc:models/Deviljho/breathe.mcanm", 60, 2F));
		attackManager.registerAction(
				new AIBite(
						this,
						"mhfc:models/Deviljho/bite.mcanm",
						40,
						25,
						115,
						15,
						MHFCSoundRegistry.getRegistry().deviljhoBiteA,
						9.0F,
						false,
						0,
						0));
		attackManager.registerAction(
				new AIBite(
						this,
						"mhfc:models/Deviljho/bite2.mcanm",
						35,
						25,
						105,
						12,
						MHFCSoundRegistry.getRegistry().deviljhoBiteB,
						9.0F,
						false,
						0,
						0));

		attackManager.registerAction(
				new AIAngleWhip<>(
						"mhfc:models/Deviljho/tailswipe.mcanm",
						55,
						5,
						145,
						18,
						MHFCSoundRegistry.getRegistry().deviljhoTailWhip,
						12,
						8,
						1,
						180,
						11));
		// to be optimize
		attackManager.registerAction(new Launch());
		attackManager.registerAction(new Charge());
		attackManager.registerAction(new Stomp());
		attackManager.registerAction(new Roar());
		attackManager.registerAction(new Jump());

		attackManager
				.registerAction(new DragonBreath("mhfc:models/Deviljho/DeviljhoFrontalBreathe.mcanm", 80, 15, 25F));
		return attackManager.build(this);
	}

	@Override
	public CollisionParts[] getParts() {
		return null;
	}

	@Override
	public RenderPassInformation preRenderCallback(float scale, RenderPassInformation sub) {
		GL11.glScaled(3.7, 3.7, 3.7);
		return super.preRenderCallback(scale, sub);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return MHFCSoundRegistry.getRegistry().deviljhoIdle;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block blockIn) {
		this.playSound(MHFCSoundRegistry.getRegistry().deviljhoStep, 1.0F, 1.0F);
	}

	@Override
	protected void dropFewItems(boolean par1, int par2) {
		int var4;
		for (var4 = 0; var4 < 2; ++var4) {
			dropItemRand(SubTypedItem.fromSubItem(MaterialSubType.DEVILJHOSCALE, 2));
		}
		for (var4 = 0; var4 < 1; ++var4) {
			dropItemRand(SubTypedItem.fromSubItem(MaterialSubType.DEVILJHOFANG, 1));

		}
		for (var4 = 0; var4 < 1; ++var4) {
			dropItemRand(SubTypedItem.fromSubItem(MaterialSubType.DEVILJHOHIDE, 1));

		}
		dropItemRand(SubTypedItem.fromSubItem(MaterialSubType.DEVILJHOSCALP, 1));
		dropItemRand(SubTypedItem.fromSubItem(MaterialSubType.DEVILJHOTALON, 1));
		dropItemRand(SubTypedItem.fromSubItem(MaterialSubType.DEVILJHOTAIL, 1));
	}

}
