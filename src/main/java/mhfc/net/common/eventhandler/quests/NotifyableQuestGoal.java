package mhfc.net.common.eventhandler.quests;

import net.minecraftforge.fml.common.eventhandler.Event;

public interface NotifyableQuestGoal<EventType extends Event> {
	public void notifyOfEvent(EventType event);
}
