package mhfc.net.common.quests.factory;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;

import mhfc.net.common.quests.api.IQuestReward;
import mhfc.net.common.quests.api.IQuestRewardFactory;
import mhfc.net.common.quests.rewards.MoneyReward;

public class MoneyRewardFactory implements IQuestRewardFactory {

	@Override
	public JsonElement convertFrom(IQuestReward value, JsonSerializationContext context) {
		MoneyReward reward = (MoneyReward) value;
		return new JsonPrimitive(Integer.valueOf(reward.getAmount()));
	}

	@Override
	public IQuestReward convertTo(JsonElement value, JsonDeserializationContext context) {
		return new MoneyReward(value.getAsInt());
	}

}
