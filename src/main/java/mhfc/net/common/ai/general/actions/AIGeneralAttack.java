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

public class AIGeneralAttack<EntityT extends EntityMHFCBase<? super EntityT>> extends AIAnimatedAction<EntityT> {

	public static interface IAttackProvider<EntityT extends EntityMHFCBase<? super EntityT>>
			extends
			IAnimatedActionProvider<EntityT>,
			IDamageProvider {

	}

	public static class AttackAdapter<EntityT extends EntityMHFCBase<? super EntityT>>
			implements
			IAttackProvider<EntityT> {
		protected IAnimationProvider animationProvider;
		protected ISelectionPredicate<EntityT> predicate;
		protected IWeightProvider<EntityT> weightProvider;
		protected IDamageProvider damageProvider;

		public AttackAdapter(
				IAnimationProvider animProvider,
				IWeightProvider<EntityT> weightProvider,
				ISelectionPredicate<EntityT> predicate,
				IDamageProvider damageProvider) {
			this.animationProvider = Objects.requireNonNull(animProvider);
			this.damageProvider = Objects.requireNonNull(damageProvider);
			this.weightProvider = Objects.requireNonNull(weightProvider);
			this.predicate = Objects.requireNonNull(predicate);

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
		public boolean shouldSelectAttack(IExecutableAction<? super EntityT> attack, EntityT actor, Entity target) {
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

	protected IAttackProvider<EntityT> provider;

	public AIGeneralAttack(IAttackProvider<EntityT> provider) {
		super(provider);
		this.provider = provider;
		dmgHelper.setDamageCalculator(provider.getDamageCalculator());
	}

	@Override
	protected void beginExecution() {
		super.beginExecution();
		dmgHelper.reset();
	}

	@Override
	protected void update() {
		AIUtils.damageCollidingEntities(this.getEntity(), dmgHelper.getCalculator());
	}

}
