package mhfc.net.common.ai;

import java.util.Random;

import mhfc.net.common.ai.AIUtils.DamageCalculatorHelper;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import com.github.worldsender.mcanm.client.model.mcanmmodel.animation.IAnimation;
import com.github.worldsender.mcanm.client.model.mcanmmodel.animation.stored.AnimationRegistry;

public abstract class AttackAdapter<T extends EntityCreature>
	implements
		IExecutableAttack<T> {
	private static final Random rand = new Random();

	private IAnimation animation;
	private int recentFrame;
	private int lastFrame = -2;
	private T entity;
	/**
	 * Almost every attack has a target entity. This is completely up to you if
	 * you want to use this
	 */
	protected EntityLivingBase target;
	protected DamageCalculatorHelper dmgHelper;

	public AttackAdapter() {
		dmgHelper = new DamageCalculatorHelper();
	}

	@Override
	public void beginExecution() {
		recentFrame = -1;
	}

	@Override
	public void finishExecution() {
	}

	@Override
	public void update() {
		setToNextFrame(getCurrentFrame());
	}

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
	 * Gets the last recent frame, set everytime {@link #setToNextFrame(int frame)}
	 * is called to <code>frame+1</code>
	 *
	 * @return the most recently returned frame
	 */
	protected int getRecentFrame() {
		return recentFrame;
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
		return recentFrame = ++frame;
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
