package mhfc.net.client.network.handler;

import mhfc.net.client.quests.MHFCRegQuestVisual;
import mhfc.net.common.network.message.quest.MessageQuestInit;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class QuestClientInitHandler implements IMessageHandler<MessageQuestInit, IMessage> {
	@Override
	public IMessage onMessage(MessageQuestInit message, MessageContext ctx) {
		MHFCRegQuestVisual.getService().onInitializationMessage(message);
		MHFCRegQuestVisual.getService().logStats();
		return null;
	}
}
