package mhfc.net.common.network;

import java.util.concurrent.atomic.AtomicInteger;

import mhfc.net.MHFCMain;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Packet pipeline class. Directs all registered packet data to be handled by the packets themselves.
 *
 * @author WorldSEnder
 */
public class PacketPipeline {
	public static final SimpleNetworkWrapper networkPipe = new SimpleNetworkWrapper("MHFC");

	private static AtomicInteger discriminator = new AtomicInteger(0);

	private static int nextID() {
		int typeID = discriminator.getAndIncrement();
		if (typeID > 255) {
			throw MHFCMain.logger()
					.throwing(new IllegalStateException("Tried to register more than 256 message types"));
		}
		return typeID;
	}

	public static <REQ extends IMessage, ANS extends IMessage> void registerPacket(
			Class<? extends IMessageHandler<REQ, ANS>> messageHandler,
			Class<REQ> requestMessageType,
			Side side) {
		int typeID = nextID();
		networkPipe.registerMessage(messageHandler, requestMessageType, typeID, side);
	}

	public static <REQ extends IMessage> void registerPacket(
			IMessageHandler<REQ, ?> messageHandler,
			Class<REQ> requestMessageType,
			Side side) {
		int typeID = nextID();
		networkPipe.registerMessage(messageHandler, requestMessageType, typeID, side);
	}
}
