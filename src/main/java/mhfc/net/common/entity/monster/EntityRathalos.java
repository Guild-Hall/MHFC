package mhfc.net.common.entity.monster;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.model.util.RenderPassInformation;

import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.IStancedEntity;
import mhfc.net.common.ai.entity.boss.rathalos.Rush;
import mhfc.net.common.ai.entity.boss.rathalos.BiteLeft;
import mhfc.net.common.ai.entity.boss.rathalos.BiteRight;
import mhfc.net.common.ai.entity.boss.rathalos.Death;
import mhfc.net.common.ai.entity.boss.rathalos.Idle;
import mhfc.net.common.ai.entity.boss.rathalos.Wander;
import mhfc.net.common.ai.entity.boss.rathalos.TailWhip;
import mhfc.net.common.ai.manager.builder.ActionManagerBuilder;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.entity.type.EntityMHFCPart;
import mhfc.net.common.entity.type.IConfusable;
import mhfc.net.common.item.materials.ItemRathalos.RathalosSubType;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityRathalos extends EntityMHFCBase<EntityRathalos>
		implements
		IStancedEntity<EntityRathalos, EntityRathalos.Stances>,
		IConfusable {

	public static enum Stances implements IStancedEntity.IStance<EntityRathalos> {
		GROUND,
		FLYING,
		FALLING,
		BLINDED {
			@Override
			public void onAttackStart(IExecutableAction<? super EntityRathalos> attack, EntityRathalos entity) {
				entity.confusedAttacks++;
			}

			@Override
			public void onAttackEnd(IExecutableAction<? super EntityRathalos> attack, EntityRathalos entity) {
				if (entity.getNumberOfConfusedAttacks() == 3) {
					entity.setStance(GROUND);
				}
			}
		};

		@Override
		public void onAttackEnd(IExecutableAction<? super EntityRathalos> attack, EntityRathalos entity) {}

		@Override
		public void onAttackStart(IExecutableAction<? super EntityRathalos> attack, EntityRathalos entity) {}

		@Override
		public void onStanceStart() {}

		@Override
		public void onStanceEnd() {}

	}

	@Override
	public RenderPassInformation preRenderCallback(float scale, RenderPassInformation sub) {
		GL11.glScaled(2, 2, 2);
		return super.preRenderCallback(scale, sub);
	}

	private int confusedAttacks;
	private Stances stance;

	public EntityRathalos(World world) {
		super(world);
		this.stance = Stances.GROUND;
		this.setSize(5F, 5F);
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
	}

	@Override
	public IActionManager<EntityRathalos> constructActionManager() {
		ActionManagerBuilder<EntityRathalos> stancedAttackManager = new ActionManagerBuilder<>();
		stancedAttackManager.registerAction(new Idle());
		stancedAttackManager.registerAction(new Wander());
		stancedAttackManager.registerAction(new BiteLeft());
		stancedAttackManager.registerAction(new BiteRight());
		stancedAttackManager.registerAction(new TailWhip());
		stancedAttackManager.registerAction(new Rush());
		stancedAttackManager.registerAction(setDeathAction(new Death()));
		return stancedAttackManager.build(this);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(healthbaseHP(14981D));
	}

	@Override
	public EntityMHFCPart[] getParts() {
		return null;
	}

	@Override
	public void confuse() {
		confusedAttacks = 0;
		setStance(Stances.BLINDED);
	}

	@Override
	public int getNumberOfConfusedAttacks() {
		return confusedAttacks;
	}

	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
	}

	/**
	 * FIXME This should disable falling during flying but it also makes collision weird I don't know if it disables AI
	 * movement as well
	 */
	@Override
	protected void updateFallState(double par1, boolean par3) {
		if (getStance() == Stances.FLYING) {
			this.motionY = 0;
			this.fallDistance = 0;
			par1 = 0;
		}

		super.updateFallState(par1, par3);
	}

	@Override
	public void setStance(Stances stance) {
		this.stance.onStanceEnd();
		this.stance = stance;
		this.stance.onStanceStart();
	}

	@Override
	public void onAttackStart(IExecutableAction<? super EntityRathalos> newAttack) {
		super.onAttackStart(newAttack);
		this.stance.onAttackStart(newAttack, this);
	}

	@Override
	public void onAttackEnd(IExecutableAction<? super EntityRathalos> oldAttack) {
		super.onAttackEnd(oldAttack);
		this.stance.onAttackEnd(oldAttack, this);
	}

	@Override
	public Stances getStance() {
		return this.stance;
	}

	@Override
	protected String getLivingSound() {
		return "mhfc:rathalos.idle";
	}

	@Override
	protected void dropFewItems(boolean par1, int par2) {
		int var4;
		for (var4 = 0; var4 < 13; ++var4) {
			dropItemRand(SubTypedItem.fromSubItem(RathalosSubType.SHELL, 1));
		}
		for (var4 = 0; var4 < 8; ++var4) {
			dropItemRand(SubTypedItem.fromSubItem(RathalosSubType.WEBBING, 1));
			dropItemRand(SubTypedItem.fromSubItem(RathalosSubType.MARROW, 1));

		}
		for (var4 = 0; var4 < 1; ++var4) {
			dropItemRand(SubTypedItem.fromSubItem(RathalosSubType.WING, 1));

		}
		dropItemRand(SubTypedItem.fromSubItem(RathalosSubType.PLATE, 1));
	}
}
