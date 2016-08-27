package mhfc.net.client.quests;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import mhfc.net.common.network.message.quest.MessageMissionUpdate;

public class QuestScreenVisualHandler implements IMessageHandler<MessageMissionUpdate, IMessage> {
	@Override
	public IMessage onMessage(MessageMissionUpdate message, MessageContext ctx) {
		MHFCRegQuestVisual.getMissionInformation(message.getMissionId()).updateProperties(message.getUpdateData());
		return null;
	}
}
