package mhfc.net.common.eventhandler.quests;

import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LivingDeathEventHandler extends QuestGoalEventHandler<LivingDeathEvent> {

	public LivingDeathEventHandler(NotifyableQuestGoal<LivingDeathEvent> g) {
		super(g, LivingDeathEvent.class);
	}

	@SubscribeEvent
	public void onEventCaught(LivingDeathEvent event) {
		if (stillActive) {
			questGoal.notifyOfEvent(event);
		}
	}

}
