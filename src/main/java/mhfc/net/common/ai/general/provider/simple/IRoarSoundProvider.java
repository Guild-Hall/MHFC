package mhfc.net.common.ai.general.provider.simple;

public interface IRoarSoundProvider {
	public String getRoarSoundLocation();

	public static class RoarSoundAdapter implements IRoarSoundProvider {
		private String soundLocation;

		public RoarSoundAdapter(String soundLocation) {
			this.soundLocation = soundLocation;
		}

		@Override
		public String getRoarSoundLocation() {
			return soundLocation;
		}

	}
}
