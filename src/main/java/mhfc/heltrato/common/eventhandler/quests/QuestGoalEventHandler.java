package mhfc.heltrato.common.eventhandler.quests;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class QuestGoalEventHandler<EventType extends cpw.mods.fml.common.eventhandler.Event> {
	NotifyableQuestGoal<EventType> questGoal;
	boolean stillActive;

	public QuestGoalEventHandler(NotifyableQuestGoal<EventType> g) {
		this.questGoal = g;
		stillActive = false;
	}

	@SubscribeEvent
	public void onEventCaught(EventType event) {
		if (stillActive)
			questGoal.notifyOfEvent(event);
	}

	public void setActive(boolean active) {
		stillActive = active;
	}
}
