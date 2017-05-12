package mhfc.net.common.quests.rewards;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import mhfc.net.common.quests.api.IQuestReward;
import mhfc.net.common.quests.api.QuestRewardDelegate;
import net.minecraft.entity.player.EntityPlayerMP;

public class MultipleRewards implements IQuestReward {

	private final List<QuestRewardDelegate> rewards;

	public MultipleRewards(Collection<QuestRewardDelegate> rewards) {
		this.rewards = new ArrayList<>(rewards);
	}

	@Override
	public void grantReward(EntityPlayerMP player) {
		for (QuestRewardDelegate reward : rewards) {
			reward.getValue().grantReward(player);
		}
	}

	public List<QuestRewardDelegate> getRewards() {
		return Collections.unmodifiableList(rewards);
	}

}
