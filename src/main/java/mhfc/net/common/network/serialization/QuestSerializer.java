package mhfc.net.common.network.serialization;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.quests.QuestFactories;
import mhfc.net.common.quests.api.IQuestDefinitionFactory;
import mhfc.net.common.quests.api.QuestDefinition;
import mhfc.net.common.util.MHFCJsonUtils;
import net.minecraft.util.JsonUtils;

public class QuestSerializer implements JsonDeserializer<QuestDefinition>, JsonSerializer<QuestDefinition> {

	@Override
	public QuestDefinition deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jsonAsObject = JsonUtils.getJsonElementAsJsonObject(json, "quest");
		if (!MHFCJsonUtils.objectFieldTypeIsString(jsonAsObject, MHFCQuestBuildRegistry.KEY_TYPE)) {
			throw new JsonParseException("Quest has no valid type, was " + jsonAsObject.toString());
		}
		String type = MHFCJsonUtils
				.getJsonObjectStringFieldValueOrDefault(jsonAsObject, MHFCQuestBuildRegistry.KEY_TYPE, "default");
		IQuestDefinitionFactory qFactory = QuestFactories.getQuestFactory(type);
		JsonElement dataObject = jsonAsObject.get(MHFCQuestBuildRegistry.KEY_DATA);
		return qFactory.buildQuestDescription(dataObject, context);
	}

	@Override
	public JsonElement serialize(QuestDefinition src, Type typeOfSrc, JsonSerializationContext context) {
		String type = src.getSerializerType();
		IQuestDefinitionFactory qFactory = QuestFactories.getQuestFactory(type);
		JsonObject holder = new JsonObject();
		holder.add(MHFCQuestBuildRegistry.KEY_DATA, qFactory.serialize(src, context));
		holder.addProperty(MHFCQuestBuildRegistry.KEY_TYPE, type);
		return holder;
	}
}
