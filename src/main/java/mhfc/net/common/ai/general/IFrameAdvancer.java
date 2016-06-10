package mhfc.net.common.ai.general;

public interface IFrameAdvancer {

	void reset();

	int getFollowingFrame(int currentFrame);

	public static class LinearAdvancer implements IFrameAdvancer {
		@Override
		public void reset() {}

		@Override
		public int getFollowingFrame(int currentFrame) {
			return currentFrame + 1;
		}
	}

	/**
	 * Creates a loop from frame start to frame end and loops the given times.
	 * 
	 * @author Katora
	 *
	 */
	public static class CountLoopAdvancer implements IFrameAdvancer {
		protected int loopStart, loopEnd, loopAmount;
		protected int loopCounter;

		/**
		 * 
		 * @param loopStart
		 *            The start frame, inclusive
		 * @param loopEnd
		 *            The end of the loop, exclusive
		 * @param loopAmount
		 *            If smaller than 0, loops infinitely. If 0 then runs just as if there was no loop.
		 */
		public CountLoopAdvancer(int loopStart, int loopEnd, int loopAmount) {
			this.loopStart = loopStart;
			this.loopEnd = loopEnd;
			this.loopAmount = loopAmount;
		}

		@Override
		public void reset() {
			loopCounter = 0;
		}

		@Override
		public int getFollowingFrame(int currentFrame) {
			int normalAdvance = currentFrame + 1;
			if (normalAdvance == loopEnd) {
				if (loopAmount < 0 || loopCounter < loopAmount) {
					loopCounter++;
					return loopStart;
				}
			}
			return normalAdvance;
		}
	}

	public static class SwitchLoopAdvancer implements IFrameAdvancer {
		protected int loopStart, loopEnd;
		protected boolean loopActive;

		public SwitchLoopAdvancer(int loopStart, int loopEnd) {
			this.loopStart = loopStart;
			this.loopEnd = loopEnd;
		}

		public void setLoopActive(boolean active) {
			this.loopActive = active;
		}

		@Override
		public void reset() {
			this.loopActive = true;
		}

		@Override
		public int getFollowingFrame(int currentFrame) {
			int normalAdvance = currentFrame + 1;
			if (normalAdvance == loopEnd && loopActive) {
				return loopStart;
			}
			return normalAdvance;
		}
	}

}
