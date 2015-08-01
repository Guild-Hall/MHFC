package mhfc.net.common.ai;

import java.util.Objects;

import net.minecraft.entity.EntityLivingBase;

public class AIStancedActionManager<EntityT extends EntityLivingBase & IStancedManagedActions<EntityT, StanceT>, //
StanceT extends Enum<StanceT> & IStancedActionManager.Stance<EntityT, StanceT>>
	extends
		AIActionManager<EntityT>
	implements
		IStancedActionManager<EntityT, StanceT> {

	protected StanceT currentStance, nextStance;

	public AIStancedActionManager(EntityT entity, StanceT initalStance) {
		super(entity);
		Objects.requireNonNull(initalStance);
		this.currentStance = this.nextStance = initalStance;
	}

	@Override
	public void setNextStance(StanceT newMode) {
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
	protected void swapAttacks(IExecutableAction<? super EntityT> oldAttack,
		IExecutableAction<? super EntityT> newAttack) {
		this.entity.onAttackEnd(oldAttack);
		if (oldAttack != null)
			oldAttack.finishAction();
		this.activeAttack = newAttack;
		this.entity.onAttackStart(newAttack);
		if (newAttack != null)
			newAttack.beginAction();
		sendUpdate();
	}

	@Override
	public StanceT getCurrentStance() {
		return currentStance;
	}

	@Override
	public IExecutableAction<? super EntityT> chooseAttack() {
		IExecutableAction<? super EntityT> attack = super.chooseAttack();
		return attack;
	}
}
