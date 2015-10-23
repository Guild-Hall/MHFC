package mhfc.net.common.ai.general.actions;

import java.util.Objects;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.type.EntityMHFCBase;
import net.minecraft.entity.Entity;

public class AIGeneralIdle<EntityT extends EntityMHFCBase<? super EntityT>>
	extends
		ActionAdapter<EntityT> {

	protected IAnimationProvider animation;
	protected IWeightProvider<EntityT> weight;
	protected ISelectionPredicate<EntityT> selectOnIdle;

	public AIGeneralIdle(IAnimationProvider animation,
		IWeightProvider<EntityT> weight) {
		this.animation = Objects.requireNonNull(animation);
		this.weight = Objects.requireNonNull(weight);
		selectOnIdle = new ISelectionPredicate.SelectIdleAdapter<>();
		setAnimation(animation.getAnimationLocation());
		setLastFrame(animation.getAnimationLength());
	}

	@Override
	public void beginAction() {
		getEntity().playLivingSound();
	}

	@Override
	public float getWeight() {
		EntityT entity = getEntity();
		Entity target = entity.getAttackTarget();
		if (selectOnIdle.shouldSelectAttack(this, entity, target)) {
			return weight.getWeight(entity, target);
		}
		return DONT_SELECT;
	}

	@Override
	protected void update() {
	} // do nothing, we idle, remember?

}
