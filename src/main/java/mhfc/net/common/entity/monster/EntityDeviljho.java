package mhfc.net.common.entity.monster;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.model.util.RenderPassInformation;

import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.entity.monsters.deviljho.DragonBreath;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityDeviljho extends EntityMHFCBase<EntityDeviljho> {

	public EntityDeviljho(World world) {
		super(world);
		setSize(7.6F, 6F);
		stepHeight = 2.0F;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(healthbaseHP(33122D));
		getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(15D);
	}

	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 0, true, true, null));
		targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
	}

	@Override
	protected IActionManager<EntityDeviljho> constructActionManager() {
		ActionManagerBuilder<EntityDeviljho> attackManager = new ActionManagerBuilder<>();


		attackManager
				.registerAction(new DragonBreath("mhfc:models/Deviljho/DeviljhoFrontalBreathe.mcanm", 80, 15, 25F));
		return attackManager.build(this);
	}

	@Override
	public EntityMHFCPart[] getParts() {
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
		for (var4 = 0; var4 < 13; ++var4) {
			dropItemRand(SubTypedItem.fromSubItem(MaterialSubType.DEVILJHOSCALE, 2));
		}
		for (var4 = 0; var4 < 8; ++var4) {
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
