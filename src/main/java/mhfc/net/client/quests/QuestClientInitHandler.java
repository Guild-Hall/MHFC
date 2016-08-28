package mhfc.net.client.quests;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import mhfc.net.common.network.message.quest.MessageQuestInit;

public class QuestClientInitHandler implements IMessageHandler<MessageQuestInit, IMessage> {
	@Override
	public IMessage onMessage(MessageQuestInit message, MessageContext ctx) {
		MHFCRegQuestVisual.getService().onInitializationMessage(message);
		MHFCRegQuestVisual.getService().logStats();
		return null;
	}
}
