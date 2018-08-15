package mhfc.net.common.ai.general.provider.adapters;

import mhfc.net.common.ai.general.provider.simple.IFrameAdvancer;

public class SwitchLoopAdvancer implements IFrameAdvancer {
	protected int loopStart, loopEnd;
	protected boolean loopActive;

	public SwitchLoopAdvancer(int loopStart, int loopEnd) {
		this.loopStart = loopStart;
		this.loopEnd = loopEnd;
	}

	public void setLoopActive(boolean active) {
		this.loopActive = active;
	}

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
