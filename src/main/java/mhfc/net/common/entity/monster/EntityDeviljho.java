package mhfc.net.common.entity.monster;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.model.util.RenderPassInformation;

import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.entity.boss.deviljho.Bite1;
import mhfc.net.common.ai.entity.boss.deviljho.Bite2;
import mhfc.net.common.ai.entity.boss.deviljho.Death;
import mhfc.net.common.ai.entity.boss.deviljho.FrontalBreathe;
import mhfc.net.common.ai.entity.boss.deviljho.Idle;
import mhfc.net.common.ai.entity.boss.deviljho.Jump;
import mhfc.net.common.ai.entity.boss.deviljho.Launch;
import mhfc.net.common.ai.entity.boss.deviljho.MoveToTarget;
import mhfc.net.common.ai.entity.boss.deviljho.Roar;
import mhfc.net.common.ai.entity.boss.deviljho.Stomp;
import mhfc.net.common.ai.entity.boss.deviljho.TailWhip;
import mhfc.net.common.ai.entity.boss.deviljho.Wander;
import mhfc.net.common.ai.manager.builder.ActionManagerBuilder;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.entity.type.EntityMHFCPart;
import mhfc.net.common.item.materials.ItemDeviljho.DeviljhoSubType;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityDeviljho extends EntityMHFCBase<EntityDeviljho> {

	public EntityDeviljho(World WORLD) {
		super(WORLD);
		setSize(7.6F, 6F);
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		stepHeight = 2.0F;
	}

	@Override
	public IActionManager<EntityDeviljho> constructActionManager() {
		ActionManagerBuilder<EntityDeviljho> attackManager = new ActionManagerBuilder<>();
		attackManager.registerAction(setDeathAction(new Death()));
		attackManager.registerAction(new Idle());
		attackManager.registerAction(new Idle());
		attackManager.registerAction(new Bite1());
		attackManager.registerAction(new Bite2());
		attackManager.registerAction(new Launch());
		attackManager.registerAction(new MoveToTarget());
		attackManager.registerAction(new Roar());
		attackManager.registerAction(new Stomp());
		attackManager.registerAction(new TailWhip());
		attackManager.registerAction(new Jump());
		attackManager.registerAction(new FrontalBreathe());
		attackManager.registerAction(new Wander());
		return attackManager.build(this);
	}

	@Override
	public EntityMHFCPart[] getParts() {
		return null;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(healthbaseHP(42253D));
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
	
	boolean CamShake = false;
	float CamShakeIntensity;

}
