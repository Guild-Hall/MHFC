package mhfc.net.common.world.area;

import mhfc.net.common.quests.world.IQuestArea;
import mhfc.net.common.world.controller.IAreaManager;

public interface IArea extends IQuestArea {
	/**
	 * Returns whether this area can (temporarily) not be used.<br>
	 * Checked by the {@link IAreaManager} when it searches for an area to acquire. When an inactive area is found and
	 * this returns <code>false</code> the area is used as the area where the raid takes place.<br>
	 * This is optional and should probably always return <code>false</code>.
	 *
	 * @return <code>true</code> to disable questing in this area.
	 */
	default boolean isUnusable() {
		return false;
	}

	default void onAcquire() {}

	default void onDismiss() {}

	IWorldView getWorldView();

	// probably unreliable, not needed for now
	// IAreaType getOwnType();
}
