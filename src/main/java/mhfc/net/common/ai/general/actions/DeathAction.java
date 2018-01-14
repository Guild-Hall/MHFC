package mhfc.net.common.ai.general.actions;

import mhfc.net.common.ai.general.provider.requirements.INeedsDeathSound;
import mhfc.net.common.ai.general.provider.simple.IContinuationPredicate;
import net.minecraft.entity.EntityCreature;

public abstract class DeathAction<T extends EntityCreature> extends AnimatedAction<T> implements INeedsDeathSound {

	public static final int deathLingeringTicks = 40 * 20;

	public DeathAction() {}

	@Override
	protected float computeSelectionWeight() {
		return DONT_SELECT;
	}

	@Override
	protected void beginExecution() {
		super.beginExecution();
		getEntity().setAttackTarget(null);
		getEntity().playSound(provideDeathSound(), 4.0F, 1.0F);
	}

	@Override
	protected void onUpdate() {}

	@Override
	public boolean forceSelection() {
		T entity = getEntity();
		return entity == null ? false : entity.isDead;
	}

	@Override
	public IContinuationPredicate provideContinuationPredicate() {
		return () -> true;
	}
}
