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
		if (discriminator == 255) {
			MHFCMain.logger
					.error("Tried to register more than 256 message types");
			return;
		}
		MHFCMain.logger
				.info(String
						.format("Registered message %s with Handler %s for Side %s at Side %s with discriminator 0x%x",
								requestMessageType, messageHandler, side,
								MHFCMain.proxy.getSide(), discriminator));
		networkPipe.registerMessage(messageHandler, requestMessageType,
				discriminator++, side);
	}
}
