package mhfc.net.common.quests;

import java.util.Objects;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.quests.api.GoalDefinitionDelegate;
import mhfc.net.common.quests.api.IGoalDefinition;
import mhfc.net.common.quests.api.IGoalDefinitionFactory;
import mhfc.net.common.quests.api.IGoalFactory;
import mhfc.net.common.quests.api.IQuestDefinition;
import mhfc.net.common.quests.api.IQuestDefinitionFactory;
import mhfc.net.common.quests.api.QuestDefinitionDelegate;
import mhfc.net.common.quests.api.QuestGoal;
import mhfc.net.common.quests.factory.ChainGoalFactory;
import mhfc.net.common.quests.factory.DeathRestrictionGoalFactory;
import mhfc.net.common.quests.factory.DefaultQuestFactory;
import mhfc.net.common.quests.factory.ForkGoalFactory;
import mhfc.net.common.quests.factory.HuntingGoalFactory;
import mhfc.net.common.quests.factory.TimeGoalFactory;
import mhfc.net.common.quests.properties.GroupProperty;
import mhfc.net.common.util.io.JsonDelegatingConverter;
import net.minecraft.util.ResourceLocation;

public class QuestFactories {

	public static final String KEY_TYPE = "type";
	public static final String KEY_DATA = "data";

	private static JsonDelegatingConverter<IGoalDefinition, GoalDefinitionDelegate> GOAL_CONVERTER = new JsonDelegatingConverter<IGoalDefinition, GoalDefinitionDelegate>(
			KEY_TYPE,
			KEY_DATA) {
		@Override
		protected GoalDefinitionDelegate createB(ResourceLocation key, IGoalDefinition value) {
			return new GoalDefinitionDelegate(key, value);
		}

		@Override
		protected IGoalDefinition extractConvertibleFromB(GoalDefinitionDelegate value) {
			return value.getValue();
		}
		@Override
		protected ResourceLocation extractKeyFromB(GoalDefinitionDelegate value) {
			return value.getDelegateKey();
		}
	};

	private static JsonDelegatingConverter<IQuestDefinition, QuestDefinitionDelegate> QUEST_CONVERTER = new JsonDelegatingConverter<IQuestDefinition, QuestDefinitionDelegate>(
			KEY_TYPE,
			KEY_DATA) {
		@Override
		protected QuestDefinitionDelegate createB(ResourceLocation key, IQuestDefinition value) {
			return new QuestDefinitionDelegate(key, value);
		}

		@Override
		protected IQuestDefinition extractConvertibleFromB(QuestDefinitionDelegate value) {
			return value.getValue();
		}

		@Override
		protected ResourceLocation extractKeyFromB(QuestDefinitionDelegate value) {
			return value.getDelegateKey();
		}
	};

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
		insertQuestFactory(new ResourceLocation(type), factory);
	}
	public static void insertQuestFactory(ResourceLocation type, IQuestDefinitionFactory factory) {
		QUEST_CONVERTER.registerConverter(type, factory);
	}

	public static void insertGoalFactory(String type, IGoalDefinitionFactory factory) {
		insertGoalFactory(new ResourceLocation(type), factory);
	}

	public static void insertGoalFactory(ResourceLocation type, IGoalDefinitionFactory factory) {
		GOAL_CONVERTER.registerConverter(type, factory);
	}

	/**
	 * Constructs a quest based on the description object.
	 */
	public static Mission constructQuest(IQuestDefinition qd, String missionID) {
		Objects.requireNonNull(qd, "Quest description was null");
		return qd.build(missionID);
	}

	/**
	 * Constructs a quest goal based on the description object. If it is somehow invalid then null is returned. The
	 * following types are implemented in the following way: <br>
	 * hunting: Needs to have exactly two arguments in its argument array, both of type string and the latter one
	 * representing an Integer.
	 */
	public static QuestGoal constructGoal(IGoalDefinition gd, GroupProperty rootProperties) {
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
	public static IGoalFactory constructGoalVisualsFactory(IGoalDefinition gd, GroupProperty rootProperties) {
		return constructAndRegisterAttribs(gd, rootProperties).bindVisualSupplements();
	}

	private static IGoalFactory constructAndRegisterAttribs(IGoalDefinition gd, GroupProperty rootProps) {
		Objects.requireNonNull(gd, "Goal description was null");
		return gd.newFactory().bindAttributes(rootProps);
	}

	public static JsonDelegatingConverter<IGoalDefinition, GoalDefinitionDelegate> getGoalConverter() {
		return GOAL_CONVERTER;
	}

	public static JsonDelegatingConverter<IQuestDefinition, QuestDefinitionDelegate> getQuestConverter() {
		return QUEST_CONVERTER;
	}
}
