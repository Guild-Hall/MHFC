package mhfc.net.common.entity.monster;

import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.IStancedEntity;
import mhfc.net.common.ai.entity.rathalos.BiteAttack;
import mhfc.net.common.ai.entity.rathalos.ChargeAttack;
import mhfc.net.common.ai.entity.rathalos.FireballAttack;
import mhfc.net.common.ai.entity.rathalos.FlyLand;
import mhfc.net.common.ai.entity.rathalos.FlyStart;
import mhfc.net.common.ai.entity.rathalos.JumpFireball;
import mhfc.net.common.ai.entity.rathalos.TailSpin;
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

	private int confusedAttacks;
	private Stances stance;

	public EntityRathalos(World world) {
		super(world);
		this.stance = Stances.GROUND;
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
	}

	@Override
	public IActionManager<EntityRathalos> constructActionManager() {
		ActionManagerBuilder<EntityRathalos> stancedAttackManager = new ActionManagerBuilder<>();
		stancedAttackManager.registerAction(new BiteAttack());
		stancedAttackManager.registerAction(new ChargeAttack());
		stancedAttackManager.registerAction(new FireballAttack());
		stancedAttackManager.registerAction(new FlyStart());
		stancedAttackManager.registerAction(new JumpFireball());
		stancedAttackManager.registerAction(new TailSpin());
		stancedAttackManager.registerAction(new FlyLand());
		return stancedAttackManager.build(this);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(50f);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(healthbaseHP(4500D, 9000D, 18000D));
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
