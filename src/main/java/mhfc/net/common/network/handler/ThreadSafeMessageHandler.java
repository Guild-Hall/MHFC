package mhfc.net.common.network.handler;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public abstract class ThreadSafeMessageHandler<REQ extends IMessage, REPLY extends IMessage>
		implements
		IMessageHandler<REQ, REPLY> {

	@Override
	public REPLY onMessage(REQ message, MessageContext ctx) {
		FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handleLater(message, ctx));
		return instantReply(message, ctx);
	}

	/**
	 * Handles the received method when the game thread executes scheduled tasks in the main loop
	 * 
	 * @param message
	 * @param ctx
	 */
	protected abstract void handleLater(REQ message, MessageContext ctx);

	protected REPLY instantReply(
			REQ message,
			MessageContext ctx) {
		return null;
	}
}
