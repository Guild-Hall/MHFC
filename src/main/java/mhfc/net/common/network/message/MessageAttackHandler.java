package mhfc.net.common.network.message;

import mhfc.net.common.ai.AIAttackManager;
import mhfc.net.common.ai.IMangedAttacks;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

@SuppressWarnings("rawtypes")
// Cause java, we need this
public class MessageAttackHandler
		implements
			IMessageHandler<MessageAIAttack, IMessage> {
	@Override
	public IMessage onMessage(MessageAIAttack message, MessageContext ctx) {
		IMangedAttacks<?> entity = message.getEntity();
		if (entity == null)
			return null;
		AIAttackManager<?> attackManger = entity.getAttackManager();
		if (attackManger == null)
			return null;
		attackManger.setAttack(message.getAttackIndex());
		return null;
	}
}