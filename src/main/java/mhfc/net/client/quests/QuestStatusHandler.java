package mhfc.net.client.quests;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import mhfc.net.MHFCMain;
import mhfc.net.common.network.message.quest.MessageMissionStatus;

public class QuestStatusHandler implements IMessageHandler<MessageMissionStatus, IMessage> {
	@Override
	public IMessage onMessage(MessageMissionStatus message, MessageContext ctx) {
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
		return null;
	}
}
