package mhfc.net.common.quests.factory;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;

import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.quests.IVisualInformation;
import mhfc.net.common.quests.QuestRunningInformation;
import mhfc.net.common.quests.api.IVisualInformationFactory;
import mhfc.net.common.quests.api.QuestFactory;

public class QuestRunningInformationFactory
	implements
		IVisualInformationFactory {

	@Override
	public IVisualInformation buildInformation(JsonElement json,
		JsonDeserializationContext context) {
		// FIXME Return a running information
		return new QuestRunningInformation(QuestFactory
			.getQuestVisualInformationFactory(
				MHFCQuestBuildRegistry.VISUAL_DEFAULT).buildInformation(json,
					context), "Hey", "Hey");
	}

	@Override
	public JsonElement serialize(IVisualInformation information,
		JsonSerializationContext context) {
		// FIXME Serialize as running information instead
		return QuestFactory.getQuestVisualInformationFactory(
			MHFCQuestBuildRegistry.VISUAL_DEFAULT).serialize(information,
				context);
	}

}
