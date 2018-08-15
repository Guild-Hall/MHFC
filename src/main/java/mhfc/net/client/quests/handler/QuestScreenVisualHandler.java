package mhfc.net.client.quests.handler;

import mhfc.net.MHFCMain;
import mhfc.net.client.quests.MHFCRegQuestVisual;
import mhfc.net.client.quests.api.IMissionInformation;
import mhfc.net.common.network.handler.ThreadSafeMessageHandler;
import mhfc.net.common.network.message.quest.MessageMissionUpdate;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class QuestScreenVisualHandler extends ThreadSafeMessageHandler<MessageMissionUpdate, IMessage> {
	@Override
	protected void handleLater(MessageMissionUpdate message, MessageContext ctx) {
		String missionID = message.getMissionId();
		IMissionInformation missionInformation = MHFCRegQuestVisual.getMissionInformation(missionID);
		if (missionInformation == null) {
			MHFCMain.logger().error("Receiving update message for invalid mission {}, ignoring", missionID);
		} else {
			missionInformation.updateProperties(message.getUpdateData());
		}
	}
}
