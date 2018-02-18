package mhfc.net.common.entity.monster.wip;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.model.util.RenderPassInformation;

import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.entity.AIBite;
import mhfc.net.common.ai.entity.AIDeath;
import mhfc.net.common.ai.entity.monsters.greatjaggi.Charge;
import mhfc.net.common.ai.entity.monsters.greatjaggi.Idle;
import mhfc.net.common.ai.entity.monsters.greatjaggi.Whip;
import mhfc.net.common.ai.manager.builder.ActionManagerBuilder;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.entity.type.EntityMHFCPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityGreatJaggi extends EntityMHFCBase<EntityGreatJaggi> {
	// TODO: implement rage
	public int rageLevel;

	public EntityGreatJaggi(World world) {
		super(world);
		setSize(2F, 2F);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(healthbaseHP(8753D));
		getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(10D);
	}

	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 0, true, true, null));
	}

	@Override
	protected IActionManager<EntityGreatJaggi> constructActionManager() {
		ActionManagerBuilder<EntityGreatJaggi> actionManager = new ActionManagerBuilder<>();
		actionManager
				.registerAction(new AIBite(this, "mhfc:models/GreatJaggi/GreatJaggiBite.mcanm", 33, 23, 35, 5, null));
		actionManager.registerAction(new Idle());
	//	actionManager.registerAction(new Roar());
		actionManager.registerAction(new Charge());
		actionManager.registerAction(new Whip());
	//	actionManager.registerAction(new Wander());

		actionManager.registerAction(
				setDeathAction(
						new AIDeath(
								this,
								"mhfc:models/GreatJaggi/GreatJaggiDeath.mcanm",
								MHFCSoundRegistry.getRegistry().greatJaggiDeath)));
		return actionManager.build(this);
	}

	@Override
	public EntityMHFCPart[] getParts() {
		return null;
	}


	@Override
	public RenderPassInformation preRenderCallback(float scale, RenderPassInformation sub) {
		GL11.glScaled(1.6, 1.6, 1.6);
		return super.preRenderCallback(scale, sub);
	}

}
