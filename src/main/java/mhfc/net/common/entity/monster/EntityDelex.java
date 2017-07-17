package mhfc.net.common.entity.monster;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.model.util.RenderPassInformation;

import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.entity.AIBreathe;
import mhfc.net.common.ai.entity.AIDeath;
import mhfc.net.common.ai.entity.monsters.delex.Bite;
import mhfc.net.common.ai.entity.monsters.delex.MoveToTarget;
import mhfc.net.common.ai.entity.monsters.delex.Tackle;
import mhfc.net.common.ai.entity.monsters.delex.Wander;
import mhfc.net.common.ai.manager.builder.ActionManagerBuilder;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.entity.type.EntityMHFCPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.world.World;

public class EntityDelex extends EntityMHFCBase<EntityDelex> {

	public EntityDelex(World world) {
		super(world);
		setSize(2f, 2f);
	}

	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntitySheep.class, 0, true, false, null));
	}

	@Override
	protected IActionManager<EntityDelex> constructActionManager() {
		ActionManagerBuilder<EntityDelex> actionManager = new ActionManagerBuilder<>();
		actionManager.registerAction(new Bite());
		actionManager.registerAction(new Tackle());
		actionManager.registerAction(new MoveToTarget(1.1F));
		actionManager.registerAction(new AIBreathe(this, "mhfc:models/Delex/delexidle.mcanm", 60, 5f));
		actionManager.registerAction(new Wander(0.7F));
		actionManager.registerAction(
				setDeathAction(
						new AIDeath(
								this,
								"mhfc:models/Delex/DelexDeath.mcanm",
								MHFCSoundRegistry.getRegistry().delexHurt)));
		return actionManager.build(this);
	}

	@Override
	public EntityMHFCPart[] getParts() {
		return null;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		//default hp 416D
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(healthbaseHP(180D));
	}

	@Override
	public RenderPassInformation preRenderCallback(float scale, RenderPassInformation sub) {
		GL11.glScaled(1.6, 1.6, 1.6);
		return super.preRenderCallback(scale, sub);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
	}

}
