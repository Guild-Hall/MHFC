package mhfc.net.client.quests.api;

import mhfc.net.common.util.stringview.Viewable;

public interface IPagedView {
	Viewable getView();

	int getPageCount();
}
