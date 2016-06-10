package mhfc.net.common.ai.entity.rathalos;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.general.actions.AIGeneralAttack;
import mhfc.net.common.ai.general.provider.simple.ISelectionPredicate;
import mhfc.net.common.entity.monster.EntityRathalos;
import net.minecraft.entity.Entity;

public class BiteAttack extends AIGeneralAttack<EntityRathalos> {

	public static final String ANIMATION = "mhfc:models/Rathalos/RathalosBiteLeft.mcanm";
	public static final int LAST_FRAME = 40;
	public static final float WEIGHT = 3.0f;
	public static final float ANGLE = 20f;
	public static final float MAX_DISTANCE = 5f;

	private static final ISelectionPredicate<EntityRathalos> selectionProvider;
	private static final IDamageCalculator damageCalc;

	static {
		selectionProvider = new ISelectionPredicate.SelectionAdapter<>(-ANGLE, ANGLE, 0, MAX_DISTANCE);
		damageCalc = AIUtils.defaultDamageCalc(95f, 50F, 9999999f);
	}

	public BiteAttack() {}

	@Override
	public void update() {}

	@Override
	public String getAnimationLocation() {
		return ANIMATION;
	}

	@Override
	public int getAnimationLength() {
		return LAST_FRAME;
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

	@Override
	public IDamageCalculator getDamageCalculator() {
		return damageCalc;
	}

}
