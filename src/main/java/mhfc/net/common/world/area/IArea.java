package mhfc.net.common.world.area;

import mhfc.net.common.quests.world.IQuestArea;
import mhfc.net.common.world.controller.IAreaManager;

public interface IArea extends IQuestArea {
	/**
	 * Returns whether this area can (temporarily) not be used.<br>
	 * Checked by the {@link IAreaManager} when it searches for an area to put a
	 * questing party in. When an area without a party is found and this returns
	 * <code>false</code> the area is used as the area where the raid takes
	 * place.<br>
	 * This is optional and should probably always return <code>false</code>.
	 *
	 * @return <code>false</code> to disable questing in this area.
	 */
	boolean isBlocked();
}
