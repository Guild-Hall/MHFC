package mhfc.net.client.quests;

import mhfc.net.common.network.message.quest.MessageMissionUpdate;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class QuestScreenVisualHandler implements IMessageHandler<MessageMissionUpdate, IMessage> {
	@Override
	public IMessage onMessage(MessageMissionUpdate message, MessageContext ctx) {
		MHFCRegQuestVisual.getMissionInformation(message.getMissionId()).updateProperties(message.getUpdateData());
		return null;
	}
}
