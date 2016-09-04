package mhfc.net.common.core.data;

import mhfc.net.common.world.exploration.ExplorationProperties;

public final class PlayerProperties {
	private ExplorationProperties exploration;

	public PlayerProperties() {
		exploration = new ExplorationProperties();
	}

	public void setExploration(ExplorationProperties exploration) {
		this.exploration = exploration;
	}

	public ExplorationProperties getExploration() {
		return exploration;
	}

	public void cloneProperties(PlayerProperties originalProperties) {
		this.exploration.cloneProperties(originalProperties.exploration);
	}
}
