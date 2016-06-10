package mhfc.net.common.ai.manager;

import java.util.Objects;

import com.github.worldsender.mcanm.common.animation.IAnimation;

import mhfc.net.MHFCMain;
import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.IManagedActions;
import mhfc.net.common.network.PacketPipeline;
import mhfc.net.common.network.message.MessageAIAction;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAITasks;

public abstract class ActionManagerAdapter<EntType extends EntityLiving & IManagedActions<EntType>>
		implements
		IActionManager<EntType> {

	protected IExecutableAction<? super EntType> activeAttack = null;
	protected EntType entity;

	public ActionManagerAdapter(EntType entity) {
		this.entity = Objects.requireNonNull(entity);
	}

	protected abstract MessageAIAction<EntType> sendUpdate();

	protected void switchAction(IExecutableAction<? super EntType> newAttack) {
		swapAttacks(activeAttack, newAttack);
	}

	protected void swapAttacks(
			IExecutableAction<? super EntType> oldAttack,
			IExecutableAction<? super EntType> newAttack) {
		this.entity.onAttackEnd(oldAttack);
		if (oldAttack != null) {
			oldAttack.finishAction();
		}
		this.activeAttack = newAttack;
		this.entity.onAttackStart(newAttack);
		if (newAttack != null) {
			newAttack.beginAction();
		}
		MHFCMain.logger.debug("Manager for entity {} switched to attack {}", this.entity, this.activeAttack);

		if (!this.entity.worldObj.isRemote) {
			PacketPipeline.networkPipe.sendToAll(sendUpdate());
		}
	}

	/**
	 * Return <code>true</code> to continue executing
	 */
	@Override
	public boolean continueExecuting() {
		if (this.activeAttack == null) {
			IExecutableAction<? super EntType> nextAttack = chooseAttack();
			if (nextAttack == null) {
				return false;
			}
			swapAttacks(null, nextAttack);
		}

		if (this.activeAttack.shouldContinue()) {
			return true;
		}
		return executeNextAttack();
	}

	/**
	 * Go to the next attack, cancel the current attack. This may for example be used when staggered.
	 */
	protected boolean executeNextAttack() {
		IExecutableAction<? super EntType> nextAttack = chooseAttack();
		if (nextAttack == null) {
			return false;
		}
		swapAttacks(this.activeAttack, nextAttack);
		return true;
	}

	/**
	 * Updates this AI every 3 ticks.<br>
	 *
	 * @see EntityAITasks#tickRate
	 */
	@Override
	public void updateTask() {
		activeAttack.updateAction();
	}

	@Override
	public IAnimation getCurrentAnimation() {
		return activeAttack == null ? null : activeAttack.getCurrentAnimation();
	}

	@Override
	public int getCurrentFrame() {
		return activeAttack == null ? -1 : activeAttack.getCurrentFrame();
	}
}
