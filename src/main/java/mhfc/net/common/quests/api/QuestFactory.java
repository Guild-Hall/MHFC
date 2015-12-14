package mhfc.net.common.quests.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.core.registry.MHFCQuestRegistry;
import mhfc.net.common.quests.GeneralQuest;
import mhfc.net.common.quests.factory.*;
import net.minecraft.entity.player.EntityPlayerMP;

public class QuestFactory {

	public static QuestDescription getQuestDescription(String id) {
		QuestDescription qd = MHFCQuestBuildRegistry.getQuestDescription(id);
		return qd;
	}

	public static GoalDescription getGoalDescription(String id) {
		GoalDescription gd = MHFCQuestBuildRegistry.getGoalDescription(id);
		return gd;
	}

	private static Map<String, IQuestFactory> questFactoryMap = new HashMap<String, IQuestFactory>();
	private static Map<String, IGoalFactory> goalFactoryMap = new HashMap<String, IGoalFactory>();
	private static Map<String, IVisualInformationFactory> visualFactoryMap = new HashMap<String, IVisualInformationFactory>();

	static {
		insertQuestFactory(MHFCQuestBuildRegistry.QUEST_DEFAULT,
			new DefaultQuestFactory());

		DeathRestrictionGoalFactory drFactory = new DeathRestrictionGoalFactory();
		insertGoalFactory(MHFCQuestBuildRegistry.GOAL_DEATH_RESTRICTION_TYPE,
			drFactory);
		insertGoalFactory("death restriction", drFactory);
		TimeGoalFactory tFactory = new TimeGoalFactory();
		insertGoalFactory(MHFCQuestBuildRegistry.GOAL_TIME_TYPE, tFactory);
		insertGoalFactory(MHFCQuestBuildRegistry.GOAL_HUNTING_TYPE,
			new HuntingGoalFactory());
		insertGoalFactory("timer", tFactory);
		insertGoalFactory(MHFCQuestBuildRegistry.GOAL_CHAIN_TYPE,
			new ChainGoalFactory());
		insertGoalFactory(MHFCQuestBuildRegistry.GOAL_FORK_TYPE,
			new ForkGoalFactory());

		insertQuestVisualFactory(MHFCQuestBuildRegistry.VISUAL_DEFAULT,
			new QuestVisualInformationFactory());
		insertQuestVisualFactory(MHFCQuestBuildRegistry.VISUAL_RUNNING,
			new QuestRunningInformationFactory());
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

	public static boolean insertQuestVisualFactory(String str,
		IVisualInformationFactory fact) {
		if (visualFactoryMap.containsKey(str))
			return false;
		visualFactoryMap.put(str, fact);
		return true;
	}

	/**
	 * Constructs a quest based on the description object and a player to join
	 * the quest. If it is somehow invalid then null is returned.
	 */
	public static GeneralQuest constructQuest(QuestDescription qd,
		EntityPlayerMP initiator, String assignedID) {
		if (qd == null || !questFactoryMap.containsKey(qd.getType()))
			return null;
		IQuestFactory factory = getQuestFactory(qd.getType());
		if (factory == null)
			return null;
		GeneralQuest quest = factory.buildQuest(qd);
		if (quest == null)
			return null;

		if (quest.canJoin(initiator)) {
			MHFCQuestRegistry.regRunningQuest(quest, assignedID);
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
		Objects.requireNonNull(gd, "Goal description was null");
		QuestGoal goal = gd.build();
		if (goal == null)
			MHFCMain.logger.warn("Constructed goal returned as null");
		return goal;
	}

	public static IGoalFactory getGoalFactory(String type) {
		return goalFactoryMap.get(type);
	}

	public static IVisualInformationFactory getQuestVisualInformationFactory(
		String type) {
		return visualFactoryMap.get(type);
	}
}
