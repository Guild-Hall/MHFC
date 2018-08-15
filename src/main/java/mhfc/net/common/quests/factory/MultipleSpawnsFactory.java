package mhfc.net.common.quests.factory;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import mhfc.net.common.quests.api.ISpawnInformation;
import mhfc.net.common.quests.api.ISpawnInformationFactory;
import mhfc.net.common.quests.api.SpawnInformationDelegate;
import mhfc.net.common.quests.spawns.MultipleSpawns;

public class MultipleSpawnsFactory implements ISpawnInformationFactory {

	@Override
	public JsonElement convertFrom(ISpawnInformation value, JsonSerializationContext context) {
		MultipleSpawns multiReward = (MultipleSpawns) value;
		List<SpawnInformationDelegate> spawns = multiReward.getSpawns();

		JsonArray spawnsArray = new JsonArray();
		for (SpawnInformationDelegate spawn : spawns) {
			JsonElement serializedReward = context.serialize(spawn, SpawnInformationDelegate.class);
			spawnsArray.add(serializedReward);
		}
		return wrap(spawnsArray);
	}

	@Override
	public ISpawnInformation convertTo(JsonElement value, JsonDeserializationContext context) {
		JsonArray rewardArray = unwrap(value.getAsJsonObject());
		List<SpawnInformationDelegate> rewards = new ArrayList<>(rewardArray.size());

		for (JsonElement serializedReward : rewardArray) {
			SpawnInformationDelegate reward = context.deserialize(serializedReward, SpawnInformationDelegate.class);
			rewards.add(reward);
		}
		return new MultipleSpawns(rewards);
	}

	public static JsonObject wrap(JsonArray spawnsArray) {
		JsonObject serialized = new JsonObject();
		serialized.add("spawns", spawnsArray);
		return serialized;
	}

	public static JsonArray unwrap(JsonObject value) {
		return value.get("spawns").getAsJsonArray();
	}
}
