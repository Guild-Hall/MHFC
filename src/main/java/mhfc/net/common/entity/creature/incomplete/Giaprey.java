package mhfc.net.common.entity.creature.incomplete;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.model.util.RenderPassInformation;

import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.manager.builder.ActionManagerBuilder;
import mhfc.net.common.entity.CreatureAttributes;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class Giaprey extends CreatureAttributes<Giaprey> {

	public Giaprey(World world) {
		super(world);
		setSize(2f, 1f);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(healthbaseHP(4310D));
		getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(7D);
	}

	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 0, true, true, null));
	}

	@Override
	protected IActionManager<Giaprey> constructActionManager() {
		ActionManagerBuilder<Giaprey> actionManager = new ActionManagerBuilder<>();
		return actionManager.build(this);
	}

	@Override
	public MultiPartEntityPart[] getParts() {
		return null;
	}


	@Override
	public RenderPassInformation preRenderCallback(float scale, RenderPassInformation sub) {
		GL11.glScaled(0.8, 0.8, 0.8);
		return super.preRenderCallback(scale, sub);
	}

}
