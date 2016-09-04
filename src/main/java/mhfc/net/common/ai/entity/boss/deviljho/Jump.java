package mhfc.net.common.ai.entity.boss.deviljho;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.entity.AIGameplayComposition;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.general.actions.AIGeneralJumpAttack;
import mhfc.net.common.ai.general.actions.IJumpTimingProvider;
import mhfc.net.common.ai.general.provider.simple.IJumpParamterProvider;
import mhfc.net.common.ai.general.provider.simple.ISelectionPredicate;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.monster.EntityDeviljho;
import net.minecraft.entity.Entity;

public class Jump extends AIGeneralJumpAttack<EntityDeviljho> {

	private static final String ANIMATION = "mhfc:models/Deviljho/DeviljhoJump.mcanm";
	private static final int FRAMES = 60;
	private static final int JUMPFRAME = 20;
	private static final float TURNRATE = 14;
	private static final float JUMPDURATION = 12f;

	private static final IDamageCalculator DAMAGEBASE = AIUtils.defaultDamageCalc(105f, 2000f, 999999F);
	private static final double DISTANCEMINIMUM = 6F;
	private static final double DISTANCEMAX = 15F;
	private static final float ANGLE_DEGREES = 140f;
	private static final float WEIGHT = 1f;

	private static ISelectionPredicate<EntityDeviljho> set_PREDICATE;
	private static IJumpParamterProvider<EntityDeviljho> set_ADAPTERVAR;
	private static IJumpTimingProvider<EntityDeviljho> set_COUNTIME;

	static {
		set_PREDICATE = new ISelectionPredicate.SelectionAdapter<>(
				-ANGLE_DEGREES,
				ANGLE_DEGREES,
				DISTANCEMINIMUM,
				DISTANCEMAX);
		set_ADAPTERVAR = new IJumpParamterProvider.AttackTargetAdapter<>(JUMPDURATION);
		set_COUNTIME = new IJumpTimingProvider.JumpTimingAdapter<>(JUMPFRAME, TURNRATE, 0);
	}

	public Jump() {
		upwardVelocityCap = 35f;

	}

	private boolean thrown = false;

	@Override
	public void update() {
		EntityDeviljho entity = this.getEntity();
		if (this.getCurrentFrame() == 5) {
			entity.playSound(MHFCSoundRegistry.getRegistry().deviljhoLeap, 2.0F, 1.0F);
		}
		if (!entity.onGround || thrown || this.getCurrentFrame() < 30) {
			return;
		}
		AIGameplayComposition.stompCracks(entity, 200);
		thrown = true;
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
	public boolean shouldSelectAttack(
			IExecutableAction<? super EntityDeviljho> attack,
			EntityDeviljho actor,
			Entity target) {
		return set_PREDICATE.shouldSelectAttack(attack, actor, target);
	}

	@Override
	public float getWeight(EntityDeviljho entity, Entity target) {
		return WEIGHT;
	}

	@Override
	public IDamageCalculator getDamageCalculator() {
		return DAMAGEBASE;
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
