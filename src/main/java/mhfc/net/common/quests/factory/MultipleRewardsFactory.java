package mhfc.net.common.quests.factory;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;

import mhfc.net.common.quests.api.IQuestReward;
import mhfc.net.common.quests.api.IQuestRewardFactory;
import mhfc.net.common.quests.api.QuestRewardDelegate;
import mhfc.net.common.quests.rewards.MultipleRewards;

public class MultipleRewardsFactory implements IQuestRewardFactory {

	@Override
	public JsonElement convertFrom(IQuestReward value, JsonSerializationContext context) {
		MultipleRewards multiReward = (MultipleRewards) value;
		List<QuestRewardDelegate> rewards = multiReward.getRewards();

		JsonArray rewardArray = new JsonArray();
		for (QuestRewardDelegate reward : rewards) {
			JsonElement serializedReward = context.serialize(reward, QuestRewardDelegate.class);
			rewardArray.add(serializedReward);
		}
		return rewardArray;
	}

	@Override
	public IQuestReward convertTo(JsonElement value, JsonDeserializationContext context) {
		JsonArray rewardArray = value.getAsJsonArray();
		List<QuestRewardDelegate> rewards = new ArrayList<>(rewardArray.size());

		for (JsonElement serializedReward : rewardArray) {
			QuestRewardDelegate reward = context.deserialize(serializedReward, QuestRewardDelegate.class);
			rewards.add(reward);
		}
		return new MultipleRewards(rewards);
	}

}
