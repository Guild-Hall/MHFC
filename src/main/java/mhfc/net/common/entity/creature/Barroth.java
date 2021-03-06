package mhfc.net.common.entity.creature;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.model.util.RenderPassInformation;

import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.entity.AIBreathe;
import mhfc.net.common.ai.entity.AIDeath;
import mhfc.net.common.ai.entity.AIWander;
import mhfc.net.common.ai.entity.monsters.barroth.HeadSlam;
import mhfc.net.common.ai.entity.monsters.barroth.Ram;
import mhfc.net.common.ai.entity.monsters.barroth.RamRun;
import mhfc.net.common.ai.entity.monsters.barroth.Roar;
import mhfc.net.common.ai.entity.monsters.barroth.Stomp;
import mhfc.net.common.ai.manager.builder.FollowUpManagerBuilder;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.CollisionParts;
import mhfc.net.common.entity.CreatureAttributes;
import mhfc.net.common.item.materials.ItemMaterial.MaterialSubType;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class Barroth extends CreatureAttributes<Barroth> {

	public int deathTick;
	public int rageLevel;

	public Barroth(World world) {
		super(world);
		setSize(5.3f, 4.3f);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(3572D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(60D);

	}

	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 0, true, true, null));
		targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
	}

	@Override
	protected IActionManager<Barroth> constructActionManager() {
		
		/** Barroth's AI Damage for low rank varies from 35 - 45 **/
		
		FollowUpManagerBuilder<Barroth>	followUpManager = new FollowUpManagerBuilder<>();
		followUpManager.registerAction(new AIBreathe(this, "mhfc:models/Barroth/barrothidle.mcanm", 60, 2f));
		followUpManager.registerAction(
				setDeathAction(
						new AIDeath(
								this,
								"mhfc:models/Barroth/BarrothDeath.mcanm",
								MHFCSoundRegistry.getRegistry().barrothDeath)));

		followUpManager.registerAction(
				new AIWander<Barroth>(
						this,
						"mhfc:models/Barroth/walknew.mcanm",
						67,
						1,
						0.1F,
						0.7F,
						0,
						59,
						1,
						30));
		followUpManager.registerAction(new HeadSlam(7F, 5F, 1F, 270F, 65F));
		followUpManager.registerAllowingAllActions(new RamRun(0.5F, 30F, 60F));
		followUpManager.registerAllowingAllActions(new Ram(66F));
		followUpManager.registerAction(new Stomp(65F));
		Roar roar = new Roar();
		followUpManager.registerAllowingAllActions(roar);
		List<IExecutableAction<? super Barroth>> allowedFirstSight = new ArrayList<>();
		allowedFirstSight.add(roar);
		
		
		return followUpManager.build(this);
	}

	@Override
	public CollisionParts[] getParts() {
		return null;
	}

	@Override
	public RenderPassInformation preRenderCallback(float scale, RenderPassInformation sub) {
		GL11.glScaled(4, 4.2, 4);
		return super.preRenderCallback(scale, sub);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return MHFCSoundRegistry.getRegistry().barrothIdle;
	}

	@Override
	protected void dropFewItems(boolean par1, int par2) {
		dropItemRand(SubTypedItem.fromSubItem(MaterialSubType.BARROTHTAIL, 1));
		dropItemRand(SubTypedItem.fromSubItem(MaterialSubType.BARROTHCARAPACE, 1));
		dropItemRand(SubTypedItem.fromSubItem(MaterialSubType.BARROTHMUD, 1));
	}

}
