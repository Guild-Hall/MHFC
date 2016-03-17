package mhfc.net.common.ai.general.provider.composite;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.provider.simple.IAnimationProvider;
import mhfc.net.common.ai.general.provider.simple.IRoarSoundProvider;
import mhfc.net.common.ai.general.provider.simple.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.simple.IWeightProvider;
import mhfc.net.common.entity.type.EntityMHFCBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public interface IRoarProvider<EntityT extends EntityMHFCBase<? super EntityT>>
		extends
		IAnimatedActionProvider<EntityT>,
		IRoarSoundProvider {

	class RoarAdapter<EntityT extends EntityMHFCBase<? super EntityT>> implements IRoarProvider<EntityT> {
		private IAnimationProvider animationProvider;
		private ISelectionPredicate<EntityT> selectionProvider;
		private IWeightProvider<EntityT> weightProvider;
		private IRoarSoundProvider roarSoundProvider;
		private boolean stunTargets;

		public RoarAdapter(
				IAnimationProvider animation,
				ISelectionPredicate<EntityT> selection,
				IWeightProvider<EntityT> weight,
				IRoarSoundProvider roarSoundFile,
				boolean shouldStun) {
			animationProvider = animation;
			selectionProvider = selection;
			weightProvider = weight;
			roarSoundProvider = roarSoundFile;
			stunTargets = shouldStun;
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
			return selectionProvider.shouldSelectAttack(attack, actor, target);
		}

		@Override
		public float getWeight(EntityT entity, Entity target) {
			return weightProvider.getWeight(entity, target);
		}

		@Override
		public String getRoarSoundLocation() {
			return roarSoundProvider.getRoarSoundLocation();
		}

		@Override
		public boolean shouldStun(EntityLivingBase actor) {
			return stunTargets;
		}
	}

	public boolean shouldStun(EntityLivingBase actor);

}
