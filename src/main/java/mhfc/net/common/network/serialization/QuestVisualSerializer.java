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
import mhfc.net.common.quests.api.IVisualDefinition;
import mhfc.net.common.quests.api.IVisualInformationFactory;
import mhfc.net.common.quests.api.QuestFactories;
import net.minecraft.util.JsonUtils;

public class QuestVisualSerializer implements JsonSerializer<IVisualDefinition>, JsonDeserializer<IVisualDefinition> {

	public QuestVisualSerializer() {}

	@SuppressWarnings("unchecked")
	private <T extends IVisualDefinition> JsonElement invokeFactory(
			IVisualInformationFactory<T> factory,
			IVisualDefinition info,
			JsonSerializationContext context) {
		return factory.serialize((T) info, context);
	}

	@Override
	public JsonElement serialize(IVisualDefinition src, Type typeOfSrc, JsonSerializationContext context) {
		String serializerType = src.getSerializerType();
		IVisualInformationFactory<?> factory = QuestFactories.getQuestVisualInformationFactory(serializerType);
		JsonElement serialized = invokeFactory(factory, src, context);
		JsonObject holder = new JsonObject();
		holder.add(MHFCQuestBuildRegistry.KEY_DATA, serialized);
		holder.addProperty(MHFCQuestBuildRegistry.KEY_TYPE, serializerType);
		return holder;
	}

	@Override
	public IVisualDefinition deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jsonAsObject = json.getAsJsonObject();
		String serializerType = JsonUtils.getJsonObjectStringFieldValue(jsonAsObject, MHFCQuestBuildRegistry.KEY_TYPE);
		JsonElement data = jsonAsObject.get(MHFCQuestBuildRegistry.KEY_DATA);
		IVisualInformationFactory<?> factory = QuestFactories.getQuestVisualInformationFactory(serializerType);
		return factory.buildInformation(data, context);
	}

}
