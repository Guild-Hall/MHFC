package mhfc.net.common.ai.entity.boss.tigrex;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.general.actions.AIGeneralJumpAttack;
import mhfc.net.common.ai.general.actions.IJumpTimingProvider;
import mhfc.net.common.ai.general.provider.simple.IJumpParamterProvider;
import mhfc.net.common.ai.general.provider.simple.ISelectionPredicate;
import mhfc.net.common.entity.monster.EntityTigrex;
import net.minecraft.entity.Entity;

public class Jump extends AIGeneralJumpAttack<EntityTigrex> {

	private static final String ANIMATION = "mhfc:models/Tigrex/jump.mcanm";
	private static final int LAST_FRAME = 50;
	private static final int JUMP_FRAME = 20;
	private static final float TURN_RATE = 14;
	private static final float JUMP_TIME = 16f;

	private static final IDamageCalculator damageCalc = AIUtils.defaultDamageCalc(45f, 62f, 999999F);
	private static final double MIN_DIST = 6f;
	private static final double MAX_DIST = 60f;
	private static final float MAX_ANGLE = 140f;
	private static final float SELECTION_WEIGHT = 1f;

	private static final ISelectionPredicate<EntityTigrex> pred;
	private static final IJumpParamterProvider<EntityTigrex> params;
	private static final IJumpTimingProvider<EntityTigrex> timing;

	static {
		pred = new ISelectionPredicate.SelectionAdapter<>(-MAX_ANGLE, MAX_ANGLE, MIN_DIST, MAX_DIST);
		params = new IJumpParamterProvider.AttackTargetAdapter<>(JUMP_TIME);
		timing = new IJumpTimingProvider.JumpTimingAdapter<EntityTigrex>(JUMP_FRAME, TURN_RATE, 0);
	}

	public Jump() {

	}
	
	@Override
	public void update(){
		EntityTigrex entity = getEntity();
		if(this.getCurrentFrame() == 10)
		entity.playSound("mhfc:tigrex.leapforward", 2.0F, 1.0F);
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
			IExecutableAction<? super EntityTigrex> attack,
			EntityTigrex actor,
			Entity target) {
		return pred.shouldSelectAttack(attack, actor, target);
	}

	@Override
	public float getWeight(EntityTigrex entity, Entity target) {
		return SELECTION_WEIGHT;
	}

	@Override
	public IDamageCalculator getDamageCalculator() {
		return damageCalc;
	}

	@Override
	public float getInitialUpVelocity(EntityTigrex entity) {
		return params.getInitialUpVelocity(entity);
	}

	@Override
	public float getForwardVelocity(EntityTigrex entity) {
		return params.getForwardVelocity(entity);
	}

	@Override
	public boolean isJumpFrame(EntityTigrex entity, int frame) {
		return timing.isJumpFrame(entity, frame);
	}

	@Override
	public boolean isDamageFrame(EntityTigrex entity, int frame) {
		return timing.isDamageFrame(entity, frame);
	}

	@Override
	public float getTurnRate(EntityTigrex entity, int frame) {
		return timing.getTurnRate(entity, frame);
	}
}
