package mhfc.net.common.ai.entity.boss.lagiacrus;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.actions.AIAnimatedAction;
import mhfc.net.common.ai.general.provider.simple.ISelectionPredicate;
import mhfc.net.common.entity.monster.EntityLagiacrus;
import net.minecraft.entity.Entity;

public class Beam extends AIAnimatedAction<EntityLagiacrus> {
	
	private static final String set_Animation = "mhfc:models/Lagiacrus/LagiacrusBeam.mcanm";
	private static final int set_Frame = 100;
	private static final double set_MaxDistance = 10f;
	private static final float set_Weight = 6F;
	private static ISelectionPredicate<EntityLagiacrus> selectionProvider;
	
	public Beam(){}

	static {
		selectionProvider = new ISelectionPredicate.DistanceAdapter<>(0, set_MaxDistance);
	}
	
	@Override
	protected void beginExecution() {
		EntityLagiacrus set_Entity = this.getEntity();
		set_Entity.playSound("mhfc:lagiacrus.discharge", 2.0F, 1.0F);
	}
	
	@Override
	public String getAnimationLocation() {
		return set_Animation;
	}

	@Override
	public int getAnimationLength() {
		return set_Frame;
	}

	@Override
	public float getWeight(EntityLagiacrus entity, Entity target) {
		return set_Weight;
	}

	@Override
	public boolean shouldSelectAttack(IExecutableAction<? super EntityLagiacrus> attack, EntityLagiacrus actor,
			Entity target) {
		return selectionProvider.shouldSelectAttack(attack, actor, target);
	}

	@Override
	protected void update() {
		
	}

	

}
