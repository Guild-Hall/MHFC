package mhfc.net.common.ai.general.provider.simple;

public interface IAnimationProvider {

	public String getAnimationLocation();

	public int getAnimationLength();

	static class AnimationAdapter implements IAnimationProvider {
		private String animationLocation;
		private int animationLength;

		public AnimationAdapter(String animationLocation, int animationLength) {
			this.animationLocation = animationLocation;
			this.animationLength = animationLength;
		}

		@Override
		public String getAnimationLocation() {
			return animationLocation;
		}

		@Override
		public int getAnimationLength() {
			return animationLength;
		}

	}

}
