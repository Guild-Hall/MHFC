package mhfc.net.common.quests.descriptions;

import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.quests.api.GoalDefinition;
import mhfc.net.common.quests.api.IGoalFactory;
import mhfc.net.common.quests.api.QuestGoal;
import mhfc.net.common.quests.goals.HuntingQuestGoal;
import mhfc.net.common.quests.properties.GroupProperty;
import mhfc.net.common.quests.properties.IntProperty;
import mhfc.net.common.util.stringview.Viewable;
import mhfc.net.common.util.stringview.Viewables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;

public class HuntingGoalDescription extends GoalDefinition {

	public static final String ID_HUNTED_TYPE = "target";
	public static final String ID_AMOUNT = "amount";

	private int amount;
	private Class<? extends Entity> huntedClass;

	public HuntingGoalDescription(Class<? extends Entity> huntedClass, int amount) {
		super(MHFCQuestBuildRegistry.GOAL_HUNTING_TYPE);
		this.huntedClass = huntedClass;
		this.amount = amount;
	}

	public Class<? extends Entity> getHuntedClass() {
		return huntedClass;
	}

	public int getAmount() {
		return amount;
	}

	@Override
	public IGoalFactory newFactory() {
		return new IGoalFactory() {
			private IntProperty goalAmount;
			private IntProperty current;
			private GroupProperty baseProps;

			@Override
			public boolean areAttributesBound() {
				return goalAmount != null && current != null && baseProps != null;
			}

			@Override
			public IGoalFactory bindAttributes(GroupProperty goalProperties) {
				goalAmount = goalProperties.newMember("goal", IntProperty.construct(getAmount()));
				current = goalProperties.newMember("current", IntProperty.construct(0));
				baseProps = goalProperties;
				return this;
			}

			@Override
			public Viewable buildVisual() {
				checkAttributesBound();
				String goalMob = (String) EntityList.classToStringMapping.get(getHuntedClass());
				goalMob = "entity." + goalMob + ".name";
				return Viewables.parse("[[" + goalMob + "]]s§r slain: {{current}}/{{goal}}", baseProps);
			}

			@Override
			public Viewable buildShortStatus() {
				checkAttributesBound();
				String goalMob = (String) EntityList.classToStringMapping.get(getHuntedClass());
				goalMob = "entity." + goalMob + ".name";
				return Viewables.parse("{{current}}/{{goal}} [[" + goalMob + "]]s", baseProps);
			}

			@Override
			public QuestGoal build() {
				checkAttributesBound();
				return new HuntingQuestGoal(null, getHuntedClass(), goalAmount, current);
			}
		};
	}
}
