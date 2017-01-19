package mhfc.net.common.ai.entity.boss.barroth;

import java.util.List;

import mhfc.net.common.ai.entity.AIGameplayComposition;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.SelectionUtils;
import mhfc.net.common.ai.general.actions.DamagingAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.AttackAdapter;
import mhfc.net.common.ai.general.provider.adapters.DamageAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.composite.IAttackProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAttackProvider;
import mhfc.net.common.ai.general.provider.simple.IDamageProvider;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.monster.EntityBarroth;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class Stomp extends DamagingAction<EntityBarroth> implements IHasAttackProvider {
	private static final String ANIMATION_LOCATION = "mhfc:models/Barroth/BarrothStomp.mcanm";
	private static final int LAST_FRAME = 85;

	private static final double MAX_DIST = 9f;
	private static final float WEIGHT = 5;

	private final IAttackProvider ATTACK;
	{
		final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, LAST_FRAME);
		final IDamageProvider DAMAGE = new DamageAdapter(AIUtils.defaultDamageCalc(60f, 50F, 9999999f));
		ATTACK = new AttackAdapter(ANIMATION, DAMAGE);
	}

	private boolean thrown = false;

	public Stomp() {}

	private void updateStomp() {
		EntityBarroth entity = this.getEntity();
		entity.getTurnHelper().updateTargetPoint(target);
		entity.getTurnHelper().updateTurnSpeed(30.0f);
		if (!entity.onGround || thrown || this.getCurrentFrame() < 19) {
			return;
		}
		List<Entity> list = entity.world.getEntitiesWithinAABBExcludingEntity(
				entity,
				entity.getCollisionBoundingBox().expand(8.0D, 1.0D, 8.0D));
		AIGameplayComposition.stompCracks(entity, 150);
		for (Entity entity1 : list) {
			if (!(entity1 instanceof EntityLivingBase)) {
				continue;
			}
			EntityLivingBase living = entity;
			ATTACK.getDamageCalculator().accept(living);
			entity1.attackEntityFrom(DamageSource.causeMobDamage(entity), 30f);
			entity1.addVelocity(0.6D, 0.5D, 0);
		}
		entity.playSound(MHFCSoundRegistry.getRegistry().barrothRam, 1.0F, 1.0F);
		thrown = true;
	}

	@Override
	protected void onUpdate() {
		EntityBarroth entity = this.getEntity();
		target = entity.getAttackTarget();
		updateStomp();
	}

	@Override
	protected void beginExecution() {
		super.beginExecution();
		EntityBarroth entity = getEntity();
		entity.playSound(MHFCSoundRegistry.getRegistry().barrothHeadsmash, 2.0F, 1.0F);
		thrown = false;
	}

	@Override
	public IAttackProvider getAttackProvider() {
		return ATTACK;
	}

	@Override
	protected float computeSelectionWeight() {
		return shouldSelect() ? WEIGHT : DONT_SELECT;
	}

	private boolean shouldSelect() {
		return SelectionUtils.isInDistance(0, MAX_DIST, getEntity(), target);
	}

}
