package mhfc.net.common.ai.general.actions;

import java.util.List;
import java.util.Objects;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.general.actions.AIGeneralJumpAttack.IJumpProvider;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.IDamageProvider;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.util.world.WorldHelper;

public class AIGeneralTailWhip <EntityT extends EntityMHFCBase<? super EntityT>>
	extends
		ActionAdapter<EntityT> {
	
	public static interface ISpinProvider<EntityT extends EntityMHFCBase<? super EntityT>>
	extends
		IAnimationProvider,
		ISelectionPredicate<EntityT>,
		IWeightProvider<EntityT>,
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
				IDamageProvider DAMAGEPROVIDER, ISelectionPredicate<EntityT> PREDICATE){
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
		public boolean shouldSelectAttack(IExecutableAction<EntityT> attack,
				EntityT actor, Entity target) {
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
	
	public AIGeneralTailWhip(ISpinProvider<EntityT> PROVIDER){
		this.provider = PROVIDER;
		dmgHelper.setDamageCalculator(provider.getDamageCalculator());
		setAnimation(provider.getAnimationLocation());
		setLastFrame(provider.getAnimationLength());
	}
	private static final double MAX_ANGLE_DOT = -0.2;
	private static final double MAX_DISTANCE = 7d;
	
	@Override
	public void beginExecution() {
		getEntity().getNavigator().clearPathEntity();
	}
	
	@Override
	protected void update() {
		EntityT entity = this.getEntity();
		List<Entity> collidingEntities = WorldHelper.collidingEntities(entity);
		for (Entity trgt : collidingEntities) {
			trgt.attackEntityFrom(DamageSource.causeMobDamage(entity),
				dmgHelper.getCalculator().accept(trgt));
			}
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
