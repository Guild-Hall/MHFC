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
import mhfc.net.common.quests.api.IVisualInformation;
import mhfc.net.common.quests.api.IVisualInformationFactory;
import mhfc.net.common.quests.api.QuestFactory;
import net.minecraft.util.JsonUtils;

public class QuestVisualSerializer implements JsonSerializer<IVisualInformation>, JsonDeserializer<IVisualInformation> {

	public QuestVisualSerializer() {}

	@Override
	public JsonElement serialize(IVisualInformation src, Type typeOfSrc, JsonSerializationContext context) {
		String serializerType = src.getSerializerType();
		IVisualInformationFactory factory = QuestFactory.getQuestVisualInformationFactory(serializerType);
		JsonElement serialized = factory.serialize(src, context);
		JsonObject holder = new JsonObject();
		holder.add(MHFCQuestBuildRegistry.KEY_DATA, serialized);
		holder.addProperty(MHFCQuestBuildRegistry.KEY_TYPE, serializerType);
		return holder;
	}

	@Override
	public IVisualInformation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jsonAsObject = json.getAsJsonObject();
		String serializerType = JsonUtils.getJsonObjectStringFieldValue(jsonAsObject, MHFCQuestBuildRegistry.KEY_TYPE);
		JsonElement data = jsonAsObject.get(MHFCQuestBuildRegistry.KEY_DATA);
		IVisualInformationFactory factory = QuestFactory.getQuestVisualInformationFactory(serializerType);
		return factory.buildInformation(data, context);
	}

}
