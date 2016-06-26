package mhfc.net.common.network.serialization;

import java.lang.reflect.Type;

import com.google.gson.*;

import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.quests.api.GoalDescription;
import mhfc.net.common.quests.api.IGoalFactory;
import mhfc.net.common.quests.api.QuestFactory;
import mhfc.net.common.util.MHFCJsonUtils;
import net.minecraft.util.JsonUtils;

public class GoalSerializer
	implements
		JsonDeserializer<GoalDescription>,
		JsonSerializer<GoalDescription> {

	@Override
	public GoalDescription deserialize(JsonElement json, Type typeOfT,
		JsonDeserializationContext context) throws JsonParseException {
		JsonObject jsonAsObject = JsonUtils.getJsonElementAsJsonObject(json,
			"goal");
		if (!MHFCJsonUtils.objectFieldTypeIsString(jsonAsObject,
			MHFCQuestBuildRegistry.KEY_TYPE)) {
			throw new JsonParseException("Goal has no valid type, was "
				+ jsonAsObject.toString());
		}
		if (!MHFCJsonUtils.objectFieldTypeIsObject(jsonAsObject,
			MHFCQuestBuildRegistry.KEY_DATA)) {
			throw new JsonParseException("Goal has no valid data");
		}
		String type = JsonUtils.getJsonObjectStringFieldValue(jsonAsObject,
			"type");
		IGoalFactory gFactory = QuestFactory.getGoalFactory(type);
		return gFactory.buildGoalDescription(jsonAsObject.get(
			MHFCQuestBuildRegistry.KEY_DATA), context);
	}

	@Override
	public JsonElement serialize(GoalDescription src, Type typeOfSrc,
		JsonSerializationContext context) {
		JsonObject descriptionAsJson = new JsonObject();
		String type = src.getGoalType();
		descriptionAsJson.addProperty(MHFCQuestBuildRegistry.KEY_TYPE, type);
		IGoalFactory gFactory = QuestFactory.getGoalFactory(type);
		JsonElement data = gFactory.serialize(src, context);
		descriptionAsJson.add(MHFCQuestBuildRegistry.KEY_DATA, data);
		return descriptionAsJson;
	}
}
