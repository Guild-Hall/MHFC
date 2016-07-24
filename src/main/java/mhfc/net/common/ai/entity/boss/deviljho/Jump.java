package mhfc.net.common.ai.entity.boss.deviljho;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.entity.AIGameplayComposition;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.general.actions.AIGeneralJumpAttack;
import mhfc.net.common.ai.general.actions.IJumpTimingProvider;
import mhfc.net.common.ai.general.provider.simple.IJumpParamterProvider;
import mhfc.net.common.ai.general.provider.simple.ISelectionPredicate;
import mhfc.net.common.entity.monster.EntityDeviljho;
import net.minecraft.entity.Entity;

public class Jump extends AIGeneralJumpAttack<EntityDeviljho> {

	private static final String set_ANIMATION = "mhfc:models/Deviljho/DeviljhoJump.mcanm";
	private static final int set_FRAME = 60;
	private static final int set_JUMPFRAME = 20;
	private static final float set_TURNRATE = 14;
	private static final float set_JUMPDURATION = 12f;

	private static final IDamageCalculator set_DAMAGEBASE = AIUtils.defaultDamageCalc(105f, 2000f, 999999F);
	private static final double set_DISTANCEMINIMUM = 6F;
	private static final double set_DISTANCEMAX = 15F;
	private static final float set_ANGLETHETA = 140f;
	private static final float set_ARITHMETICWEIGHT = 1f;

	private static ISelectionPredicate<EntityDeviljho> set_PREDICATE;
	private static IJumpParamterProvider<EntityDeviljho> set_ADAPTERVAR;
	private static IJumpTimingProvider<EntityDeviljho> set_COUNTIME;

	static {
		set_PREDICATE = new ISelectionPredicate.SelectionAdapter<>(
				-set_ANGLETHETA,
				set_ANGLETHETA,
				set_DISTANCEMINIMUM,
				set_DISTANCEMAX);
		set_ADAPTERVAR = new IJumpParamterProvider.AttackTargetAdapter<>(set_JUMPDURATION);
		set_COUNTIME = new IJumpTimingProvider.JumpTimingAdapter<EntityDeviljho>(set_JUMPFRAME, set_TURNRATE, 0);
	}

	public Jump() {
		upwardVelocityCap = 35f;
		
	}
	
	
	private boolean thrown = false;
	
	@Override
	public void update() { 
		EntityDeviljho entity = this.getEntity();
		if(this.getCurrentFrame() == 5){
		
		entity.playSound("mhfc:deviljho.leap", 2.0F, 1.0F);
		}
		if (!entity.onGround || thrown || this.getCurrentFrame() < 30)
			return;
		AIGameplayComposition.stompCracks(entity, 200);
		thrown = true;
	}

	@Override
	public String getAnimationLocation() {
		return set_ANIMATION;
	}

	@Override
	public int getAnimationLength() {
		return set_FRAME;
	}

	@Override
	public boolean shouldSelectAttack(
			IExecutableAction<? super EntityDeviljho> attack,
			EntityDeviljho actor,
			Entity target) {
		return set_PREDICATE.shouldSelectAttack(attack, actor, target);
	}

	@Override
	public float getWeight(EntityDeviljho entity, Entity target) {
		return set_ARITHMETICWEIGHT;
	}

	@Override
	public IDamageCalculator getDamageCalculator() {
		return set_DAMAGEBASE;
	}

	@Override
	public float getInitialUpVelocity(EntityDeviljho entity) {
		return set_ADAPTERVAR.getInitialUpVelocity(entity);
	}

	@Override
	public float getForwardVelocity(EntityDeviljho entity) {
		return set_ADAPTERVAR.getForwardVelocity(entity);
	}

	@Override
	public boolean isJumpFrame(EntityDeviljho entity, int frame) {
		return set_COUNTIME.isJumpFrame(entity, frame);
	}

	@Override
	public boolean isDamageFrame(EntityDeviljho entity, int frame) {
		return set_COUNTIME.isDamageFrame(entity, frame);
	}

	@Override
	public float getTurnRate(EntityDeviljho entity, int frame) {
		return set_COUNTIME.getTurnRate(entity, frame);
	}
}
