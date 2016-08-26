package mhfc.net.common.quests.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.google.common.base.Preconditions;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.quests.GeneralQuest;
import mhfc.net.common.quests.factory.ChainGoalFactory;
import mhfc.net.common.quests.factory.DeathRestrictionGoalFactory;
import mhfc.net.common.quests.factory.DefaultQuestFactory;
import mhfc.net.common.quests.factory.ForkGoalFactory;
import mhfc.net.common.quests.factory.HuntingGoalFactory;
import mhfc.net.common.quests.factory.TimeGoalFactory;
import mhfc.net.common.quests.properties.GroupProperty;

public class QuestFactories {

	private static Map<String, IQuestFactory> questFactoryMap = new HashMap<>();
	private static Map<String, IGoalFactory> goalFactoryMap = new HashMap<>();
	private static Map<String, IVisualInformationFactory<?>> visualFactoryMap = new HashMap<>();

	static {
		insertQuestFactory(MHFCQuestBuildRegistry.QUEST_DEFAULT, new DefaultQuestFactory());

		DeathRestrictionGoalFactory drFactory = new DeathRestrictionGoalFactory();
		insertGoalFactory(MHFCQuestBuildRegistry.GOAL_DEATH_RESTRICTION_TYPE, drFactory);
		insertGoalFactory("death restriction", drFactory);
		TimeGoalFactory tFactory = new TimeGoalFactory();
		insertGoalFactory(MHFCQuestBuildRegistry.GOAL_TIME_TYPE, tFactory);
		insertGoalFactory(MHFCQuestBuildRegistry.GOAL_HUNTING_TYPE, new HuntingGoalFactory());
		insertGoalFactory("timer", tFactory);
		insertGoalFactory(MHFCQuestBuildRegistry.GOAL_CHAIN_TYPE, new ChainGoalFactory());
		insertGoalFactory(MHFCQuestBuildRegistry.GOAL_FORK_TYPE, new ForkGoalFactory());

		insertQuestVisualFactory(
				MHFCQuestBuildRegistry.VISUAL_DEFAULT,
				new DefaultQuestVisualDefinition.QuestVisualInformationFactory());
	}

	public static void insertQuestFactory(String type, IQuestFactory factory) {
		Preconditions.checkArgument(questFactoryMap.containsKey(type), "duplicate factory type " + type);
		questFactoryMap.put(type, factory);
	}

	public static void insertGoalFactory(String type, IGoalFactory factory) {
		Preconditions.checkArgument(goalFactoryMap.containsKey(type), "duplicate goal factory type " + type);
		goalFactoryMap.put(type, factory);
	}

	public static void insertQuestVisualFactory(String type, IVisualInformationFactory<?> factory) {
		Preconditions.checkArgument(visualFactoryMap.containsKey(type), "duplicate visual factory type " + type);
		visualFactoryMap.put(type, factory);
	}

	/**
	 * Constructs a quest based on the description object.
	 */
	public static GeneralQuest constructQuest(QuestDefinition qd) {
		Objects.requireNonNull(qd, "Quest description was null");
		return qd.build();
	}

	/**
	 * Constructs a quest goal based on the description object. If it is somehow invalid then null is returned. The
	 * following types are implemented in the following way: <br>
	 * hunting: Needs to have exactly two arguments in its argument array, both of type string and the latter one
	 * representing an Integer.
	 */
	public static QuestGoal constructGoal(GoalDefinition gd, GroupProperty rootProperties) {
		Objects.requireNonNull(gd, "Goal description was null");
		QuestGoal goal = gd.build(rootProperties);
		if (goal == null) {
			MHFCMain.logger().warn("Constructed goal returned as null");
		}
		return goal;
	}

	public static IQuestFactory getQuestFactory(String type) {
		return questFactoryMap.get(type);
	}

	public static IGoalFactory getGoalFactory(String type) {
		return goalFactoryMap.get(type);
	}

	public static IVisualInformationFactory<?> getQuestVisualInformationFactory(String type) {
		return visualFactoryMap.get(type);
	}
}
