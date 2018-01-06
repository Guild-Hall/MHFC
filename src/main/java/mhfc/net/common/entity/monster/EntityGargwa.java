package mhfc.net.common.entity.monster;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.model.util.RenderPassInformation;

import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.entity.AIBreathe;
import mhfc.net.common.ai.entity.AIDeath;
import mhfc.net.common.ai.entity.AIIdle;
import mhfc.net.common.ai.entity.AIWander;
import mhfc.net.common.ai.entity.monsters.gargwa.Sleep;
import mhfc.net.common.ai.manager.builder.ActionManagerBuilder;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.entity.type.EntityMHFCPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityGargwa extends EntityMHFCBase<EntityGargwa> {

	public EntityGargwa(World world) {
		super(world);
		setSize(2f, 2f);
	}

	@Override
	protected IActionManager<EntityGargwa> constructActionManager() {
		ActionManagerBuilder<EntityGargwa> actionManager = new ActionManagerBuilder<>();
		actionManager.registerAction(new AIBreathe(this, "mhfc:models/Gagua/gaguabreathe.mcanm", 40, 2F));
		actionManager.registerAction(new AIIdle(this, "mhfc:models/Gagua/lookaround.mcanm", 100, 0.5F));
		actionManager.registerAction(new Sleep());
		actionManager.registerAction(
				setDeathAction(
						new AIDeath(
								this,
								"mhfc:models/Gagua/GaguaDeath.mcanm",
								MHFCSoundRegistry.getRegistry().gargwaDeath)));
		actionManager.registerAction(
				new AIWander<EntityGargwa>(
						this,
						"mhfc:models/gagua/gaguawalk.mcanm",
						60,
						1.5F,
						0.06F,
						0.2F,
						0,
						31,
						0,
						10));
		return actionManager.build(this);
	}

	@Override
	protected void applyEntityAttributes() {
		//HashMap<String, Double> attb = new HashMap<String, Double>();
		//attb.put("a1", 65D);
		//attb.put("a2", 45D);
		//this.monsterAttributes(attb);
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(healthbaseHP(60D));
		getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(45D);
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

	@Override
	protected SoundEvent getAmbientSound() {
		return MHFCSoundRegistry.getRegistry().gargwaIdle;
	}

}
