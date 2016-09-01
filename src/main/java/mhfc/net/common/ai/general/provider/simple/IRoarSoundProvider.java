package mhfc.net.common.ai.general.provider.simple;

import net.minecraft.util.SoundEvent;

public interface IRoarSoundProvider {
	public SoundEvent getRoarSoundLocation();

	public static class RoarSoundAdapter implements IRoarSoundProvider {
		private SoundEvent soundLocation;

		public RoarSoundAdapter(SoundEvent soundLocation) {
			this.soundLocation = soundLocation;
		}

		@Override
		public SoundEvent getRoarSoundLocation() {
			return soundLocation;
		}

	}
}
