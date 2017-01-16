package mhfc.net.common.ai.entity.boss.nargacuga;

import mhfc.net.common.ai.general.actions.DeathAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.monster.EntityNargacuga;
import net.minecraft.util.SoundEvent;

public class Death extends DeathAction<EntityNargacuga> implements IHasAnimationProvider {

	private static final String ANIMATION_LOCATION = "mhfc:models/Nargacuga/NargaDeath.001.mcanm";
	private final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, 0);

	public Death() {}

	@Override
	public IAnimationProvider getAnimProvider() {
		return ANIMATION;
	}

	@Override
	public SoundEvent provideDeathSound() {
		return MHFCSoundRegistry.getRegistry().nargacugaDeath;
	}

}
