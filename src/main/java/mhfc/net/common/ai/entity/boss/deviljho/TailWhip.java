package mhfc.net.common.ai.entity.boss.deviljho;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.general.actions.AIGeneralTailWhip;
import mhfc.net.common.ai.general.provider.simple.ISelectionPredicate;
import mhfc.net.common.entity.monster.EntityDeviljho;
import net.minecraft.entity.Entity;

public class TailWhip extends AIGeneralTailWhip<EntityDeviljho> {

	private static final String ANIMATION = "mhfc:models/Deviljho/tailswipe.mcanm";
	private static final double MAX_DISTANCE = 18F;
	private static final double MIN_DIST = 2f;
	private static final float MIN_RIGHT_ANGLE = 10f;
	private static final int LAST_FRAME = 55; // CLEANUP exact value here please
	private static final float WEIGHT = 5F;

	private static final IDamageCalculator damageCalculator;
	private static final ISelectionPredicate<EntityDeviljho> select;

	static {
		damageCalculator = AIUtils.defaultDamageCalc(145, 50, 9999999f);
		select = new ISelectionPredicate.SelectionAdapter<>(MIN_RIGHT_ANGLE, 180, MIN_DIST, MAX_DISTANCE);
	}

	public TailWhip() {}

	@Override
	public String getAnimationLocation() {
		return ANIMATION;
	}

	@Override
	public int getAnimationLength() {
		return LAST_FRAME;
	}
	
	@Override
	public void update(){
		EntityDeviljho entity = getEntity();
		if(this.getCurrentFrame() == 5)
		entity.playSound("mhfc:deviljho.tailwhip", 2.0F, 1.0F);
	}

	@Override
	public boolean shouldSelectAttack(
			IExecutableAction<? super EntityDeviljho> attack,
			EntityDeviljho actor,
			Entity target) {
		return select.shouldSelectAttack(attack, actor, target);
	}

	@Override
	public float getWeight(EntityDeviljho entity, Entity target) {
		return WEIGHT;
	}

	@Override
	public IDamageCalculator getDamageCalculator() {
		return damageCalculator;
	}

}
