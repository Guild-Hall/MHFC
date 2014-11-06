package mhfc.net.common.ai;

import mhfc.net.common.ai.WeightedPick.WeightedItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

import com.github.worldsender.mcanm.client.model.mhfcmodel.animation.IAnimation;
/**
 * A derived class can specify an Entity-class this method is applicable to. The
 * typeparameter T should be as narrow as necessary but as broad as possible.
 * E.g. to make an entity jump you would set T as {@link EntityLivingBase}
 * because all those entities can jump. This makes it possible to reuse attacks
 * and (WIP) apply them to special weapons.
 *
 * @author WorldSEnder
 *
 * @param <T>
 *            the necessary Entity class
 */
public interface IExecutableAttack<T extends Entity> extends WeightedItem {
	/**
	 * Tells the attack that it is rebound to the given entity. This might
	 * happen when the entity is teleported and a new entity had to be created
	 * or when the attack is assigned to an abnormal entity through special
	 * behavior, e.g. the player when equipping special weapons.
	 *
	 * @param entity
	 */
	public void updateEntity(T entity);
	/**
	 * Gets how firmly this attack wants to be executed. The higher the value
	 * the more likely the attack will be executed. Returning 0 means that the
	 * attack will not be executed at all.
	 *
	 * @return
	 */
	@Override
	public float getWeight();
	/**
	 * Returns if this attack has to be executed (e.g. a stun animation) this
	 * gets checked every tick to determine if the current attack should be
	 * stopped. This also gets checked before a new animation is picked.<br>
	 * Contract: The attacks will be checked in the order they were registered.
	 * The only exception is the currently executed attack which will be always
	 * asked first (so one can continue to execute). If you return
	 * <code>true</code> here you can be assured that this attack will be
	 * executed before any attack registered after this attack will.
	 *
	 * @return if execution is to be forced (infinite weight)
	 */
	@Override
	public boolean forceSelection();
	/**
	 * Gets called at the beginning of execution. This should behave as a setup
	 * method to determine the attack target for example.
	 */
	public void beginExecution();
	/**
	 * Should get called every tick the entity receives to update this attack.
	 */
	public void update();
	/**
	 * The return value determines whether this attack should still be executed.<br>
	 * Returning false should determines the attack instantly, call
	 * {@link #finishExecution()} on this attack, {@link #beginExecution()} on
	 * the new attack and then proceed to the next tick
	 *
	 * @return a value of <code>false</code> halts execution of this attack
	 */
	public boolean shouldContinue();
	/**
	 * Finishes the execution of this attack. This method can also be called
	 * when this attack was terminated unexpectedly, for example if some other
	 * attack did interrupt this attack.<br>
	 * The attack is expected to clean-up and undo actions that were applied
	 * temporarily to the entity like a different attack target.
	 */
	public void finishExecution();
	/**
	 * Returns the mutex bits for the attack.
	 *
	 * @return
	 * @see EntityAIBase#getMutexBits()
	 */
	public byte mutexBits();
	/**
	 * Gets the animation that should be played alongside the executed attack.
	 *
	 * @return
	 */
	public IAnimation getCurrentAnimation();
	/**
	 * Gets the next frame, depending on the state of the attack. For example
	 * running can loop over the running part of the animation, and play wind up
	 * and wind down only when it is needed.
	 *
	 * @param frame
	 *            the current frame, mostly for convenience
	 * @return the next frame in the animation
	 */
	public int getNextFrame(int frame);
}
