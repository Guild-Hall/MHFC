package mhfc.net.common.network.message;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.IManagedActions;
import net.minecraft.entity.EntityLiving;

@SuppressWarnings("rawtypes")
// Cause java, we need this
public class MessageAttackHandler implements IMessageHandler<MessageAIAction, IMessage> {
	@SuppressWarnings("unchecked")
	@Override
	public IMessage onMessage(MessageAIAction message, MessageContext ctx) {
		IManagedActions<?> entity = message.getEntity();
		EntityLiving entityLiving = message.getEntityLiving();
		if (entity == null)
			return null;
		entityLiving.setAttackTarget(message.getTarget());
		IActionManager<?> attackManger = entity.getActionManager();
		if (attackManger == null)
			return null;
		attackManger.receiveUpdate(message);
		return null;
	}
}
