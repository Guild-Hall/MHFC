package mhfc.net.common.quests.rewards;

import mhfc.net.common.quests.api.IQuestReward;
import net.minecraft.entity.player.EntityPlayerMP;

public class NullReward implements IQuestReward {

	@Override
	public void grantReward(EntityPlayerMP player) {
	}

}
