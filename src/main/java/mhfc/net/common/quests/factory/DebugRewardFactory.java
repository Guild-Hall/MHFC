package mhfc.net.common.quests.factory;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonSerializationContext;

import mhfc.net.common.quests.api.IQuestReward;
import mhfc.net.common.quests.api.IQuestRewardFactory;
import mhfc.net.common.quests.rewards.DebugReward;

public class DebugRewardFactory implements IQuestRewardFactory {

	@Override
	public JsonElement convertFrom(IQuestReward value, JsonSerializationContext context) {
		return JsonNull.INSTANCE;
	}

	@Override
	public IQuestReward convertTo(JsonElement value, JsonDeserializationContext context) {
		return new DebugReward();
	}

}
