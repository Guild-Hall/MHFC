package mhfc.net.common.ai.general.actions;

import java.util.Objects;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.core.registry.MHFCPotionRegistry;
import mhfc.net.common.entity.type.EntityMHFCBase;
import net.minecraft.entity.Entity;
import net.minecraft.potion.Potion;

public class AIGeneralStunned <EntityT extends EntityMHFCBase<? super EntityT>>
extends
ActionAdapter<EntityT> {
	
	protected IAnimationProvider animation;
	protected IWeightProvider<EntityT> weight;
	
	public AIGeneralStunned(IAnimationProvider Animation, IWeightProvider<EntityT> Weight) {
		animation = Objects.requireNonNull(Animation);
		weight = Objects.requireNonNull(Weight);
		setAnimation(animation.getAnimationLocation());
		setLastFrame(animation.getAnimationLength());
	}
	
	
	@Override
	protected void update() {
		
	}

	@Override
	public float getWeight() {
		EntityT entity = getEntity();
		Entity target = entity.getAttackTarget();
		if(entity.isPotionActive(MHFCPotionRegistry.stun.id)){
			return weight.getWeight(entity, entity);
		}
		return DONT_SELECT;
	}
	

}
