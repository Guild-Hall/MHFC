package mhfc.net.common.ai.entity.tigrex;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.general.actions.AIGeneralTailWhip;
import mhfc.net.common.ai.general.provider.simple.ISelectionPredicate;
import mhfc.net.common.entity.monster.EntityTigrex;
import net.minecraft.entity.Entity;

public class TigrexWhip extends AIGeneralTailWhip<EntityTigrex> {

	private static final String ANIMATION = "mhfc:models/Tigrex/tailswipe.mcanm";
	private static final int LAST_FRAME = 70; // CLEANUP exact value here please
	private static final double MAX_DISTANCE = 7F;
	private static final double MIN_DIST = 0f;
	private static final float MIN_RIGHT_ANGLE = 10f;
	private static final float WEIGHT = 5;

	private static final IDamageCalculator damageCalc;
	private static final ISelectionPredicate<EntityTigrex> pred;

	static {
		damageCalc = AIUtils.defaultDamageCalc(94, 92, 9999999f);
		pred = new ISelectionPredicate.SelectionAdapter<>(MIN_RIGHT_ANGLE, 180, MIN_DIST, MAX_DISTANCE);
	}

	public TigrexWhip() {}

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
			IExecutableAction<? super EntityTigrex> attack,
			EntityTigrex actor,
			Entity target) {
		return pred.shouldSelectAttack(attack, actor, target);
	}

	@Override
	public float getWeight(EntityTigrex entity, Entity target) {
		return WEIGHT;
	}

	@Override
	public IDamageCalculator getDamageCalculator() {
		return damageCalc;
	}

}
