package mhfc.net.common.quests.factory;

import java.util.Optional;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;

import mhfc.net.common.quests.api.ISpawnInformation;
import mhfc.net.common.quests.api.ISpawnInformationFactory;
import mhfc.net.common.quests.spawns.MonsterSpawn;
import net.minecraft.util.ResourceLocation;

public class MonsterSpawnFactory implements ISpawnInformationFactory {

	@Override
	public JsonElement convertFrom(ISpawnInformation value, JsonSerializationContext context) {
		MonsterSpawn spawner = (MonsterSpawn) value;
		String mobType = spawner.getSpawnType().toString();
		Optional<Integer> spawnedCount = spawner.getSpawnCount();

		JsonObject json = new JsonObject();
		json.add("mob", new JsonPrimitive(mobType));
		spawnedCount.ifPresent(c -> json.add("count", new JsonPrimitive(c)));
		return json;
	}

	@Override
	public ISpawnInformation convertTo(JsonElement value, JsonDeserializationContext context) {
		JsonObject object = value.getAsJsonObject();
		ResourceLocation mobName = new ResourceLocation(object.get("mob").getAsString());
		JsonElement count = object.get("count");
		if (count == null) {
			return new MonsterSpawn(mobName);
		}
		return new MonsterSpawn(mobName, count.getAsNumber().intValue());
	}

}
