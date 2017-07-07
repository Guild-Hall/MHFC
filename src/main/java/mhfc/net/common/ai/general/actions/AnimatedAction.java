package mhfc.net.common.ai.general.actions;

import java.util.Objects;
import java.util.Random;

import com.github.worldsender.mcanm.common.animation.IAnimation;

import mhfc.net.MHFCMain;
import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.provider.requirements.INeedsAnimations;
import mhfc.net.common.ai.general.provider.simple.IContinuationPredicate;
import mhfc.net.common.ai.general.provider.simple.IFrameAdvancer;
import mhfc.net.common.eventhandler.ai.ActionSelectionEvent;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.common.MinecraftForge;

public abstract class AnimatedAction<T extends EntityCreature> implements IExecutableAction<T>, INeedsAnimations {
	private static final Random rand = new Random();

	private IAnimation animation;
	private int framesPassed;
	private int recentFrame;
	private T entity;
	private IFrameAdvancer frameAdvancer;
	private IContinuationPredicate continuation;
	@SuppressWarnings("unused")
	private int lastFrame = -2; // TO BE ADDED WHERE LAST FRAME WILL BE SET.


	/**
	 * Almost every attack has a target entity. This is completely up to you if you want to use this
	 */
	protected EntityLivingBase target;

	public AnimatedAction() {
	}

	// =========== Final methods, shouldn't be overwritten
	@Override
	public final void beginAction() {
		framesPassed = 0;
		recentFrame = -1;
		target = getEntity().getAttackTarget();
		MinecraftForge.EVENT_BUS.post(new ActionSelectionEvent(this, getEntity()));
		initializeExecutionRandomness();
		beginExecution();
	}

	@Override
	public final void finishAction() {
		finishExecution();
	}

	@Override
	public final void updateAction() {
		if (frameAdvancer != null) {
			int currentFrame = getCurrentFrame();
			int nextFrame = frameAdvancer.getFollowingFrame(currentFrame);
			forceNextFrame(nextFrame);
		} else {
			MHFCMain.logger().debug("#AnimatedAction: frameAdvancer is null on update");
		}
		framesPassed++;
		onUpdate();
	}

	@Override
	public final IAnimation getCurrentAnimation() {
		return this.animation;
	}

	@Override
	public final int getCurrentFrame() {
		return recentFrame;
	}

	@Override
	public final float getWeight() {
		return computeSelectionWeight();
	}

	@Override
	public final boolean shouldContinue() {
		return continuation.shouldContinueAction();
	}

	@Override
	public final void rebind(T entity) {
		this.entity = entity;
		onEntityBind(entity);
	}

	// =========== Utility methods for inheriting classes
	/**
	 * Gets the entity this attack is bounded to (executed on).
	 *
	 * @return
	 */
	protected T getEntity() {
		return entity;
	}

	protected boolean isEffectiveClient() {
		return this.entity != null && this.entity.world.isRemote;
	}

	/**
	 * Retrieves a random to use to generate random numbers
	 *
	 * @return a random
	 */
	protected Random rng() {
		if (entity == null || entity.world == null) {
			return rand;
		}
		return entity.world.rand;
	}

	protected int forceNextFrame(int frame) {
		return recentFrame = frame;
	}

	/**
	 * Returns the number of frames this attack is running, counting only upwards even when the animation loops
	 */
	protected int getFramesPased() {
		return framesPassed;
	}
	// =========== Methods that may be overridden

	@Override
	public boolean forceSelection() {
		return false;
	}

	@Override
	public byte mutexBits() {
		return 7;
	}

	// =========== Lifetime callbacks
	protected void onEntityBind(T entity) {

	}

	protected abstract float computeSelectionWeight();

	/**
	 * This can be overwritten if you want to initialize some e.g. randomness before the provider functions are called.
	 * This is useful to decide on one randomness (aka rng-seed) for this pass.
	 */
	protected void initializeExecutionRandomness() {

	}

	/**
	 * This should be overridden by a subclass if it wants to take actions on begin of the action
	 */
	protected void beginExecution() {
		frameAdvancer = provideFrameAdvancer();
		animation = provideAnimation();
		continuation = provideContinuationPredicate();
		Objects.requireNonNull(frameAdvancer);
		Objects.requireNonNull(animation);
		Objects.requireNonNull(continuation);
	}

	/**
	 *
	 * This should be overridden by a subclass if it wants to take actions on end of the action
	 */
	protected void finishExecution() {

	}

	/**
	 * This must be overridden by the subclass to specify the behavior during execution
	 */
	protected void onUpdate() {

	}

	protected void resetTask(IAnimation currentAnim) {
		currentAnim = null;
	}

}
