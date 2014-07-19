package mhfc.net.common.quests;

import mhfc.net.common.core.registry.MHFCRegQuests;
import mhfc.net.common.quests.goals.ChainQuestGoal;
import mhfc.net.common.quests.goals.ForkQuestGoal;
import mhfc.net.common.quests.goals.HuntingQuestGoal;
import mhfc.net.common.quests.goals.QuestGoal;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;

public class QuestFactory {

	public static QuestDescription getQuestDescription(String id) {
		QuestDescription qd = MHFCRegQuests.getQuestDescription(id);
		return qd;
	}

	public static GoalDescription getGoalDescription(String id) {
		GoalDescription gd = MHFCRegQuests.getGoalDescription(id);
		return gd;
	}

	/**
	 * Constructs a quest based on the description object and a player to join
	 * the quest. If it is somehow invalid then null is returned.
	 */
	public static GeneralQuest constructQuest(QuestDescription qd,
			EntityPlayer initiator) {
		QuestGoal goal = constructGoal(getGoalDescription(qd
				.getGoalDescriptionID()));
		if (goal == null)
			return null;
		GeneralQuest quest = new GeneralQuest(goal, qd.getMaxPartySize(),
				qd.getReward(), qd.getFee(), qd.getAreaID());
		if (quest.canJoin(initiator)) {
			quest.addPlayer(initiator);
			return quest;
		}
		quest.getQuestGoal().questGoalFinalize();
		return null;
	}

//@formatter:off
	/**
	 * Constructs a quest goal based on the description object. If it is somehow
	 * invalid then null is returned. The following types are implemented in the
	 * following way: 
	 * hunting: Needs to have exactly two arguments in its
	 * argument array, both of type string and the latter one representing an Integer.
	 */
//@formatter:on

	// TODO throw some more exceptions for debugging
	public static QuestGoal constructGoal(GoalDescription gd) {
		QuestGoal goal = null;
		switch (gd.getGoalType()) {
			case "hunting" :
				if (gd.getArguments().length != 2
						|| !String.class.isAssignableFrom(gd.getArguments()[0]
								.getClass())
						|| !String.class.isAssignableFrom(gd.getArguments()[1]
								.getClass()))
					return null;

				Class<?> goalClass = (Class<?>) EntityList.stringToClassMapping
						.get(gd.arguments[0]);
				if (goalClass == null)
					return null;
				int number = Integer.parseInt((String) gd.getArguments()[1]);
				HuntingQuestGoal hGoal = new HuntingQuestGoal(null, goalClass,
						number);
				goal = hGoal;
				break;
			case "chain" :
				if (gd.getDependencyIds().length == 0)
					goal = new ChainQuestGoal(null);
				else if (gd.getDependencyIds().length == 1) {
					QuestGoal dep1 = constructGoal(getGoalDescription(gd
							.getDependencyIds()[0]));
					if (dep1 == null) {
						// TODO What should be done here?
					} else {
						goal = new ChainQuestGoal(dep1);
						dep1.setSocket((ChainQuestGoal) goal);
					}
				} else if (gd.getDependencyIds().length == 2) {
					QuestGoal dep1 = constructGoal(getGoalDescription(gd
							.getDependencyIds()[0]));
					QuestGoal dep2 = constructGoal(getGoalDescription(gd
							.getDependencyIds()[1]));
					if (dep1 == null || dep2 == null) {
						// TODO What should be done here?
					} else {
						goal = new ChainQuestGoal(dep1, dep2);
						dep1.setSocket((ChainQuestGoal) goal);
						dep2.setSocket((ChainQuestGoal) goal);
					}
				} else {
					// TODO Too many dependencies for ChainQuestGoal, do sth
				}
				break;
			case "fork" :
				ForkQuestGoal fGoal = new ForkQuestGoal(null);
				for (String str : gd.getDependencyIds()) {
					QuestGoal dep = constructGoal(getGoalDescription(str));
					if (dep == null) {
						// TODO Wrong dependency, do sth
					} else {
						fGoal.addRequisite(dep);
					}
				}
				// TODO what with optional?
				break;
			default :
				break;
		}
		return null;
	}
}
