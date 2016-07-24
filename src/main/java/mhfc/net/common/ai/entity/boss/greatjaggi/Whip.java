package mhfc.net.common.ai.entity.boss.greatjaggi;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.general.actions.AIGeneralTailWhip;
import mhfc.net.common.ai.general.provider.simple.ISelectionPredicate;
import mhfc.net.common.entity.monster.EntityGreatJaggi;
import net.minecraft.entity.Entity;

public class Whip extends AIGeneralTailWhip<EntityGreatJaggi> {

	private static final String ANIMATION = "mhfc:models/GreatJaggi/GreatJaggiTailWhip.mcanm";
	private static final double MAX_DISTANCE = 16F;
	private static final double MIN_DIST = 0f;
	private static final float MIN_RIGHT_ANGLE = 5f;
	private static final int LAST_FRAME = 35; 
	private static final float WEIGHT = 5F;

	private static final IDamageCalculator damageCalculator;
	private static final ISelectionPredicate<EntityGreatJaggi> select;

	static {
		damageCalculator = AIUtils.defaultDamageCalc(85, 50, 9999999f);
		select = new ISelectionPredicate.SelectionAdapter<>(MIN_RIGHT_ANGLE, 180, MIN_DIST, MAX_DISTANCE);
	}

	public Whip() {}
	
	@Override
	protected void update() {
		EntityGreatJaggi entity = getEntity();
		if(this.getCurrentFrame() == 10){
			entity.playSound("greatjaggi.tailwhip", 2.0f, 1.0f);
		}
	}

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
			IExecutableAction<? super EntityGreatJaggi> attack,
			EntityGreatJaggi actor,
			Entity target) {
		return select.shouldSelectAttack(attack, actor, target);
	}

	@Override
	public float getWeight(EntityGreatJaggi entity, Entity target) {
		return WEIGHT;
	}

	@Override
	public IDamageCalculator getDamageCalculator() {
		return damageCalculator;
	}

}
