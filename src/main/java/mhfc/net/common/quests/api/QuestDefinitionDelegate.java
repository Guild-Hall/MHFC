package mhfc.net.common.quests.api;

import mhfc.net.common.util.io.DelegatedConvertible;
import net.minecraft.util.ResourceLocation;

public class QuestDefinitionDelegate extends DelegatedConvertible<IQuestDefinition, ResourceLocation> {

	public QuestDefinitionDelegate(ResourceLocation typeKey, IQuestDefinition questDefinition) {
		super(typeKey, questDefinition);
	}

}
