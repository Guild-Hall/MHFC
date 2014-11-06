package mhfc.net.common.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import mhfc.net.common.network.PacketPipeline;
import mhfc.net.common.network.message.MessageAIAttack;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks;

import com.github.worldsender.mcanm.client.model.mhfcmodel.animation.IAnimation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AIAttackManager<T extends EntityLivingBase & IMangedAttacks<T>>
		extends
			EntityAIBase {
	private final List<IExecutableAttack<T>> attacks = new ArrayList<IExecutableAttack<T>>();
	private IExecutableAttack<T> activeAttack = null;
	private T entity;

	public AIAttackManager(T entity) {
		this.entity = Objects.requireNonNull(entity);
	}

	/**
	 * Should be only called from arriving attack packages for the client as
	 * attack selection is done on the server
	 */
	@SideOnly(Side.CLIENT)
	public void setAttack(int index) {
		if (index < 0)
			this.activeAttack = null;
		else {
			this.activeAttack = this.attacks.get(index);
			this.startExecuting();
		}
	}

	private IExecutableAttack<T> chooseAttack() {
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

	@Override
	public void startExecuting() {
		this.sendUpdate();
		this.entity.onAttackStart(activeAttack);
		this.activeAttack.beginExecution();
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
		this.entity.onAttackEnd(activeAttack);
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

	public IAnimation getCurrentAnimation() {
		return activeAttack == null ? null : activeAttack.getCurrentAnimation();
	}

	public int getNextFrame(int current) {
		return activeAttack == null ? -1 : activeAttack.getNextFrame(current);
	}
}
