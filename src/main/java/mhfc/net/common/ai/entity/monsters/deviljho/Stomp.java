package mhfc.net.common.ai.entity.monsters.deviljho;

import java.util.List;

import mhfc.net.common.ai.entity.EntityAIMethods;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.SelectionUtils;
import mhfc.net.common.ai.general.actions.DamagingAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.AttackAdapter;
import mhfc.net.common.ai.general.provider.adapters.DamageAdapter;
import mhfc.net.common.ai.general.provider.composite.IAttackProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAttackProvider;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.monster.EntityDeviljho;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;

public class Stomp extends DamagingAction<EntityDeviljho> implements IHasAttackProvider {
	private boolean thrown = false;

	public Stomp() {}

	@Override
	protected float computeSelectionWeight() {
		EntityDeviljho entity = this.getEntity();
		target = entity.getAttackTarget();
		if (SelectionUtils.isIdle(entity)) {
			return DONT_SELECT;
		}
		if (!SelectionUtils.isInDistance(0, 15, entity, target)) {
			return DONT_SELECT;
		}
		return 15;
	}
	@Override
	public IAttackProvider getAttackProvider() {
		return new AttackAdapter(
				new AnimationAdapter(this, "mhfc:models/Deviljho/DeviljhoStomp.mcanm", 55),
				new DamageAdapter(AIUtils.defaultDamageCalc(40F, 50F, 9999999f)));
	}

	@Override
	public void beginExecution() {
		super.beginExecution();
		thrown = false;
	}

	private void updateStomp() {
		damageCollidingEntities();
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

	}


}
