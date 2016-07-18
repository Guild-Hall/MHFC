package mhfc.net.client.quests;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import mhfc.net.common.network.packet.MessageQuestVisual;
import mhfc.net.common.quests.api.IVisualInformation;

public class QuestScreenVisualHandler implements IMessageHandler<MessageQuestVisual, IMessage> {
	@Override
	public IMessage onMessage(MessageQuestVisual message, MessageContext ctx) {
		IVisualInformation visual = message.getInformation();
		switch (message.getMessageType()) {
		case PERSONAL_QUEST:
			MHFCRegQuestVisual.setPlayerVisual(visual);
			break;
		case RUNNING_QUEST:
			modifyRunningQuestList(visual, message);
			break;
		}
		return null;
	}
}
