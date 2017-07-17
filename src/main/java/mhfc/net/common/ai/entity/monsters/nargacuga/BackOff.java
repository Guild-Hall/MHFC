package mhfc.net.common.ai.entity.monsters.nargacuga;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.SelectionUtils;
import mhfc.net.common.ai.general.actions.JumpAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.AttackTargetAdapter;
import mhfc.net.common.ai.general.provider.adapters.DamageAdapter;
import mhfc.net.common.ai.general.provider.adapters.JumpAdapter;
import mhfc.net.common.ai.general.provider.adapters.JumpTimingAdapter;
import mhfc.net.common.ai.general.provider.composite.IJumpProvider;
import mhfc.net.common.ai.general.provider.impl.IHasJumpProvider;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.monster.EntityNargacuga;

public class BackOff extends JumpAction<EntityNargacuga> implements IHasJumpProvider<EntityNargacuga> {



	public BackOff() {}

	@Override
	protected float computeSelectionWeight() {
		EntityNargacuga entity = this.getEntity();
		target = entity.getAttackTarget();
		if (SelectionUtils.isIdle(entity)) {
			return DONT_SELECT;
		}
		return 1;
	}

	@Override
	public IJumpProvider<EntityNargacuga> getJumpProvider() {
		return new JumpAdapter<EntityNargacuga>(
				new AnimationAdapter(this, "mhfc:models/Nargacuga/JumpBack.mcanm", 50),
				new DamageAdapter(AIUtils.defaultDamageCalc(50, 250, 70)),
				new AttackTargetAdapter<EntityNargacuga>(12) {
					@Override
					public float getForwardVelocity(EntityNargacuga entity) {
						return -4.5f;
					}
				},
				new JumpTimingAdapter<>(23, 2.5f, 1.5f));
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		EntityNargacuga entity = getEntity();
		if (this.getCurrentFrame() == 5) {
			damageCollidingEntities();
			entity.playSound(MHFCSoundRegistry.getRegistry().nargacugaBackOff, 2.0F, 1.0F);
		}
	}

}
