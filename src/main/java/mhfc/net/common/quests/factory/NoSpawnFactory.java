package mhfc.net.common.quests.factory;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonSerializationContext;

import mhfc.net.common.quests.api.ISpawnInformation;
import mhfc.net.common.quests.api.ISpawnInformationFactory;
import mhfc.net.common.quests.spawns.NoSpawn;

public class NoSpawnFactory implements ISpawnInformationFactory {
	@Override
	public JsonElement convertFrom(ISpawnInformation value, JsonSerializationContext context) {
		return JsonNull.INSTANCE;
	}

	@Override
	public ISpawnInformation convertTo(JsonElement value, JsonDeserializationContext context) {
		return NoSpawn.INSTANCE;
	}
}
