package mhfc.heltrato.common.quests;

import mhfc.heltrato.common.core.registry.MHFCRegQuests;
import mhfc.heltrato.common.quests.goals.ChainQuestGoal;
import mhfc.heltrato.common.quests.goals.DeathRestrictionQuestGoal;
import mhfc.heltrato.common.quests.goals.ForkQuestGoal;
import mhfc.heltrato.common.quests.goals.HuntingQuestGoal;
import mhfc.heltrato.common.quests.goals.QuestGoal;
import mhfc.heltrato.common.quests.goals.TimeQuestGoal;
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
		QuestGoal goal = constructGoal(qd.getGoalDescription());
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

	public static QuestGoal constructGoal(GoalDescription gd) {
		QuestGoal goal = null;
		switch (gd.getGoalType()) {
			case "hunting" :
				if (gd.getArguments().length != 2
						|| !String.class.isAssignableFrom(gd.getArguments()[0]
								.getClass())
						|| !String.class.isAssignableFrom(gd.getArguments()[1]
								.getClass()))
					throw new IllegalArgumentException(
							"[MHFC] A hunter goal needs a String and a string representing an integer argument");

				Class<?> goalClass = (Class<?>) EntityList.stringToClassMapping
						.get(gd.arguments[0]);
				if (goalClass == null)
					throw new IllegalArgumentException(
							"[MHFC] The mob identifier could not be resolved");
				int number = Integer.parseInt((String) gd.getArguments()[1]);
				HuntingQuestGoal hGoal = new HuntingQuestGoal(null, goalClass,
						number);
				goal = hGoal;
				break;
			case "chain" :
				if (gd.getDependencies().length == 0)
					goal = new ChainQuestGoal(null);
				else if (gd.getDependencies().length == 1) {
					QuestGoal dep1 = constructGoal(gd.getDependencies()[0]);
					if (dep1 == null) {
						// TODO What should be done here?
					} else {
						goal = new ChainQuestGoal(dep1);
						dep1.setSocket((ChainQuestGoal) goal);
					}
				} else if (gd.getDependencies().length == 2) {
					QuestGoal dep1 = constructGoal(gd.getDependencies()[0]);
					QuestGoal dep2 = constructGoal(gd.getDependencies()[1]);
					if (dep1 == null || dep2 == null) {
						// TODO What should be done here?
					} else {
						goal = new ChainQuestGoal(dep1, dep2);
						dep1.setSocket((ChainQuestGoal) goal);
						dep2.setSocket((ChainQuestGoal) goal);
					}
				} else {
					throw new IllegalArgumentException(
							"[MHFC] Too many dependencies for a chain goal");
				}
				break;
			case "fork" :
				ForkQuestGoal fGoal = new ForkQuestGoal(null);
				for (GoalDescription desc : gd.getDependencies()) {
					QuestGoal dep = constructGoal(desc);
					if (dep == null) {
						throw new IllegalArgumentException(
								"[MHFC] A dependency of the description was wrong");
					} else {
						fGoal.addRequisite(dep);
					}
				}
				// TODO what with optional?
				goal = fGoal;
				break;
			case "timer" :
			case "time" :
				if (gd.getArguments().length != 1
						|| !String.class.isAssignableFrom(gd.getArguments()[0]
								.getClass())) {
					throw new IllegalArgumentException(
							"A time goal expects exactly one argument in string format that represents an integer");
				}
				Integer i = Integer.parseInt((String) gd.getArguments()[0]);
				goal = new TimeQuestGoal(i.intValue());
				break;
			case "death restriction" :
			case "death" :
				if (gd.getArguments().length != 1
						|| !String.class.isAssignableFrom(gd.getArguments()[0]
								.getClass())) {
					throw new IllegalArgumentException(
							"A death restriction goal expects exactly one argument in string format that represents an integer");
				}
				i = Integer.parseInt((String) gd.getArguments()[0]);
				goal = new DeathRestrictionQuestGoal(i.intValue());
				break;
			default :
				break;
		}
		return goal;
	}
}
