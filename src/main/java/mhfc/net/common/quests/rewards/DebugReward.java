package mhfc.net.common.quests.rewards;

import mhfc.net.MHFCMain;
import mhfc.net.common.quests.api.IQuestReward;
import net.minecraft.entity.player.EntityPlayerMP;

public class DebugReward implements IQuestReward {

	@Override
	public void grantReward(EntityPlayerMP player) {
		MHFCMain.logger().debug("Giving debug reward to " + player);
	}

}
