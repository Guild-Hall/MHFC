package mhfc.net.common.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.github.worldsender.mcanm.client.model.mcanmmodel.animation.IAnimation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mhfc.net.MHFCMain;
import mhfc.net.common.ai.general.WeightedPick;
import mhfc.net.common.network.PacketPipeline;
import mhfc.net.common.network.message.MessageAIAttack;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAITasks;

public class AIActionManager<EntType extends EntityLiving & IManagedActions<EntType>>
		implements
		IActionManager<EntType> {

	protected final List<IExecutableAction<? super EntType>> attacks = new ArrayList<IExecutableAction<? super EntType>>();
	protected IExecutableAction<? super EntType> activeAttack = null;
	protected EntType entity;

	public AIActionManager(EntType entity) {
		this.entity = Objects.requireNonNull(entity);
	}

	/**
	 * Should be only called from arriving attack packages for the client as attack selection is done on the server
	 */
	@SideOnly(Side.CLIENT)
	public void setAttack(int index) {
		swapAttacks(this.activeAttack, index < 0 ? null : this.attacks.get(index));
	}

	@Override
	public IExecutableAction<? super EntType> chooseAttack() {
		return WeightedPick.pickRandom(attacks);
	}

	protected void sendUpdate() {
		if (!this.entity.worldObj.isRemote) {
			PacketPipeline.networkPipe
					.sendToAll(new MessageAIAttack<EntType>(this.entity, this.attacks.indexOf(activeAttack)));
		}
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
		sendUpdate();
	}

	/**
	 * Return <code>true</code> to continue executing
	 */
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
	public void updateTask() {
		activeAttack.updateAction();
	}

	@Override
	public void registerAttack(IExecutableAction<? super EntType> attack) {
		Objects.requireNonNull(attack);
		attack.rebind(entity);
		this.attacks.add(attack);
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
