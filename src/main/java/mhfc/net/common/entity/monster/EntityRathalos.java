package mhfc.net.common.entity.monster;

import mhfc.net.common.ai.AIActionManager;
import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.IStancedEntity;
import mhfc.net.common.ai.entity.rathalos.BiteAttack;
import mhfc.net.common.ai.entity.rathalos.ChargeAttack;
import mhfc.net.common.ai.entity.rathalos.FireballAttack;
import mhfc.net.common.ai.entity.rathalos.FlyLand;
import mhfc.net.common.ai.entity.rathalos.FlyStart;
import mhfc.net.common.ai.entity.rathalos.JumpFireball;
import mhfc.net.common.ai.entity.rathalos.TailSpin;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.entity.type.EntityMHFCPart;
import mhfc.net.common.entity.type.IConfusable;
import net.minecraft.entity.SharedMonsterAttributes;
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

	private final AIActionManager<EntityRathalos> stancedAttackManager;
	private int confusedAttacks;
	private Stances stance;

	public EntityRathalos(World world) {
		super(world);
		AIActionManager<EntityRathalos> stancedAttackManager = new AIActionManager<EntityRathalos>(this);
		stancedAttackManager.registerAttack(new BiteAttack());
		stancedAttackManager.registerAttack(new ChargeAttack());
		stancedAttackManager.registerAttack(new FireballAttack());
		stancedAttackManager.registerAttack(new FlyStart());
		stancedAttackManager.registerAttack(new JumpFireball());
		stancedAttackManager.registerAttack(new TailSpin());
		stancedAttackManager.registerAttack(new FlyLand());
		setAIActionManager(stancedAttackManager);
		this.stancedAttackManager = stancedAttackManager;
		this.stance = Stances.GROUND;
	}

	@Override
	protected void applyEntityAttributes() {

		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(healthbaseHP(6000D, 7800D, 8600D));

	}

	@Override
	public EntityMHFCPart[] getParts() {
		return null;
	}

	@Override
	public AIActionManager<EntityRathalos> getAttackManager() {
		return stancedAttackManager;
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
}
