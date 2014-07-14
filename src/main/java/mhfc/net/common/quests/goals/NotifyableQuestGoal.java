package mhfc.net.common.quests.goals;

public interface NotifyableQuestGoal<EventType extends cpw.mods.fml.common.eventhandler.Event> {
	public void notifyOfEvent(EventType event);
}
