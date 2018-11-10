package mhfc.net.common.ai.entity;

import mhfc.net.common.ai.general.actions.DeathAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.entity.CreatureAttributes;
import net.minecraft.util.SoundEvent;

public class AIDeath extends DeathAction<CreatureAttributes<?>> implements IHasAnimationProvider {

	protected CreatureAttributes<?> entity;
	protected String animationLocation;
	protected SoundEvent sound;

	public AIDeath(CreatureAttributes<?> entity, String animationLocation, SoundEvent sound) {
		this.entity = entity;
		this.animationLocation = animationLocation;
		this.sound = sound;
	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return new AnimationAdapter(this, animationLocation, 0);
	}

	@Override
	protected void beginExecution() {
		super.beginExecution();
	}

	@Override
	public SoundEvent provideDeathSound() {
		return this.sound;
	}

}
