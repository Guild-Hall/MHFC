package mhfc.net.common.ai.entity.monsters.deviljho;

import java.util.List;

import mhfc.net.common.ai.entity.EntityAIMethods;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.SelectionUtils;
import mhfc.net.common.ai.general.actions.DamagingAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.AttackAdapter;
import mhfc.net.common.ai.general.provider.adapters.DamageAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.composite.IAttackProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAttackProvider;
import mhfc.net.common.ai.general.provider.simple.IDamageCalculator;
import mhfc.net.common.ai.general.provider.simple.IDamageProvider;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.monster.EntityDeviljho;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

public class Stomp extends DamagingAction<EntityDeviljho> implements IHasAttackProvider {
	private static final int LAST_FRAME = 55;
	private static final String ANIMATION_LOCATION = "mhfc:models/Deviljho/DeviljhoStomp.mcanm";

	private static final IDamageCalculator DAMAGE_CALC = AIUtils.defaultDamageCalc(60f, 50F, 9999999f);
	private static final double MAX_DIST = 9f;
	private static final float WEIGHT = 7;

	private final IAttackProvider ATTACK;
	{
		IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, LAST_FRAME);
		IDamageProvider DAMAGE = new DamageAdapter(DAMAGE_CALC);
		ATTACK = new AttackAdapter(ANIMATION, DAMAGE);
	}
	private boolean thrown = false;

	public Stomp() {}

	@Override
	protected float computeSelectionWeight() {
		EntityDeviljho entity = this.getEntity();
		target = entity.getAttackTarget();
		if (SelectionUtils.isIdle(entity)) {
			return DONT_SELECT;
		}
		Vec3d toTarget = WorldHelper.getVectorToTarget(entity, target);
		double dist = toTarget.lengthVector();
		if (dist > MAX_DIST) {
			return DONT_SELECT;
		}
		return WEIGHT;
	}
	@Override
	public IAttackProvider getAttackProvider() {
		return ATTACK;
	}

	@Override
	public void beginExecution() {
		super.beginExecution();
		thrown = false;
	}

	private void updateStomp() {
		EntityDeviljho actor = this.getEntity();
		if (!actor.onGround || thrown || this.getCurrentFrame() < 26) {
			return;
		}
		super.onUpdate();

		AxisAlignedBB stompRange = actor.getEntityBoundingBox().expand(10.0D, 1.0D, 10.0D);
		List<Entity> list = actor.world.getEntitiesWithinAABBExcludingEntity(actor, stompRange);
		for (Entity entity : list) {
			if (!(entity instanceof EntityLivingBase)) {
				continue;
			}
			// FIXME: camera shake for players?
			entity.turn(0, 40);
			entity.addVelocity(0.2, 0.3, 0);
		}
		EntityAIMethods.stompCracks(actor, 100);
		actor.playSound(MHFCSoundRegistry.getRegistry().deviljhoStomp, 1.0F, 1.0F);
		thrown = true;
	}

	@Override
	protected void onUpdate() {
		EntityDeviljho entity = this.getEntity();
		target = entity.getAttackTarget();
		updateStomp();

		if (isMoveForwardFrame(getCurrentFrame())) {
			EntityDeviljho e = getEntity();
			e.moveForward(1, false);
		}

	}

	private boolean isMoveForwardFrame(int frame) {
		return (frame > 20 && frame < 30);
	}

}
