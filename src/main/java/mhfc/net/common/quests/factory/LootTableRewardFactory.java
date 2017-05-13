package mhfc.net.common.quests.factory;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;

import mhfc.net.common.quests.api.IQuestReward;
import mhfc.net.common.quests.api.IQuestRewardFactory;
import mhfc.net.common.quests.rewards.LootTableReward;
import net.minecraft.util.ResourceLocation;

public class LootTableRewardFactory implements IQuestRewardFactory {

	@Override
	public JsonElement convertFrom(IQuestReward value, JsonSerializationContext context) {
		LootTableReward reward = (LootTableReward) value;
		return new JsonPrimitive(reward.getLootTableLocation().toString());
	}

	@Override
	public IQuestReward convertTo(JsonElement value, JsonDeserializationContext context) {
		ResourceLocation loottableLoc = new ResourceLocation(value.getAsString());
		return new LootTableReward(loottableLoc);
	}

}
