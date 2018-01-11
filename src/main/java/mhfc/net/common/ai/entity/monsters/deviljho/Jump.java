package mhfc.net.common.ai.entity.monsters.deviljho;

import mhfc.net.common.ai.entity.EntityAIMethods;
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
import mhfc.net.common.entity.monster.EntityDeviljho;
import net.minecraft.entity.player.EntityPlayer;

public class Jump extends JumpAction<EntityDeviljho> implements IHasJumpProvider<EntityDeviljho> {


	private boolean thrown = false;

	public Jump() {}

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
		return 12F;
	}

	@Override
	public IJumpProvider<EntityDeviljho> getJumpProvider() {
		return new JumpAdapter<EntityDeviljho>(
				new AnimationAdapter(this, "mhfc:models/Deviljho/DeviljhoJump.mcanm", 60),
				new DamageAdapter(AIUtils.defaultDamageCalc(34F, 2000f, 999999F)),
				new AttackTargetAdapter<EntityDeviljho>(13.5f),
				new JumpTimingAdapter<EntityDeviljho>(20, 14, 0));
	}

	@Override
	public void onUpdate() {
		EntityDeviljho entity = this.getEntity();
		target = entity.getAttackTarget();
		super.onUpdate();
		if (this.getCurrentFrame() == 5) {
			entity.playSound(MHFCSoundRegistry.getRegistry().deviljhoLeap, 2.0F, 1.0F);
		}
		if (!entity.onGround || thrown || this.getCurrentFrame() < 302) {
			return;
		}
		EntityAIMethods.stompCracks(entity, 200);
		if (this.getCurrentFrame() > 10) {

		if(target instanceof EntityPlayer){
			EntityAIMethods.camShake(entity, 10F, 40F);
		}
		}
		thrown = true;
	}
}
