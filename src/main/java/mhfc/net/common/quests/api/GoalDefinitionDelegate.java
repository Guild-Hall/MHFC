package mhfc.net.common.quests.api;

import mhfc.net.common.util.io.DelegatedConvertible;
import net.minecraft.util.ResourceLocation;

public class GoalDefinitionDelegate extends DelegatedConvertible<IGoalDefinition, ResourceLocation> {

	public GoalDefinitionDelegate(ResourceLocation typeKey, IGoalDefinition goalDefinition) {
		super(typeKey, goalDefinition);
	}

}
