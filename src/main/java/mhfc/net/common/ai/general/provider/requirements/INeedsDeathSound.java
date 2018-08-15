package mhfc.net.common.ai.general.provider.requirements;

import net.minecraft.util.SoundEvent;

public interface INeedsDeathSound {
	/**
	 * Called before execution to determine the death sound to make while dying
	 * 
	 * @return
	 */
	SoundEvent provideDeathSound();
}
