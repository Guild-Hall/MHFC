package mhfc.net.common.quests.api;

import mhfc.net.common.util.io.DelegatedConvertible;
import net.minecraft.util.ResourceLocation;

public class QuestRewardDelegate extends DelegatedConvertible<IQuestReward, ResourceLocation> {

	public QuestRewardDelegate(ResourceLocation typeKey, IQuestReward reward) {
		super(typeKey, reward);
	}

}
