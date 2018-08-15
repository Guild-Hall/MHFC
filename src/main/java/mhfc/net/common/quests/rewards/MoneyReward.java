package mhfc.net.common.quests.rewards;

import mhfc.net.MHFCMain;
import mhfc.net.common.quests.api.IQuestReward;
import net.minecraft.entity.player.EntityPlayerMP;

public class MoneyReward implements IQuestReward {

	private final int amount;

	public MoneyReward(int amount) {
		this.amount = amount;
	}

	@Override
	public void grantReward(EntityPlayerMP player) {
		MHFCMain.logger().warn("Money rewards are currently unsupported.");
	}

	public int getAmount() {
		return amount;
	}

}
