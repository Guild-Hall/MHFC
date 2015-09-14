package mhfc.net.common.ai.general.actions;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.type.EntityMHFCBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class AIGeneralRoar<EntityT extends EntityMHFCBase<? super EntityT>>
	extends
		ActionAdapter<EntityT> {

	public static interface IRoarProvider<EntityT extends EntityMHFCBase<? super EntityT>>
		extends
			IAnimationProvider,
			ISelectionPredicate<EntityT>,
			IWeightProvider<EntityT> {

		public String getRoarSoundLocation();

	}

	public static class RoarAdapter<EntityT extends EntityMHFCBase<? super EntityT>>
		implements
			IRoarProvider<EntityT> {
		private IAnimationProvider animationProvider;
		private ISelectionPredicate<EntityT> selectionProvider;
		private IWeightProvider<EntityT> weightProvider;
		private String roarSoundFileLocation;

		public RoarAdapter(IAnimationProvider animation,
			ISelectionPredicate<EntityT> selection,
			IWeightProvider<EntityT> weight, String roarSoundFile) {
			animationProvider = animation;
			selectionProvider = selection;
			weightProvider = weight;
			roarSoundFileLocation = roarSoundFile;
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
			return selectionProvider.shouldSelectAttack(attack, actor, target);
		}

		@Override
		public float getWeight(EntityT entity, Entity target) {
			return weightProvider.getWeight(entity, target);
		}

		@Override
		public String getRoarSoundLocation() {
			return roarSoundFileLocation;
		}
	}

	private Collection<EntityPlayer> affectedEntities;
	private IRoarProvider<EntityT> roarProvider;

	public AIGeneralRoar(IRoarProvider<EntityT> provider) {
		setAnimation(provider.getAnimationLocation());
		setLastFrame(provider.getAnimationLength());
		affectedEntities = new HashSet<EntityPlayer>();
		roarProvider = provider;
	}

	@Override
	public void beginExecution() {
		affectedEntities.clear();
		getEntity().playSound(roarProvider.getRoarSoundLocation(), 1.0F, 1.0F);
	}

	@Override
	protected void update() {
		EntityT roaringEntity = getEntity();
		@SuppressWarnings("unchecked")
		List<Entity> list = roaringEntity.worldObj
			.getEntitiesWithinAABBExcludingEntity(roaringEntity,
				roaringEntity.boundingBox.expand(16.0D, 8.0D, 16.0D));

		for (Entity affectedEntity : list) {
			if (!affectedEntities.contains(affectedEntity)
				&& affectedEntity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) affectedEntity;
				player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id,
					80, 10));
				player.addPotionEffect(new PotionEffect(Potion.digSlowdown.id,
					80, 10));
				affectedEntities.add(player);
			}
		}
	}

	@Override
	public float getWeight() {
		if (roarProvider.shouldSelectAttack(this, getEntity(), getEntity()
			.getAttackTarget()))
			return roarProvider.getWeight(getEntity(), getEntity()
				.getAttackTarget());
		else
			return DONT_SELECT;
	}

}
