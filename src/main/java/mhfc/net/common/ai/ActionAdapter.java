package mhfc.net.common.ai;

import java.util.Random;

import mhfc.net.common.ai.general.AIUtils.DamageCalculatorHelper;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import com.github.worldsender.mcanm.client.model.mcanmmodel.animation.IAnimation;
import com.github.worldsender.mcanm.client.model.mcanmmodel.animation.stored.AnimationRegistry;

public abstract class ActionAdapter<T extends EntityCreature>
	implements
		IExecutableAction<T> {
	private static final Random rand = new Random();

	private IAnimation animation;
	private int framesPassed;
	private int recentFrame;
	private int lastFrame = -2;
	private T entity;

	/**
	 * Almost every attack has a target entity. This is completely up to you if
	 * you want to use this
	 */
	protected EntityLivingBase target;
	protected DamageCalculatorHelper dmgHelper;

	public ActionAdapter() {
		dmgHelper = new DamageCalculatorHelper();
	}

	@Override
	public void beginAction() {
		framesPassed = 0;
		recentFrame = -1;
		beginExecution();
	}

	@Override
	public void finishAction() {
		finishExecution();
	}

	@Override
	public void updateAction() {
		setToNextFrame(getCurrentFrame() + 1);
		framesPassed++;
		update();
	}

	/**
	 * This should be overridden by a subclass if it wants to take actions on
	 * begin of the action
	 */
	protected void beginExecution() {

	}

	/**
	 * 
	 * This should be overridden by a subclass if it wants to take actions on
	 * end of the action
	 */
	protected void finishExecution() {

	}

	/**
	 * This must be overridden by the subclass to specify the behavior during
	 * execution
	 */
	protected abstract void update();

	@Override
	public abstract float getWeight();

	/**
	 * Gets the entity this attack is bounded to (executed on).
	 *
	 * @return
	 */
	protected T getEntity() {
		return entity;
	}

	/**
	 * Retrieves a random to use to generate random numbers
	 *
	 * @return a random
	 */
	protected Random rng() {
		if (entity == null || entity.worldObj == null)
			return rand;
		return entity.worldObj.rand;
	}

	/**
	 * Sets the animation of this attack (to be used in the constructor)
	 *
	 * @param anim
	 */
	protected void setAnimation(IAnimation anim) {
		this.animation = anim;
	}

	protected void setAnimation(ResourceLocation resLoc) {
		this.animation = AnimationRegistry.loadAnimation(resLoc);
	}

	protected void setAnimation(String resLoc) {
		this.animation = AnimationRegistry.loadAnimation(new ResourceLocation(
			resLoc));
	}

	protected void setLastFrame(int lastFrame) {
		this.lastFrame = lastFrame;
	}

	@Override
	public IAnimation getCurrentAnimation() {
		return this.animation;
	}

	@Override
	public void rebind(T entity) {
		this.entity = entity;

	}

	@Override
	public boolean forceSelection() {
		return false;
	}

	@Override
	public byte mutexBits() {
		return 7;
	}

	public int setToNextFrame(int frame) {
		return recentFrame = frame;
	}

	@Override
	public int getCurrentFrame() {
		return recentFrame;
	};

	@Override
	public boolean shouldContinue() {
		return getCurrentFrame() < lastFrame;
	}

}
