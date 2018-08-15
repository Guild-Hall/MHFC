package mhfc.net.common.core.data;

import mhfc.net.common.world.exploration.ExplorationProperties;
import net.minecraft.entity.player.EntityPlayer;

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
		this.exploration.cloneFrom(originalProperties.exploration);
	}

	public void setPlayer(EntityPlayer player) {
		this.exploration.setPlayer(player);
	}
}
