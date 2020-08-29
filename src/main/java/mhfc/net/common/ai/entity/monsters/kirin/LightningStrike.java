package mhfc.net.common.ai.entity.monsters.kirin;

import mhfc.net.common.ai.general.SelectionUtils;
import mhfc.net.common.ai.general.actions.AnimatedAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.creature.Kirin;
import net.minecraft.entity.effect.EntityLightningBolt;

public class LightningStrike extends AnimatedAction<Kirin> implements IHasAnimationProvider{
	
	private boolean thrown;
	
	public LightningStrike() {}

	@Override
	public IAnimationProvider getAnimProvider() {
		return new AnimationAdapter(this, "mhfc:models/Kirin/kirinlightningstrike.mcanm", 50);
	}

	@Override
	protected float computeSelectionWeight() {
		Kirin entity = this.getEntity();
		target = entity.getAttackTarget();
		if( SelectionUtils.isIdle(entity)) {
			return DONT_SELECT;
		}
		return 10F;
	}
	
	@Override
	protected void beginExecution() {
		super.beginExecution();
		thrown = false;
	}
	
	@Override
	protected void onUpdate() {
		Kirin entity = this.getEntity();
		if(thrown) {
			return;
		}
		if(entity.world.isRemote) {
			return;
		}
		if(this.getCurrentFrame() < 31) {
			if (getCurrentFrame() < 17) {
				entity.getTurnHelper().updateTurnSpeed(50);
				entity.getTurnHelper().updateTargetPoint(entity.getAttackTarget());
			}
			return;
		}
		thrown = true;
		entity.playSound(MHFCSoundRegistry.getRegistry().kirinLightningStrike, 2.0F, 1.0F);
		if(entity.getAttackTarget() != null) {
			entity.world.addWeatherEffect(new EntityLightningBolt(entity.world, entity.getAttackTarget().posX, entity.getAttackTarget().posY, entity.getAttackTarget().posZ, false));
		}
	}

}
