package mhfc.net.common.ai.entity.boss.lagiacrus;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.actions.AIAnimatedAction;
import mhfc.net.common.ai.general.provider.simple.ISelectionPredicate;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.monster.EntityLagiacrus;
import net.minecraft.entity.Entity;

public class Beam extends AIAnimatedAction<EntityLagiacrus> {

	private static final String ANIMATION = "mhfc:models/Lagiacrus/LagiacrusBeam.mcanm";
	private static final int FRAMES = 100;
	private static final double MAXDISTANCE = 10f;
	private static final float WEIGHT = 6F;
	private static ISelectionPredicate<EntityLagiacrus> selectionProvider = new ISelectionPredicate.DistanceAdapter<>(
			0,
			MAXDISTANCE);

	public Beam() {}

	@Override
	protected void beginExecution() {
		EntityLagiacrus set_Entity = this.getEntity();
		set_Entity.playSound(MHFCSoundRegistry.getRegistry().lagiacrusBeam, 2.0F, 1.0F);
	}

	@Override
	public String getAnimationLocation() {
		return ANIMATION;
	}

	@Override
	public int getAnimationLength() {
		return FRAMES;
	}

	@Override
	public float getWeight(EntityLagiacrus entity, Entity target) {
		return WEIGHT;
	}

	@Override
	public boolean shouldSelectAttack(
			IExecutableAction<? super EntityLagiacrus> attack,
			EntityLagiacrus actor,
			Entity target) {
		return selectionProvider.shouldSelectAttack(attack, actor, target);
	}

	@Override
	protected void update() {

	}

}
