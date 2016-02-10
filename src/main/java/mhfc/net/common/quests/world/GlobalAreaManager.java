package mhfc.net.common.quests.world;

import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IAreaType;

public class GlobalAreaManager {
	public static final GlobalAreaManager instance = new GlobalAreaManager();

	public static GlobalAreaManager getInstance() {
		return instance;
	}

	private GlobalAreaManager() {}

	public IActiveArea findFreeInstance(IAreaType type, QuestFlair questFlair) {
		// FIXME: add implementation
		return null;
	}
}
