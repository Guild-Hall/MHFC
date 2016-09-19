package mhfc.net.common.network.handler;

import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.IManagedActions;
import mhfc.net.common.network.message.MessageAIAction;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

@SuppressWarnings("rawtypes")
// Cause java, we need this
public class MessageAttackHandler extends ThreadSafeMessageHandler<MessageAIAction, IMessage> {
	@Override
	public void handle(MessageAIAction message, MessageContext ctx) {
		IManagedActions<?> entity = message.getEntity();
		EntityLiving entityLiving = message.getEntityLiving();
		if (entity == null) {
			return;
		}
		entityLiving.setAttackTarget(message.getTarget());
		IActionManager<?> attackManger = entity.getActionManager();
		if (attackManger == null) {
			return;
		}
		attackManger.receiveUpdate(message);
	}
}
