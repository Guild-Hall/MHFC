package mhfc.net.common.network.serialization;

import java.lang.reflect.Type;

import com.google.gson.*;

import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.quests.api.IQuestFactory;
import mhfc.net.common.quests.api.QuestDescription;
import mhfc.net.common.quests.api.QuestFactory;
import mhfc.net.common.util.MHFCJsonUtils;
import net.minecraft.util.JsonUtils;

public class QuestSerializer
	implements
		JsonDeserializer<QuestDescription>,
		JsonSerializer<QuestDescription> {

	@Override
	public QuestDescription deserialize(JsonElement json, Type typeOfT,
		JsonDeserializationContext context) throws JsonParseException {
		JsonObject jsonAsObject = JsonUtils.getJsonElementAsJsonObject(json,
			"quest");
		if (!MHFCJsonUtils.objectFieldTypeIsString(jsonAsObject,
			MHFCQuestBuildRegistry.KEY_TYPE)) {
			throw new JsonParseException("Quest has no valid type, was "
				+ jsonAsObject.toString());
		}
		if (!MHFCJsonUtils.objectFieldTypeIsObject(jsonAsObject,
			MHFCQuestBuildRegistry.KEY_DATA)) {
			throw new JsonParseException("Quest has no valid data");
		}
		String type = MHFCJsonUtils.getJsonObjectStringFieldValueOrDefault(
			jsonAsObject, MHFCQuestBuildRegistry.KEY_TYPE, "default");
		IQuestFactory qFactory = QuestFactory.getQuestFactory(type);
		return qFactory.buildQuestDescription(jsonAsObject.get(
			MHFCQuestBuildRegistry.KEY_DATA), context);
	}

	@Override
	public JsonElement serialize(QuestDescription src, Type typeOfSrc,
		JsonSerializationContext context) {
		String type = src.getType();
		IQuestFactory qFactory = QuestFactory.getQuestFactory(type);
		JsonObject holder = new JsonObject();
		holder.add(MHFCQuestBuildRegistry.KEY_DATA, qFactory.serialize(src,
			context));
		holder.addProperty(MHFCQuestBuildRegistry.KEY_TYPE, type);
		return holder;
	}
}
