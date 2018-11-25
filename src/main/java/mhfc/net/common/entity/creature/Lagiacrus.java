package mhfc.net.common.entity.creature;

import com.github.worldsender.mcanm.client.model.util.RenderPassInformation;
import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.entity.AIBite;
import mhfc.net.common.ai.entity.AIBreathe;
import mhfc.net.common.ai.entity.AIDeath;
import mhfc.net.common.ai.entity.AIWander;
import mhfc.net.common.ai.entity.monsters.lagiacrus.Roar;
import mhfc.net.common.ai.entity.monsters.lagiacrus.Sweep;
import mhfc.net.common.ai.manager.builder.ActionManagerBuilder;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.CreatureAttributes;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class Lagiacrus extends CreatureAttributes<Lagiacrus> {

	public Lagiacrus(World world) {
		super(world);
		setSize(9F, 7F);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(healthbaseHP(25100D));
		getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(23D);
	}

	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 0, true, true, null));
	}

	@Override
	protected IActionManager<Lagiacrus> constructActionManager() {
		ActionManagerBuilder<Lagiacrus> actionManager = new ActionManagerBuilder<>();
		actionManager.registerAction(new AIBite(this, "mhfc:models/Lagiacrus/LagiacrusBite.mcanm", 40, 8, 125, 8, MHFCSoundRegistry.getRegistry().lagiacrusBite, 15, true, 15, 20));
		actionManager.registerAction(new AIBite(this, "mhfc:models/Lagiacrus/LagiacrusBite.mcanm", 60, 28, 105, 5, MHFCSoundRegistry.getRegistry().lagiacrusBite, 15, true, 25, 30));
		actionManager.registerAction(new AIWander<Lagiacrus>(this, "mhfc:models/Lagiacrus/LagiacrusWalk.mcanm", 100, 10F, 0.2F, 0.7F, 20	, 80, 1, 10));
		actionManager.registerAction(new AIBreathe(this, "mhfc:models/Lagiacrus/LagiacrusIdle.mcanm", 50, 15F));
		
		actionManager.registerAction(new Sweep());
		actionManager.registerAction(new Roar());
		actionManager.registerAction(
				setDeathAction(
						new AIDeath(
								this,
								"mhfc:models/Lagiacrus/LagiacrusHurt.mcanm",
								MHFCSoundRegistry.getRegistry().lagiacrusDeath)));
		return actionManager.build(this);
	}

	@Override
	public MultiPartEntityPart[] getParts() {
		return null;
	}



	@Override
	public RenderPassInformation preRenderCallback(float scale, RenderPassInformation sub) {
		GL11.glScaled(5.1, 5.1, 5.1);
		return super.preRenderCallback(scale, sub);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return MHFCSoundRegistry.getRegistry().lagiacrusIdle;
	}

}
