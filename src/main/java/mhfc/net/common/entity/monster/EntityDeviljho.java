package mhfc.net.common.entity.monster;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.model.mcanmmodel.data.RenderPassInformation;

import mhfc.net.common.ai.AIActionManager;
import mhfc.net.common.ai.entity.deviljho.DeviljhoDeath;
import mhfc.net.common.ai.entity.deviljho.DeviljhoFrontalBreathe;
import mhfc.net.common.ai.entity.deviljho.DeviljhoIdle;
import mhfc.net.common.ai.entity.deviljho.DeviljhoWander;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.entity.type.EntityMHFCPart;
import mhfc.net.common.item.materials.ItemDeviljho.DeviljhoSubType;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityDeviljho extends EntityMHFCBase<EntityDeviljho> {

	public EntityDeviljho(World WORLD) {
		super(WORLD);
		setSize(7.6F, 6F);
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));

		AIActionManager<EntityDeviljho> attackManager = getAIActionManager();

		attackManager.registerAttack(new DeviljhoIdle());

		attackManager.registerAttack(new DeviljhoFrontalBreathe());
		attackManager.registerAttack(new DeviljhoDeath());
		attackManager.registerAttack(new DeviljhoWander());

	}

	@Override
	public EntityMHFCPart[] getParts() {
		return null;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getAttributeMap().getAttributeInstance(SharedMonsterAttributes.followRange).setBaseValue(128d);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.3D);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(healthbaseHP(26839D, 25000D, 50000D));
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(35D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.32D);
	}

	@Override
	public RenderPassInformation preRenderCallback(float scale, RenderPassInformation sub) {
		GL11.glScaled(3.7, 3.7, 3.7);
		return super.preRenderCallback(scale, sub);

	}

	@Override
	protected String getLivingSound() {
		return "mhfc:deviljho.idle";
	}

	@Override
	protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_) {
		this.playSound("mhfc:deviljho.step", 1.0F, 1.0F);
	}

	@Override
	protected void dropFewItems(boolean par1, int par2) {
		int var4;
		for (var4 = 0; var4 < 13; ++var4) {
			dropItemRand(SubTypedItem.fromSubItem(DeviljhoSubType.SCALE, 2));
		}
		for (var4 = 0; var4 < 8; ++var4) {
			dropItemRand(SubTypedItem.fromSubItem(DeviljhoSubType.FANG, 1));

		}
		for (var4 = 0; var4 < 1; ++var4) {
			dropItemRand(SubTypedItem.fromSubItem(DeviljhoSubType.HIDE, 1));

		}
		dropItemRand(SubTypedItem.fromSubItem(DeviljhoSubType.SCALP, 1));
		dropItemRand(SubTypedItem.fromSubItem(DeviljhoSubType.TALON, 1));
		dropItemRand(SubTypedItem.fromSubItem(DeviljhoSubType.TAIL, 1));
	}

}
