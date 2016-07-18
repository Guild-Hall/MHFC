package mhfc.net.common.quests.factory;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.quests.QuestRunningInformation;
import mhfc.net.common.quests.api.IVisualInformation;
import mhfc.net.common.quests.api.IVisualInformationFactory;
import mhfc.net.common.quests.api.QuestFactory;
import mhfc.net.common.util.MHFCJsonUtils;

public class QuestRunningInformationFactory implements IVisualInformationFactory {

	public static final String KEY_LONG_STATUS = "longDescription";
	public static final String KEY_SHORT_STATUS = "shortDescription";

	@Override
	public IVisualInformation buildInformation(JsonElement json, JsonDeserializationContext context) {
		IVisualInformation base = QuestFactory.getQuestVisualInformationFactory(MHFCQuestBuildRegistry.VISUAL_DEFAULT)
				.buildInformation(json, context);
		JsonObject jsonAsObject = json.getAsJsonObject();
		String shortStatus = MHFCJsonUtils.getJsonObjectStringFieldValueOrDefault(jsonAsObject, KEY_SHORT_STATUS, "");
		String longStatus = MHFCJsonUtils.getJsonObjectStringFieldValueOrDefault(jsonAsObject, KEY_LONG_STATUS, "");
		return new QuestRunningInformation(base, shortStatus, longStatus);
	}

	@Override
	public JsonElement serialize(IVisualInformation information, JsonSerializationContext context) {
		QuestRunningInformation runningInfo = (QuestRunningInformation) information;
		// FIXME Serialize as running information instead
		JsonObject holder = QuestFactory.getQuestVisualInformationFactory(MHFCQuestBuildRegistry.VISUAL_DEFAULT)
				.serialize(information, context).getAsJsonObject();
		holder.addProperty(KEY_LONG_STATUS, runningInfo.getLongStatus());
		holder.addProperty(KEY_SHORT_STATUS, runningInfo.getShortStatus());
		return holder;
	}

}
