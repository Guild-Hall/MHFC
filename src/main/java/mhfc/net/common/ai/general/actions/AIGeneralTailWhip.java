package mhfc.net.common.ai.general.actions;

import java.util.Objects;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.IDamageProvider;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.type.EntityMHFCBase;
import net.minecraft.entity.Entity;

public class AIGeneralTailWhip<EntityT extends EntityMHFCBase<? super EntityT>>
	extends
		AIAnimatedAction<EntityT> {

	public static interface ISpinProvider<EntityT extends EntityMHFCBase<? super EntityT>>
		extends
			IAnimatedActionProvider<EntityT>,
			IDamageProvider {

	}

	public static class TailWhipAdapter<EntityT extends EntityMHFCBase<? super EntityT>>
		implements
			ISpinProvider<EntityT> {
		private IAnimationProvider animationProvider;
		private ISelectionPredicate<EntityT> predicate;
		private IWeightProvider<EntityT> weightProvider;
		private IDamageProvider damageProvider;

		public TailWhipAdapter(IAnimationProvider ANIMPROVIDER,
			IWeightProvider<EntityT> WEIGHTPROVIDER,
			IDamageProvider DAMAGEPROVIDER,
			ISelectionPredicate<EntityT> PREDICATE) {
			animationProvider = ANIMPROVIDER;
			damageProvider = DAMAGEPROVIDER;
			weightProvider = Objects.requireNonNull(WEIGHTPROVIDER);
			predicate = Objects.requireNonNull(PREDICATE);

		}

		@Override
		public String getAnimationLocation() {
			return animationProvider.getAnimationLocation();
		}

		@Override
		public int getAnimationLength() {
			return animationProvider.getAnimationLength();
		}

		@Override
		public boolean shouldSelectAttack(
			IExecutableAction<? super EntityT> attack, EntityT actor,
			Entity target) {
			return predicate.shouldSelectAttack(attack, actor, target);
		}

		@Override
		public float getWeight(EntityT entity, Entity target) {
			return weightProvider.getWeight(entity, target);
		}

		@Override
		public IDamageCalculator getDamageCalculator() {
			return damageProvider.getDamageCalculator();
		}

	}

	protected ISpinProvider<EntityT> provider;

	public AIGeneralTailWhip(ISpinProvider<EntityT> PROVIDER) {
		super(PROVIDER);
		this.provider = PROVIDER;
		dmgHelper.setDamageCalculator(provider.getDamageCalculator());
		setAnimation(provider.getAnimationLocation());
		setLastFrame(provider.getAnimationLength());
	}

	@Override
	public void beginExecution() {
		super.beginExecution();
		getEntity().getNavigator().clearPathEntity();
	}

	@Override
	protected void update() {
		AIUtils.damageCollidingEntities(this.getEntity(), dmgHelper
			.getCalculator());
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
