package mhfc.net.client.quests.handler;

import mhfc.net.MHFCMain;
import mhfc.net.client.quests.MHFCRegQuestVisual;
import mhfc.net.common.network.handler.ThreadSafeMessageHandler;
import mhfc.net.common.network.message.quest.MessageMissionStatus;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class QuestStatusHandler extends ThreadSafeMessageHandler<MessageMissionStatus, IMessage> {
	@Override
	protected void handleLater(MessageMissionStatus message, MessageContext ctx) {
		switch (message.getStatusType()) {
		case MISSION_CREATED:
			MHFCRegQuestVisual.startNewMission(message.getQuestID(), message.getMissionID());
			break;
		case MISSION_JOINED:
			MHFCRegQuestVisual.joinMission(message.getMissionID());
			break;
		case MISSION_DEPARTED:
			MHFCRegQuestVisual.departMission(message.getMissionID());
		case MISSION_ENDED:
			MHFCRegQuestVisual.endMission(message.getMissionID());
		default:
			MHFCMain.logger().warn("Ingoring invalid mission status update");
		}
	}
}
