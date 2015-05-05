package mhfc.net.common.ai;

import net.minecraft.entity.EntityLivingBase;

public class AIStancedAttackManager<EntityT extends EntityLivingBase & IStancedManagedAttacks<EntityT, StanceT>, //
StanceT extends Enum<StanceT> & IStancedAttackManager.Stance<EntityT, StanceT>>
	extends
		AIAttackManager<EntityT>
	implements
		IStancedAttackManager<EntityT, StanceT> {

	protected StanceT currentStance, nextStance;

	public AIStancedAttackManager(EntityT entity) {
		super(entity);
	}

	@Override
	public void setNextMode(StanceT newMode) {
		nextStance = newMode;
	}

	/**
	 * The current mode is notified that an attack ends, then the super method
	 * is called and if a new attack was chosen, the new mode is notified. The
	 * new mode is always set as the current mode after this method. The mode
	 * has the chance to modify the next mode last. The methods are called in
	 * the following order:<br>
	 * mode.onAttackEnd() <br>
	 * swpaMode(mode, nextMode)<br>
	 * mode = nextMode<br>
	 * attack = chooseAttack()<br>
	 * swapAttacks(oldAttack, attack)<br>
	 * ->entity.onAttackEnd()<br>
	 * ->oldAttack.finish()<br>
	 * ->entity.onAttackStart()<br>
	 * ->attack.begin()<br>
	 * Mode.onAttackStart()
	 */
	@Override
	protected boolean executeNextAttack() {
		currentStance.onAttackEnd(activeAttack, entity);
		swapStances();
		boolean hasAttack = super.executeNextAttack();
		if (hasAttack)
			currentStance.onAttackStart(activeAttack, entity);
		return hasAttack;
	}

	protected void swapStances() {
		if (currentStance != null)
			currentStance.onStanceEnd();
		this.currentStance = nextStance;
		if (currentStance != null)
			currentStance.onStanceStart();
	}

	@Override
	protected void swapAttacks(IExecutableAttack<? super EntityT> oldAttack,
		IExecutableAttack<? super EntityT> newAttack) {
		this.entity.onAttackEnd(oldAttack);
		if (oldAttack != null)
			oldAttack.finishExecution();
		this.activeAttack = newAttack;
		this.entity.onAttackStart(newAttack);
		if (newAttack != null)
			newAttack.beginExecution();
		sendUpdate();
	}

	@Override
	public StanceT getCurrentMode() {
		return currentStance;
	}
}
