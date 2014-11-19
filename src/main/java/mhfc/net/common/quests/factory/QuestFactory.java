package mhfc.net.common.quests.factory;

import java.util.HashMap;
import java.util.Map;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCRegQuests;
import mhfc.net.common.quests.GeneralQuest;
import mhfc.net.common.quests.goals.ChainQuestGoal;
import mhfc.net.common.quests.goals.DeathRestrictionQuestGoal;
import mhfc.net.common.quests.goals.ForkQuestGoal;
import mhfc.net.common.quests.goals.HuntingQuestGoal;
import mhfc.net.common.quests.goals.QuestGoal;
import mhfc.net.common.quests.goals.TimeQuestGoal;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;

public class QuestFactory {

	public static interface IQuestFactory {
		public GeneralQuest buildQuest(QuestDescription questDesc);
	}

	public static class DefaultQuestFactory implements IQuestFactory {

		@Override
		public GeneralQuest buildQuest(QuestDescription qd) {
			QuestGoal goal = constructGoal(qd.getGoalDescription());
			if (goal == null)
				return null;
			return new GeneralQuest(goal, qd.getMaxPartySize(), qd.getReward(),
					qd.getFee(), qd.getAreaID(), qd);
		}

	}

	public static interface IGoalFactory {
		public QuestGoal buildQuestGoal(GoalDescription goalDesc);
	}

	public static class DeathRestrictionGoalFactory implements IGoalFactory {

		@Override
		public QuestGoal buildQuestGoal(GoalDescription gd) {
			if (gd.getArguments().length != 1
					|| !String.class.isAssignableFrom(gd.getArguments()[0]
							.getClass())) {
				throw new IllegalArgumentException(
						"A death restriction goal expects exactly one argument in string format that represents an integer");
			}
			Integer i = Integer.parseInt((String) gd.getArguments()[0]);
			return new DeathRestrictionQuestGoal(i.intValue());
		}

	}

	public static class TimeGoalFactory implements IGoalFactory {

		@Override
		public QuestGoal buildQuestGoal(GoalDescription gd) {
			if (gd.getArguments().length != 1
					|| !String.class.isAssignableFrom(gd.getArguments()[0]
							.getClass())) {
				throw new IllegalArgumentException(
						"A time goal expects exactly one argument in string format that represents an integer");
			}
			Integer i = Integer.parseInt((String) gd.getArguments()[0]);
			return new TimeQuestGoal(i.intValue());
		}

	}

	public static class HuntingGoalFactory implements IGoalFactory {

		@Override
		public QuestGoal buildQuestGoal(GoalDescription gd) {
			if (gd.getArguments().length != 2
					|| !String.class.isAssignableFrom(gd.getArguments()[0]
							.getClass())
					|| !String.class.isAssignableFrom(gd.getArguments()[1]
							.getClass()))
				throw new IllegalArgumentException(
						"[MHFC] A hunter goal needs a String and a string representing an integer argument");

			Class<?> goalClass = (Class<?>) EntityList.stringToClassMapping
					.get(gd.arguments[0]);
			if (goalClass == null) {
				System.out.println(gd.getArguments()[0]);
				throw new IllegalArgumentException(
						"[MHFC] The mob identifier could not be resolved");
			}
			int number = Integer.parseInt((String) gd.getArguments()[1]);
			return new HuntingQuestGoal(null, goalClass, number);
		}

	}

	public static class ChainGoalFactory implements IGoalFactory {

		@Override
		public QuestGoal buildQuestGoal(GoalDescription gd) {
			ChainQuestGoal goal;
			if (gd.getDependencies().length == 0)
				return new ChainQuestGoal(null);
			else if (gd.getDependencies().length == 1) {
				QuestGoal dep1 = constructGoal(gd.getDependencies()[0]);

				goal = new ChainQuestGoal(dep1);
				if (dep1 != null)
					dep1.setSocket(goal);
				return goal;

			} else if (gd.getDependencies().length == 2) {
				QuestGoal dep1 = constructGoal(gd.getDependencies()[0]);
				QuestGoal dep2 = constructGoal(gd.getDependencies()[1]);

				goal = new ChainQuestGoal(dep1, dep2);
				if (dep1 != null)
					dep1.setSocket(goal);
				if (dep2 != null)
					dep2.setSocket(goal);
				return goal;

			} else
				return null;
		}

	}

	public static class ForkGoalFactory implements IGoalFactory {

		@Override
		public QuestGoal buildQuestGoal(GoalDescription goalDesc) {
			GoalDescription[] dependencies = goalDesc.getDependencies();
			ForkQuestGoal fork = new ForkQuestGoal(null);
			for (GoalDescription desc : dependencies) {
				QuestGoal q = constructGoal(desc);
				if (q == null)
					continue;
				fork.addRequisite(constructGoal(desc));
			}
			return fork;

		}

	}

	public static QuestDescription getQuestDescription(String id) {
		QuestDescription qd = MHFCRegQuests.getQuestDescription(id);
		return qd;
	}

	public static GoalDescription getGoalDescription(String id) {
		GoalDescription gd = MHFCRegQuests.getGoalDescription(id);
		return gd;
	}

	private static Map<String, IQuestFactory> questFactoryMap = new HashMap<String, IQuestFactory>();
	private static Map<String, IGoalFactory> goalFactoryMap = new HashMap<String, IGoalFactory>();

	static {
		insertQuestFactory("", new DefaultQuestFactory());
		DeathRestrictionGoalFactory drFactory = new DeathRestrictionGoalFactory();
		insertGoalFactory("death", drFactory);
		insertGoalFactory("death restriction", drFactory);
		TimeGoalFactory tFactory = new TimeGoalFactory();
		insertGoalFactory("time", tFactory);
		insertGoalFactory("hunting", new HuntingGoalFactory());
		insertGoalFactory("timer", tFactory);
		insertGoalFactory("chain", new ChainGoalFactory());
		insertGoalFactory("fork", new ForkGoalFactory());
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
		IQuestFactory factory = questFactoryMap.get(qd.getFactory());
		if (factory == null)
			return null;
		GeneralQuest quest = factory.buildQuest(qd);
		if (quest == null)
			return null;

		if (quest.canJoin(initiator)) {
			MHFCRegQuests.registerQuest(quest, assignedID);
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
		if (gd == null || !goalFactoryMap.containsKey(gd.getGoalType())) {
			MHFCMain.logger.error("Description null or type not registered");
			return null;
		}
		IGoalFactory factory = goalFactoryMap.get(gd.getGoalType());
		if (factory == null) {
			MHFCMain.logger.error("Factory was null");
			return null;
		}
		QuestGoal goal = factory.buildQuestGoal(gd);
		if (goal == null)
			MHFCMain.logger.warn("Goal was null");
		return goal;
	}
}
