package mhfc.net.common.network.message;

import mhfc.net.common.ai.AIActionManager;
import mhfc.net.common.ai.IManagedActions;
import net.minecraft.entity.EntityLiving;
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
		IManagedActions<?> entity = message.getEntity();
		EntityLiving entityLiving = message.getEntityLiving();
		if (entity == null)
			return null;
		entityLiving.setAttackTarget(message.getTarget());
		AIActionManager<?> attackManger = entity.getAttackManager();
		if (attackManger == null)
			return null;
		attackManger.setAttack(message.getAttackIndex());
		return null;
	}
}
