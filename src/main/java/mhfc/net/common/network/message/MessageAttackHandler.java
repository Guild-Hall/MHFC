package mhfc.net.common.network.message;

import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.IManagedActions;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

@SuppressWarnings("rawtypes")
// Cause java, we need this
public class MessageAttackHandler implements IMessageHandler<MessageAIAction, IMessage> {
	@Override
	public IMessage onMessage(MessageAIAction message, MessageContext ctx) {
		IManagedActions<?> entity = message.getEntity();
		EntityLiving entityLiving = message.getEntityLiving();
		if (entity == null) {
			return null;
		}
		entityLiving.setAttackTarget(message.getTarget());
		IActionManager<?> attackManger = entity.getActionManager();
		if (attackManger == null) {
			return null;
		}
		attackManger.receiveUpdate(message);
		return null;
	}
}
