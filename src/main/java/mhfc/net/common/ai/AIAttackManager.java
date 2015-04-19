package mhfc.net.common.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import mhfc.net.common.network.PacketPipeline;
import mhfc.net.common.network.message.MessageAIAttack;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks;

import com.github.worldsender.mcanm.client.model.mcanmmodel.animation.IAnimation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AIAttackManager<T extends EntityLivingBase & IManagedAttacks<T>>
	extends
		EntityAIBase implements IAttackManager<T, AIAttackManager<T>> {

	private final List<IExecutableAttack<T>> attacks = new ArrayList<IExecutableAttack<T>>();
	private IExecutableAttack<T> activeAttack = null;
	private T entity;

	public AIAttackManager(T entity) {
		this.entity = Objects.requireNonNull(entity, "Entity can't be null");
	}

	/**
	 * Should be only called from arriving attack packages for the client as
	 * attack selection is done on the server
	 */
	@SideOnly(Side.CLIENT)
	public void setAttack(int index) {
		swapAttacks(this.activeAttack, index < 0 ? null : this.attacks
			.get(index));
	}

	@Override
	public IExecutableAttack<T> chooseAttack() {
		return WeightedPick.pickRandom(attacks);
	}

	@Override
	public boolean shouldExecute() {
		this.activeAttack = this.chooseAttack();
		return this.activeAttack != null;
	}

	private void sendUpdate() {
		if (!this.entity.worldObj.isRemote)
			PacketPipeline.networkPipe.sendToAll(new MessageAIAttack<T>(
				this.entity, this.attacks.indexOf(activeAttack)));
	}

	private void swapAttacks(IExecutableAttack<T> oldAttack,
		IExecutableAttack<T> newAttack) {
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
	public void startExecuting() {
		swapAttacks(null, this.activeAttack);
	}

	/**
	 * Return <code>true</code> to continue executing
	 */
	@Override
	public boolean continueExecuting() {
		if (this.activeAttack.shouldContinue())
			return true;
		IExecutableAttack<T> nextAttack = chooseAttack();
		if (nextAttack == null)
			return false;
		swapAttacks(this.activeAttack, nextAttack);
		return true;
	}

	/**
	 * Terminates this task.
	 */
	@Override
	public void resetTask() {
		swapAttacks(this.activeAttack, null);
	}

	/**
	 * Updates this AI every 3 ticks.<br>
	 *
	 * @see EntityAITasks#tickRate
	 */
	@Override
	public void updateTask() {
		this.activeAttack.update();
	}

	/**
	 * <table border="1px">
	 * <caption>Mutexbit Usage</caption>
	 * <tr>
	 * <th>Category
	 * <th>Mutex-bit
	 * </tr>
	 * <tr>
	 * <td>Walking
	 * <td>0b001
	 * </tr>
	 * <tr>
	 * <td>Looking
	 * <td>0b010
	 * </tr>
	 * <tr>
	 * <td>Jumping
	 * <td>0b100
	 * </tr>
	 * <table>
	 */
	@Override
	public int getMutexBits() {
		return this.activeAttack == null ? 0 : this.activeAttack.mutexBits();
	}

	@Override
	public void registerAttack(IExecutableAttack<T> attack) {
		Objects.requireNonNull(attack);
		attack.rebind(entity);
		this.attacks.add(attack);
	}

	@Override
	public IAnimation getCurrentAnimation() {
		return activeAttack == null ? null : activeAttack.getCurrentAnimation();
	}

	@Override
	public int getNextFrame(int current) {
		return activeAttack == null ? -1 : activeAttack.getNextFrame(current);
	}

	@Override
	public AIAttackManager<T> getEntityAI() {
		return this;
	}

}
