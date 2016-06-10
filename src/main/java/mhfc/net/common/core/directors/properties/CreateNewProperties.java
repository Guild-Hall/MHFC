package mhfc.net.common.core.directors.properties;

import mhfc.net.common.core.data.PlayerProperties;
import mhfc.net.common.world.exploration.ExplorationProperties;

public class CreateNewProperties {

	private PlayerProperties properties;

	public CreateNewProperties(PlayerProperties props) {
		properties = props;
	}

	public void construct() {
		properties.setExploration(new ExplorationProperties());
	}

}
