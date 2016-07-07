package mhfc.net.common.network;

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
public class PacketPipeline {
	public static final SimpleNetworkWrapper networkPipe = new SimpleNetworkWrapper(
			"MHFC");

	private static int discriminator = 0;

	public static <REQ extends IMessage, ANS extends IMessage> void registerPacket(
			Class<? extends IMessageHandler<REQ, ANS>> messageHandler,
			Class<REQ> requestMessageType, Side side) {
		if (discriminator > 255) {
			throw MHFCMain.logger().throwing(new IllegalStateException(
					"Tried to register more than 256 message types"));
		}
		networkPipe.registerMessage(messageHandler, requestMessageType,
				discriminator++, side);
	}
}
