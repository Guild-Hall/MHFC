package mhfc.net.common.eventhandler.quests;

public interface NotifyableQuestGoal<EventType extends cpw.mods.fml.common.eventhandler.Event> {
	public void notifyOfEvent(EventType event);
}
