package mhfc.net.client.quests.handler;

import mhfc.net.client.quests.MHFCRegQuestVisual;
import mhfc.net.common.network.handler.ThreadSafeMessageHandler;
import mhfc.net.common.network.message.quest.MessageQuestInit;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class QuestClientInitHandler extends ThreadSafeMessageHandler<MessageQuestInit, IMessage> {
	@Override
	protected void handleLater(MessageQuestInit message, MessageContext ctx) {
		MHFCRegQuestVisual.getService().onInitializationMessage(message);
		MHFCRegQuestVisual.getService().logStats();
	}
}
