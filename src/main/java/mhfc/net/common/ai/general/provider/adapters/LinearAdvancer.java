package mhfc.net.common.ai.general.provider.adapters;

import mhfc.net.common.ai.general.provider.simple.IFrameAdvancer;

public class LinearAdvancer implements IFrameAdvancer {

	@Override
	public int getFollowingFrame(int currentFrame) {
		return currentFrame + 1;
	}

}
