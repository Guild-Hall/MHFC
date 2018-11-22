package mhfc.net.common.entity.creature;

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
import mhfc.net.common.entity.CreatureAttributes;
import mhfc.net.common.item.materials.ItemMaterial.MaterialSubType;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class Gargwa extends CreatureAttributes<Gargwa> {

	public Gargwa(World world) {
		super(world);
		setSize(2f, 2f);
	}

	@Override
	protected IActionManager<Gargwa> constructActionManager() {
		ActionManagerBuilder<Gargwa> actionManager = new ActionManagerBuilder<>();
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
				new AIWander<Gargwa>(
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
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(healthbaseHP(60D));
		getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(45D);
	}

	@Override
	public MultiPartEntityPart[] getParts() {
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

	@Override
	protected void dropFewItems(boolean par1, int par2) {
		dropItemRand(SubTypedItem.fromSubItem(MaterialSubType.CARPENTERBUG, 1));
		dropItemRand(SubTypedItem.fromSubItem(MaterialSubType.INSECTHUSK, 1));
		dropItemRand(SubTypedItem.fromSubItem(MaterialSubType.INSECTHUSK, 1));
	}

}
