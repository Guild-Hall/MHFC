package mhfc.net.common.network.packet;

import io.netty.buffer.ByteBuf;
import mhfc.net.common.entity.mob.EntityRathalos;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketAIMessageTest implements IMessage {
	
	private byte animID;
	private int entityID;
	public PacketAIMessageTest() {
		
	}
	
	public PacketAIMessageTest(byte anim, EntityRathalos e){
		animID = anim;
		entityID = e.getEntityId();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		animID = buf.readByte();
		entityID = buf.readInt();
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte(animID);
		buf.writeInt(entityID);
	}

}
