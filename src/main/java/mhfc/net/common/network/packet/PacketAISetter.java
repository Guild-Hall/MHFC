package mhfc.net.common.network.packet;

import mhfc.net.MHFCMain;
import mhfc.net.common.implement.iMHFC;
import net.minecraft.entity.Entity;

public class PacketAISetter{
	
	
	public static void sendAnimPacket(iMHFC entity, int animID) {
		if(MHFCMain.isEffectiveClient()) return;
		entity.setAnimID(animID);
		Entity e = (Entity)entity;
		MHFCMain.packetPipeline.sendToAll(new PacketAIAnim((byte)animID, e.getEntityId()));
	}
}
