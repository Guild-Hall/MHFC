package mhfc.net.common.network;

import io.netty.channel.ChannelHandler;
import mhfc.net.MHFCMain;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

/**
 * Packet pipeline class. Directs all registered packet data to be handled by
 * the packets themselves.
 *
 * @author WorldSEnder
 */
@ChannelHandler.Sharable
public class PacketPipeline {

	public static final SimpleNetworkWrapper networkPipe = new SimpleNetworkWrapper(
			"MHFC");
	private int discriminator = 0xFF;

	public <Req extends IMessage, Reply extends IMessage> void registerPacket(
			Class<? extends IMessageHandler<Req, Reply>> messageHandler,
			Class<Req> requestMessageType, Side side) {
		if (discriminator <= 0) {
			MHFCMain.logger
					.error("Tried to register more than 256 message types");
			return;
		}
		networkPipe.registerMessage(messageHandler, requestMessageType,
				discriminator--, side);
	}
}
