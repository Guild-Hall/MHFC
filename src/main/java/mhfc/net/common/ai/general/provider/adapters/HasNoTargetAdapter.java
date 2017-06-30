package mhfc.net.common.ai.general.provider.adapters;

import java.util.Objects;

import mhfc.net.common.ai.general.provider.simple.IContinuationPredicate;
import net.minecraft.entity.EntityLiving;

public class HasNoTargetAdapter implements IContinuationPredicate {

	private EntityLiving actor;

	public HasNoTargetAdapter(EntityLiving actor) {
		this.actor = Objects.requireNonNull(actor);
	}

	@Override
	public boolean shouldContinueAction() {
		return actor.getAttackTarget() == null;
	}

}
