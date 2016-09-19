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
		FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
		return instantReply(message, ctx);
	}

	protected abstract void handle(REQ message, MessageContext ctx);

	protected REPLY instantReply(REQ message, MessageContext ctx) {
		return null;
	}
}
