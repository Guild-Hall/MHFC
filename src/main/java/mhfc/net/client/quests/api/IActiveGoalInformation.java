package mhfc.net.client.quests.api;

import mhfc.net.common.util.parsing.IValueHolder;
import mhfc.net.common.util.stringview.Viewable;

public interface IActiveGoalInformation {

	/**
	 * Called once to get a {@link IValueHolder} that is used to display the status of this
	 *
	 * @return
	 */
	public abstract Viewable getStatus();
}
