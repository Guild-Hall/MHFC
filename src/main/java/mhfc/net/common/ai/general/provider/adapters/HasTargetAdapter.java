package mhfc.net.common.ai.general.provider.adapters;

import java.util.Objects;

import mhfc.net.common.ai.general.provider.simple.IContinuationPredicate;
import net.minecraft.entity.EntityLiving;

public class HasTargetAdapter implements IContinuationPredicate {

	private EntityLiving actor;

	public HasTargetAdapter(EntityLiving actor) {
		this.actor = Objects.requireNonNull(actor);
	}

	@Override
	public boolean shouldContinueAction() {
		return actor.getAttackTarget() != null;
	}

}
