package mhfc.net.common.ai.entity.rathalos;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.IFrameAdvancer.SwitchLoopAdvancer;
import mhfc.net.common.ai.general.actions.AIAnimatedAction;
import mhfc.net.common.ai.general.provider.simple.ISelectionPredicate;
import mhfc.net.common.entity.monster.EntityRathalos;
import mhfc.net.common.entity.monster.EntityRathalos.Stances;
import net.minecraft.entity.Entity;

public class FlyLand extends AIAnimatedAction<EntityRathalos> {

	private static final String ANIMATION = "mhfc:models/Rathalos/RathalosFlightHover.mcanm";
	private static final float WEIGHT = 1.0f;
	private static final int FLAP_LAST_FRAME = 20;
	private static final int LAND_LAST_FRAME = 50;

	private static ISelectionPredicate<EntityRathalos> selectionProvider;

	protected boolean hasTouchedDown;
	protected SwitchLoopAdvancer animationStep;

	static {
		selectionProvider = new ISelectionPredicate<EntityRathalos>() {
			@Override
			public boolean shouldSelectAttack(
					IExecutableAction<? super EntityRathalos> attack,
					EntityRathalos actor,
					Entity target) {
				return actor.getStance() == Stances.FLYING;
			}
		};
	}

	public FlyLand() {
		this.animationStep = new SwitchLoopAdvancer(0, FLAP_LAST_FRAME);
		setFrameAdvancer(animationStep);
	}

	@Override
	public void beginExecution() {
		super.beginExecution();
		hasTouchedDown = false;
	}

	@Override
	public void update() {
		if (!hasTouchedDown) {
			EntityRathalos entity = getEntity();
			entity.motionY = Math.max(entity.motionY, -1);
			if (!entity.isAirBorne) {
				hasTouchedDown = true;
				entity.setStance(Stances.GROUND);
				animationStep.setLoopActive(false);
			}
		}
	}

	@Override
	public boolean shouldContinue() {
		return !hasTouchedDown || super.shouldContinue();
	}

	@Override
	public String getAnimationLocation() {
		return ANIMATION;
	}

	@Override
	public int getAnimationLength() {
		return LAND_LAST_FRAME;
	}

	@Override
	public boolean shouldSelectAttack(
			IExecutableAction<? super EntityRathalos> attack,
			EntityRathalos actor,
			Entity target) {
		return selectionProvider.shouldSelectAttack(attack, actor, target);
	}

	@Override
	public float getWeight(EntityRathalos entity, Entity target) {
		return WEIGHT;
	}

}
