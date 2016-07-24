package mhfc.net.common.ai.entity.boss.deviljho;

import java.util.List;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.entity.AIGameplayComposition;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.general.actions.AIAnimatedAction;
import mhfc.net.common.ai.general.provider.simple.ISelectionPredicate;
import mhfc.net.common.entity.monster.EntityDeviljho;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class Stomp extends AIAnimatedAction<EntityDeviljho> {
	private static final String ANIMATION = "mhfc:models/Deviljho/DeviljhoStomp.mcanm";
	private static final int LAST_FRAME = 55;
	private static final IDamageCalculator damageCalc = AIUtils.defaultDamageCalc(60f, 50F, 9999999f);
	private static final double MAX_DIST = 9f;
	private static final float WEIGHT = 7;

	private static final ISelectionPredicate<EntityDeviljho> selectionProvider;

	static {
		selectionProvider = new ISelectionPredicate.DistanceAdapter<>(0, MAX_DIST);
	}

	private boolean thrown = false;

	public Stomp() {}

	private void updateStomp() {
		boolean CamShake = false;
		float CamShakeIntensity;
		EntityDeviljho entity = this.getEntity();
		if (!entity.onGround || thrown || this.getCurrentFrame() < 26) {
			return;
		}
		List<Entity> list = entity.worldObj.getEntitiesWithinAABBExcludingEntity(entity, entity.boundingBox.expand(10.0D, 1.0D, 10.0D));
		AIGameplayComposition.stompCracks(entity, 100);
		for (Entity entity1 : list) {
			if (!(entity1 instanceof EntityLivingBase)) {
				continue;
			}
			float Intenstity = 40;
			CamShake = (CamShake == false) ? true : false;
			CamShakeIntensity = (CamShake) ? Intenstity : -Intenstity;
			entity1.setAngles(0, 40);
			EntityLivingBase living = entity;
			damageCalc.accept(living);
			entity1.attackEntityFrom(DamageSource.causeMobDamage(entity), 60f);
			entity1.addVelocity(0.2, 0.3, 0);
		}
		entity.playSound("mhfc:deviljho.stomp", 1.0F, 1.0F);
		thrown = true;
	}

	@Override
	protected void update() {
		EntityDeviljho entity = this.getEntity();
		target = entity.getAttackTarget();
		updateStomp();

		if (isMoveForwardFrame(getCurrentFrame())) {
			EntityDeviljho e = getEntity();
			e.moveForward(1, false);
		}

	}

	@Override
	public void beginExecution() {
		super.beginExecution();
		thrown = false;
	}

	private boolean isMoveForwardFrame(int frame) {
		return (frame > 20 && frame < 30);
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
			IExecutableAction<? super EntityDeviljho> attack,
			EntityDeviljho actor,
			Entity target) {
		return selectionProvider.shouldSelectAttack(attack, actor, target);
	}

	@Override
	public float getWeight(EntityDeviljho entity, Entity target) {
		return WEIGHT;
	}

}
