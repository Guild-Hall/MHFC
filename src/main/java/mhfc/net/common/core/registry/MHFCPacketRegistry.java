package mhfc.net.common.core.registry;

import mhfc.net.common.network.PacketPipeline;
import mhfc.net.common.network.message.MessageAIAttack;
import mhfc.net.common.network.message.MessageAttackHandler;
import cpw.mods.fml.relauncher.Side;

public class MHFCPacketRegistry {

	public static void init() {
		PacketPipeline.registerPacket(MessageAttackHandler.class,
				MessageAIAttack.class, Side.CLIENT);
	}
}
