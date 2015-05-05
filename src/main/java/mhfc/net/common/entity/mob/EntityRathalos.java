package mhfc.net.common.entity.mob;

import mhfc.net.common.ai.AIStancedAttackManager;
import mhfc.net.common.ai.IExecutableAttack;
import mhfc.net.common.ai.IStancedAttackManager;
import mhfc.net.common.ai.IStancedManagedAttacks;
import mhfc.net.common.ai.rathalos.*;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.entity.type.EntityMHFCPart;
import mhfc.net.common.entity.type.IConfusable;
import net.minecraft.world.World;

public class EntityRathalos extends EntityMHFCBase<EntityRathalos>
	implements
		IStancedManagedAttacks<EntityRathalos, EntityRathalos.Stances>,
		IConfusable {

	public static enum Stances
		implements
			IStancedAttackManager.Stance<EntityRathalos, Stances> {
		GROUND,
		FLYING,
		FALLING,
		BLINDED {
			@Override
			public void onAttackStart(
				IExecutableAttack<? super EntityRathalos> attack,
				EntityRathalos entity) {
				entity.confusedAttacks++;
			}

			@Override
			public void onAttackEnd(
				IExecutableAttack<? super EntityRathalos> attack,
				EntityRathalos entity) {
				if (entity.getNumberOfConfusedAttacks() == 3) {
					entity.getAttackManager().setNextStance(GROUND);
				}
			}
		};

		@Override
		public void onAttackEnd(
			IExecutableAttack<? super EntityRathalos> attack,
			EntityRathalos entity) {
		}

		@Override
		public void onAttackStart(
			IExecutableAttack<? super EntityRathalos> attack,
			EntityRathalos entity) {
		}

		@Override
		public void onStanceStart() {
		}

		@Override
		public void onStanceEnd() {
		}

	}

	private final AIStancedAttackManager<EntityRathalos, Stances> attackManager;
	private int confusedAttacks;

	public EntityRathalos(World world) {
		super(world);
		tasks.removeTask(super.attackManager);
		this.attackManager = new AIStancedAttackManager<EntityRathalos, Stances>(
			this, Stances.GROUND);
		attackManager.registerAttack(new BiteAttack());
		attackManager.registerAttack(new ChargeAttack());
		attackManager.registerAttack(new FireballAttack());
		attackManager.registerAttack(new FlyStart());
		attackManager.registerAttack(new JumpFireball());
		attackManager.registerAttack(new TailSpin());
		attackManager.registerAttack(new FlyLand());
		tasks.addTask(0, attackManager);
	}

	@Override
	public EntityMHFCPart[] getParts() {
		return null;
	}

	@Override
	public AIStancedAttackManager<EntityRathalos, Stances> getAttackManager() {
		return attackManager;
	}

	@Override
	public void confuse() {
		confusedAttacks = 0;
		attackManager.setNextStance(Stances.BLINDED);
	}

	@Override
	public int getNumberOfConfusedAttacks() {
		return confusedAttacks;
	}

	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		// FIXME This should disable falling during flying but it also makes
		// collision weird I don't know if it disables AI movement as well
		if (attackManager.getCurrentStance() == Stances.FLYING)
			this.posY = this.prevPosY;
	}

	/**
	 * Deals fall damage to the entity, capture it unless Rathalos was staggered
	 */
	@Override
	protected void updateFallState(double par1, boolean par3) {
		if (attackManager.getCurrentStance() != Stances.FALLING)
			return;
		super.updateFallState(par1, par3);
	}
}
