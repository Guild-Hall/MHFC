package mhfc.net.common.entity.creature.incomplete;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.model.util.RenderPassInformation;

import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.manager.builder.ActionManagerBuilder;
import mhfc.net.common.entity.CollisionParts;
import mhfc.net.common.entity.CreatureAttributes;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class Ukanlos extends CreatureAttributes<Ukanlos> {

	public Ukanlos(World world) {
		super(world);
		setSize(9f, 9f);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(healthbaseHP(145391D));
		getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(25D);
	}

	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 0, true, true, null));
	}

	@Override
	protected IActionManager<Ukanlos> constructActionManager() {
		ActionManagerBuilder<Ukanlos> manager = new ActionManagerBuilder<>();
		return manager.build(this);
	}

	@Override
	public CollisionParts[] getParts() {
		return null;
	}



	@Override
	public RenderPassInformation preRenderCallback(float scale, RenderPassInformation sub) {
		GL11.glScaled(5, 5, 5);
		return super.preRenderCallback(scale, sub);
	}

}
