package mhfc.net.common.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks;

public class AIAttackManager<T extends EntityLivingBase> extends EntityAIBase {
	private final List<IExecutableAttack<T>> attacks = new ArrayList<IExecutableAttack<T>>();
	private IExecutableAttack<T> activeAttack = null;
	private T entity;

	public AIAttackManager(T entity) {
		this.entity = Objects.requireNonNull(entity);
	}

	private IExecutableAttack<T> chooseAttack() {
		return WeightedPick.pickRandom(attacks);
	}

	@Override
	public boolean shouldExecute() {
		this.activeAttack = this.chooseAttack();
		return this.activeAttack != null;
	}

	@Override
	public void startExecuting() {
		// TODO: send package to update client
	}
	/**
	 * Return <code>true</code> to continue executing
	 */
	@Override
	public boolean continueExecuting() {
		return this.activeAttack.shouldContinue();
	}
	/**
	 * Terminates this task.
	 */
	@Override
	public void resetTask() {
		this.activeAttack.finishExecution();
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

	public void registerAttack(IExecutableAttack<T> attack) {
		attack.updateEntity(entity);
		this.attacks.add(attack);
	}
}
