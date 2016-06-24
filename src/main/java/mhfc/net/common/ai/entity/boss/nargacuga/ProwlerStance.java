package mhfc.net.common.ai.entity.boss.nargacuga;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.actions.AIAnimatedAction;
import mhfc.net.common.ai.general.provider.simple.ISelectionPredicate;
import mhfc.net.common.entity.monster.EntityNargacuga;
import net.minecraft.entity.Entity;

public class ProwlerStance extends AIAnimatedAction<EntityNargacuga> {

	private static final String ANIMATION_LOCATION = "mhfc:models/Nargacuga/Pounce.mcanm";
	private static final int ANIMATION_LENGTH = 18;
	private static final float MAX_ANGLE = 40;
	private static final float MAX_DISTANCE = 40;
	private static final float WEIGHT = 15;

	private static final ISelectionPredicate<EntityNargacuga> select;

	static {
		select = new ISelectionPredicate.SelectionAdapter<>(-MAX_ANGLE, MAX_ANGLE, 0, MAX_DISTANCE);
	}

	public ProwlerStance() {}

	@Override
	protected void update() {}

	@Override
	public String getAnimationLocation() {
		return ANIMATION_LOCATION;
	}

	@Override
	public int getAnimationLength() {
		return ANIMATION_LENGTH;
	}

	@Override
	public boolean shouldSelectAttack(
			IExecutableAction<? super EntityNargacuga> attack,
			EntityNargacuga actor,
			Entity target) {
		return select.shouldSelectAttack(attack, actor, target);
	}

	@Override
	public float getWeight(EntityNargacuga entity, Entity target) {
		return WEIGHT;
	}

}
