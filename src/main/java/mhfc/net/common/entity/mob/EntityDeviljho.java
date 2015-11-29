package mhfc.net.common.entity.mob;

import mhfc.net.common.ai.AIActionManager;
import mhfc.net.common.ai.entity.deviljho.*;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.entity.type.EntityMHFCPart;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.model.mcanmmodel.data.RenderPassInformation;

public class EntityDeviljho extends EntityMHFCBase<EntityDeviljho> {

	public EntityDeviljho(World WORLD) {
		super(WORLD);
		setSize(9, 7);
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this,
			EntityPlayer.class, 0, true));

		AIActionManager<EntityDeviljho> attackManager = getAIActionManager();
		attackManager.registerAttack(new DeviljhoIdle());
		attackManager.registerAttack(new DeviljhoMovetoTarget());
		attackManager.registerAttack(new DeviljhoRoar());
		attackManager.registerAttack(new DeviljhoBiteA());
		attackManager.registerAttack(new DeviljhoBiteB());
		attackManager.registerAttack(new DeviljhoTailWhip());
	//    attackManager.registerAttack(new DeviljhoWander());
	}

	@Override
	public EntityMHFCPart[] getParts() {
		return null;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getAttributeMap().getAttributeInstance(
			SharedMonsterAttributes.followRange).setBaseValue(128d);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance)
			.setBaseValue(1.3D);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(
			healthbaseHP(17432D, 23874D, 29100D));
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(
			35D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(
			0.32D);
	}

	@Override
	public RenderPassInformation preRenderCallback(float scale,
		RenderPassInformation sub) {
		GL11.glScaled(3.5, 3.5, 3.5);
		return super.preRenderCallback(scale, sub);

	}
	
	@Override
	protected String getLivingSound() {

		return "mhfc:deviljho-idle";
	}
	
	@Override
	protected void func_145780_a(int p_145780_1_, int p_145780_2_,
		int p_145780_3_, Block p_145780_4_) {
		this.playSound("mhfc:deviljho-step", 1.0F, 1.0F);
	}

}
