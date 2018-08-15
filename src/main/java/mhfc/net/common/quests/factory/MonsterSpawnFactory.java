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
		ResourceLocation type = spawner.getSpawnType();
		ResourceLocation location = spawner.getSpawnLocation();
		Optional<Integer> spawnedCount = spawner.getSpawnCount();

		JsonObject json = new JsonObject();
		json.add("mob", context.serialize(type));
		json.add("location", context.serialize(location));
		spawnedCount.ifPresent(c -> json.add("count", new JsonPrimitive(c)));
		return json;
	}

	@Override
	public ISpawnInformation convertTo(JsonElement value, JsonDeserializationContext context) {
		JsonObject object = value.getAsJsonObject();
		ResourceLocation mobName = context.deserialize(object.get("mob"), ResourceLocation.class);
		ResourceLocation location = context.deserialize(object.get("location"), ResourceLocation.class);
		JsonElement count = object.get("count");
		if (count == null) {
			return new MonsterSpawn(mobName, location);
		}
		return new MonsterSpawn(mobName, count.getAsNumber().intValue(), location);
	}

}
