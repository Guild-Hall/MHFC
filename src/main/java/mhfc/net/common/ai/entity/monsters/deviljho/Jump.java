package mhfc.net.common.ai.entity.monsters.deviljho;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.SelectionUtils;
import mhfc.net.common.ai.general.actions.JumpAction;
import mhfc.net.common.ai.general.provider.adapters.*;
import mhfc.net.common.ai.general.provider.composite.IJumpProvider;
import mhfc.net.common.ai.general.provider.impl.IHasJumpProvider;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.CreatureAttributes;
import mhfc.net.common.entity.creature.Deviljho;

public class Jump extends JumpAction<Deviljho> implements IHasJumpProvider<Deviljho> {


	private boolean thrown = false;

	public Jump() {}

	@Override
	protected float computeSelectionWeight() {
		Deviljho entity = this.getEntity();
		target = entity.getAttackTarget();
		if (SelectionUtils.isIdle(entity)) {
			return DONT_SELECT;
		}
		if (!SelectionUtils.isInDistance(0, 15, entity, target)) {
			return DONT_SELECT;
		}
		return 12F;
	}

	@Override
	public IJumpProvider<Deviljho> getJumpProvider() {
		return new JumpAdapter<Deviljho>(
				new AnimationAdapter(this, "mhfc:models/Deviljho/DeviljhoJump.mcanm", 60),
				new DamageAdapter(AIUtils.defaultDamageCalc(34F, 2000f, 999999F)),
				new AttackTargetAdapter<Deviljho>(13.5f),
				new JumpTimingAdapter<Deviljho>(20, 14, 0));
	}

	@Override
	public void onUpdate() {
		Deviljho entity = this.getEntity();
		target = entity.getAttackTarget();
		super.onUpdate();
		if (this.getCurrentFrame() == 5) {
			entity.playSound(MHFCSoundRegistry.getRegistry().deviljhoLeap, 2.0F, 1.0F);
		}
		if (!entity.onGround || thrown || this.getCurrentFrame() < 302) {
			return;
		}
		CreatureAttributes.spawnCracks(entity, 200);
		if (this.getCurrentFrame() > 10) {

			if (this.getCurrentFrame() >= 15 && this.getCurrentFrame() <= 20) {
				CreatureAttributes.screenIntensity(entity, 10F, 40F);
			}
			thrown = true;
		}
	}
}
