package mhfc.net.common.ai.entity.boss.barroth;

import java.util.List;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.entity.AIGameplayComposition;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.general.actions.AIAnimatedAction;
import mhfc.net.common.ai.general.provider.simple.ISelectionPredicate;
import mhfc.net.common.entity.monster.EntityBarroth;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class Stomp extends AIAnimatedAction<EntityBarroth> {
	private static final String ANIMATION = "mhfc:models/Barroth/BarrothStomp.mcanm";
	private static final int LAST_FRAME = 85;
	private static final IDamageCalculator damageCalc = AIUtils.defaultDamageCalc(60f, 50F, 9999999f);
	private static final double MAX_DIST = 9f;
	private static final float WEIGHT = 5;

	private static final ISelectionPredicate<EntityBarroth> selectionProvider;

	static {
		selectionProvider = new ISelectionPredicate.DistanceAdapter<>(0, MAX_DIST);
	}

	private boolean thrown = false;

	public Stomp() {}

	private void updateStomp() {
		EntityBarroth entity = this.getEntity();
		entity.getTurnHelper().updateTargetPoint(target);
		entity.getTurnHelper().updateTurnSpeed(30.0f);
		if (!entity.onGround || thrown || this.getCurrentFrame() < 19)
			return;
		@SuppressWarnings("unchecked")
		List<Entity> list = entity.worldObj
				.getEntitiesWithinAABBExcludingEntity(entity, entity.boundingBox.expand(8.0D, 1.0D, 8.0D));
		AIGameplayComposition.stompCracks(entity, 150);
		for (Entity entity1 : list) {
			if (!(entity1 instanceof EntityLivingBase)) {
				continue;
			}
			EntityLivingBase living = (EntityLivingBase) entity;
			damageCalc.accept(living);
			entity1.attackEntityFrom(DamageSource.causeMobDamage(entity), 30f);
			entity1.addVelocity(0.6D, 0.5D, 0);
		}
//		entity.playSound("mhfc:deviljho.stomp", 1.0F, 1.0F);
		thrown = true;
	}

	@Override
	protected void update() {
		EntityBarroth entity = this.getEntity();
		target = entity.getAttackTarget();
		updateStomp();


	}

	@Override
	public void beginExecution() {
		super.beginExecution();
		EntityBarroth entity = getEntity();
		entity.playSound("mhfc:barroth.headsmash", 2.0F, 1.0F);
		thrown = false;
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
			IExecutableAction<? super EntityBarroth> attack,
			EntityBarroth actor,
			Entity target) {
		return selectionProvider.shouldSelectAttack(attack, actor, target);
	}

	@Override
	public float getWeight(EntityBarroth entity, Entity target) {
		return WEIGHT;
	}

}
