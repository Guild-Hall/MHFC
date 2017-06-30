package mhfc.net.common.eventhandler.quests;

import net.minecraftforge.fml.common.eventhandler.Event;

public abstract class QuestGoalEventHandler<EventType extends Event> {
	NotifyableQuestGoal<EventType> questGoal;
	boolean stillActive;
	protected Class<EventType> eventType;

	public QuestGoalEventHandler(NotifyableQuestGoal<EventType> g, Class<EventType> t) {
		this.questGoal = g;
		stillActive = false;
		this.eventType = t;
	}

	public void setActive(boolean active) {
		stillActive = active;
	}
}
