package mhfc.net.common.quests.factory;

import mhfc.net.common.quests.GeneralQuest;
import mhfc.net.common.quests.api.IQuestFactory;
import mhfc.net.common.quests.api.QuestDescription;
import mhfc.net.common.quests.api.QuestFactory;
import mhfc.net.common.quests.api.QuestGoal;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;

public class DefaultQuestFactory implements IQuestFactory {

	@Override
	public GeneralQuest buildQuest(QuestDescription qd) {
		QuestGoal goal = QuestFactory.constructGoal(qd.getGoalDescription());
		if (goal == null)
			return null;
		return new GeneralQuest(goal, qd.getMaxPartySize(), qd.getReward(),
				qd.getFee(), qd.getAreaID(), qd);
	}

	@Override
	public QuestDescription buildQuestDescription(JsonElement json,
			JsonDeserializationContext context) {
		// TODO Auto-generated method stub
		return null;
	}

}