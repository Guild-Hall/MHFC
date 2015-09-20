package mhfc.net.common.ai.general.actions;

import java.util.Objects;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import net.minecraft.entity.EntityCreature;

public abstract class AIAnimatedAction<EntityT extends EntityCreature>
	extends
		ActionAdapter<EntityT> {

	public static interface IAnimatedActionProvider<EntityT extends EntityCreature>
		extends
			IAnimationProvider,
			ISelectionPredicate<EntityT>,
			IWeightProvider<EntityT> {
	}

	private IAnimatedActionProvider<EntityT> provider;

	public AIAnimatedAction(IAnimatedActionProvider<EntityT> provider) {
		this.provider = Objects.requireNonNull(provider);
		setAnimation(provider.getAnimationLocation());
		setLastFrame(provider.getAnimationLength());
	}

	@Override
	public float getWeight() {
		EntityT entity = this.getEntity();
		target = entity.getAttackTarget();
		if (provider.shouldSelectAttack(this, entity, target)) {
			return provider.getWeight(entity, target);
		} else {
			return DONT_SELECT;
		}
	}

}
