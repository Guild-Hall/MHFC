package mhfc.net.common.ai.entity.boss.rathalos;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.general.actions.AIGeneralTailWhip;
import mhfc.net.common.ai.general.provider.simple.ISelectionPredicate;
import mhfc.net.common.entity.monster.EntityRathalos;
import net.minecraft.entity.Entity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class TailWhip extends AIGeneralTailWhip<EntityRathalos> {

	private static final String ANIMATION = "mhfc:models/Rathalos/RathalosTailSwipeRight.mcanm";
	private static final int LAST_FRAME = 40;
	private static final double MAX_DISTANCE = 15F;
	private static final double MIN_DIST = 0f;
	private static final float MIN_RIGHT_ANGLE = 10f;
	private static final float WEIGHT = 5;

	private static final IDamageCalculator damageCalc;
	private static final ISelectionPredicate<EntityRathalos> pred;

	static {
		damageCalc = AIUtils.defaultDamageCalc(102, 156, 9999999f);
		pred = new ISelectionPredicate.SelectionAdapter<>(MIN_RIGHT_ANGLE, 180, MIN_DIST, MAX_DISTANCE);
	}

	public TailWhip() {}
	
	
	@Override
	public void update(){
		EntityRathalos entity = getEntity();
		if(this.getCurrentFrame() == 12)
		entity.playSound("mhfc:rathalos.tailwhip", 2.0F, 1.0F);
		
		if(this.getCurrentFrame() == 20){
			if(entity.getRNG().nextInt() == 1){
				target.addPotionEffect(new PotionEffect(Potion.poison.id, 60, 5));
			}
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
			IExecutableAction<? super EntityRathalos> attack,
			EntityRathalos actor,
			Entity target) {
		return pred.shouldSelectAttack(attack, actor, target);
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
