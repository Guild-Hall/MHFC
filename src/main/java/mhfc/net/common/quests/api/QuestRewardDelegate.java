package mhfc.net.common.quests.api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;

import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.quests.rewards.NullReward;
import mhfc.net.common.util.io.DelegatedConvertible;
import net.minecraft.util.ResourceLocation;

public class QuestRewardDelegate extends DelegatedConvertible<IQuestReward, ResourceLocation> {
	public static final QuestRewardDelegate MISSING = new QuestRewardDelegate(
			new ResourceLocation(MHFCQuestBuildRegistry.REWARD_NULL_TYPE),
			new NullReward());

	public QuestRewardDelegate(ResourceLocation typeKey, IQuestReward reward) {
		super(typeKey, reward);
	}

	/**
	 * If element is null, converts a JsonNull instead. If the result is null, returns {@link #MISSING}
	 *
	 * @param element
	 * @param context
	 * @return
	 */
	public static final QuestRewardDelegate deserialize(JsonElement element, JsonDeserializationContext context) {
		if (element == null) {
			// Gson context.deserialize will return null when element is null, which is not what we want
			element = JsonNull.INSTANCE;
		}
		QuestRewardDelegate reward = context.deserialize(element, QuestRewardDelegate.class);
		return reward == null ? MISSING : reward;
	}

}
