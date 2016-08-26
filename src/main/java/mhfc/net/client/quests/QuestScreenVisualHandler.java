package mhfc.net.client.quests;

import java.util.Optional;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import mhfc.net.common.network.packet.MessageQuestVisual;
import mhfc.net.common.quests.api.IVisualDefinition;

public class QuestScreenVisualHandler implements IMessageHandler<MessageQuestVisual, IMessage> {
	@Override
	public IMessage onMessage(MessageQuestVisual message, MessageContext ctx) {
		IVisualDefinition visual = message.getInformation();
		switch (message.getMessageType()) {
		case PERSONAL_QUEST:
			MHFCRegQuestVisual.setPlayerVisual(Optional.of(visual));
			break;
		case RUNNING_QUEST:
			modifyRunningQuestList(visual, message);
			break;
		}
		return null;
	}
}
