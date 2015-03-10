package mhfc.net.common.quests.api;

import java.util.HashMap;
import java.util.Map;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCQuestsRegistry;
import mhfc.net.common.quests.GeneralQuest;
import mhfc.net.common.quests.factory.ChainGoalFactory;
import mhfc.net.common.quests.factory.DeathRestrictionGoalFactory;
import mhfc.net.common.quests.factory.DefaultQuestFactory;
import mhfc.net.common.quests.factory.ForkGoalFactory;
import mhfc.net.common.quests.factory.HuntingGoalFactory;
import mhfc.net.common.quests.factory.TimeGoalFactory;
import net.minecraft.entity.player.EntityPlayer;

public class QuestFactory {

	public static QuestDescription getQuestDescription(String id) {
		QuestDescription qd = MHFCQuestsRegistry.getQuestDescription(id);
		return qd;
	}

	public static GoalDescription getGoalDescription(String id) {
		GoalDescription gd = MHFCQuestsRegistry.getGoalDescription(id);
		return gd;
	}

	private static Map<String, IQuestFactory> questFactoryMap = new HashMap<String, IQuestFactory>();
	private static Map<String, IGoalFactory> goalFactoryMap = new HashMap<String, IGoalFactory>();

	static {
		insertQuestFactory("", new DefaultQuestFactory());

		DeathRestrictionGoalFactory drFactory = new DeathRestrictionGoalFactory();
		insertGoalFactory(MHFCQuestsRegistry.GOAL_DEATH_RESTRICTION_TYPE,
				drFactory);
		insertGoalFactory("death restriction", drFactory);
		TimeGoalFactory tFactory = new TimeGoalFactory();
		insertGoalFactory(MHFCQuestsRegistry.GOAL_TIME_TYPE, tFactory);
		insertGoalFactory(MHFCQuestsRegistry.GOAL_HUNTING_TYPE,
				new HuntingGoalFactory());
		insertGoalFactory("timer", tFactory);
		insertGoalFactory(MHFCQuestsRegistry.GOAL_CHAIN_TYPE,
				new ChainGoalFactory());
		insertGoalFactory(MHFCQuestsRegistry.GOAL_FORK_TYPE,
				new ForkGoalFactory());
	}

	public static boolean insertQuestFactory(String str, IQuestFactory fact) {
		if (questFactoryMap.containsKey(str))
			return false;
		questFactoryMap.put(str, fact);
		return true;
	}

	public static boolean insertGoalFactory(String str, IGoalFactory fact) {
		if (goalFactoryMap.containsKey(str))
			return false;
		goalFactoryMap.put(str, fact);
		return true;
	}

	/**
	 * Constructs a quest based on the description object and a player to join
	 * the quest. If it is somehow invalid then null is returned.
	 */
	public static GeneralQuest constructQuest(QuestDescription qd,
			EntityPlayer initiator, String assignedID) {
		if (qd == null || !questFactoryMap.containsKey(qd.getFactory()))
			return null;
		IQuestFactory factory = getQuestFactory(qd.getFactory());
		if (factory == null)
			return null;
		GeneralQuest quest = factory.buildQuest(qd);
		if (quest == null)
			return null;

		if (quest.canJoin(initiator)) {
			MHFCQuestsRegistry.regRunningQuest(quest, assignedID);
			quest.addPlayer(initiator);
			return quest;
		}
		quest.getQuestGoal().questGoalFinalize();
		return null;
	}

	public static IQuestFactory getQuestFactory(String type) {
		return questFactoryMap.get(type);
	}

	/**
	 * Constructs a quest goal based on the description object. If it is somehow
	 * invalid then null is returned. The following types are implemented in the
	 * following way: <br>
	 * hunting: Needs to have exactly two arguments in its argument array, both
	 * of type string and the latter one representing an Integer.
	 */

	public static QuestGoal constructGoal(GoalDescription gd) {
		if (gd == null || !goalFactoryMap.containsKey(gd.getGoalType())) {
			MHFCMain.logger.error("Description null or type not registered");
			return null;
		}
		IGoalFactory factory = getGoalFactory(gd.getGoalType());
		if (factory == null) {
			MHFCMain.logger.error("Factory was not found");
			return null;
		}
		QuestGoal goal = factory.buildQuestGoal(gd);
		if (goal == null)
			MHFCMain.logger.warn("Constructed goal was given as null");
		return goal;
	}

	public static IGoalFactory getGoalFactory(String type) {
		return goalFactoryMap.get(type);
	}
}
