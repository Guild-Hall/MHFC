package mhfc.net.common.quests;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.google.common.base.Preconditions;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.quests.api.GoalDefinition;
import mhfc.net.common.quests.api.IGoalDefinitionFactory;
import mhfc.net.common.quests.api.IGoalFactory;
import mhfc.net.common.quests.api.IQuestDefinitionFactory;
import mhfc.net.common.quests.api.QuestDefinition;
import mhfc.net.common.quests.api.QuestGoal;
import mhfc.net.common.quests.factory.ChainGoalFactory;
import mhfc.net.common.quests.factory.DeathRestrictionGoalFactory;
import mhfc.net.common.quests.factory.DefaultQuestFactory;
import mhfc.net.common.quests.factory.ForkGoalFactory;
import mhfc.net.common.quests.factory.HuntingGoalFactory;
import mhfc.net.common.quests.factory.TimeGoalFactory;
import mhfc.net.common.quests.properties.GroupProperty;

public class QuestFactories {

	private static Map<String, IQuestDefinitionFactory> questFactoryMap = new HashMap<>();
	private static Map<String, IGoalDefinitionFactory> goalFactoryMap = new HashMap<>();

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
	}

	public static void insertQuestFactory(String type, IQuestDefinitionFactory factory) {
		Preconditions.checkArgument(!questFactoryMap.containsKey(type), "duplicate factory type " + type);
		questFactoryMap.put(type, factory);
	}

	public static void insertGoalFactory(String type, IGoalDefinitionFactory factory) {
		Preconditions.checkArgument(!goalFactoryMap.containsKey(type), "duplicate goal factory type " + type);
		goalFactoryMap.put(type, factory);
	}

	/**
	 * Constructs a quest based on the description object.
	 */
	public static Mission constructQuest(QuestDefinition qd, String missionID) {
		Objects.requireNonNull(qd, "Quest description was null");
		return qd.build(missionID);
	}

	/**
	 * Constructs a quest goal based on the description object. If it is somehow invalid then null is returned. The
	 * following types are implemented in the following way: <br>
	 * hunting: Needs to have exactly two arguments in its argument array, both of type string and the latter one
	 * representing an Integer.
	 */
	public static QuestGoal constructGoal(GoalDefinition gd, GroupProperty rootProperties) {
		QuestGoal goal = constructAndRegisterAttribs(gd, rootProperties).build();
		if (goal == null) {
			MHFCMain.logger().warn("Constructed goal returned as null");
		}
		return goal;
	}

	/**
	 * Constructs the visual of a quest goal based on the description object. If it is somehow invalid then null is
	 * returned.
	 */
	public static IGoalFactory constructGoalVisualsFactory(GoalDefinition gd, GroupProperty rootProperties) {
		return constructAndRegisterAttribs(gd, rootProperties).bindVisualSupplements();
	}

	private static IGoalFactory constructAndRegisterAttribs(GoalDefinition gd, GroupProperty rootProps) {
		Objects.requireNonNull(gd, "Goal description was null");
		return gd.newFactory().bindAttributes(rootProps);
	}

	public static IQuestDefinitionFactory getQuestFactory(String type) {
		return questFactoryMap.get(type);
	}

	public static IGoalDefinitionFactory getGoalFactory(String type) {
		return goalFactoryMap.get(type);
	}
}
