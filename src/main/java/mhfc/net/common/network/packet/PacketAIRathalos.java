package mhfc.net.common.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import mhfc.net.common.entity.mob.EntityRathalos;
import mhfc.net.common.network.AbstractPacket;

public class PacketAIRathalos extends AbstractPacket {
	
	private byte animID;
	private int entityID;
	
	public PacketAIRathalos() {
		
	}
	
	public PacketAIRathalos(byte anim, EntityRathalos e) {
		animID = anim;
		entityID = e.getEntityId();
	}

	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeByte(animID);
		buffer.writeInt(entityID);
		
	}

	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		animID = buffer.readByte();
		entityID = buffer.readInt();
	}

	public void handleClientSide(EntityPlayer player) {
		EntityRathalos entity = (EntityRathalos)player.worldObj.getEntityByID(entityID);
		if(entity != null && animID != -1){
			entity.setAnimID(animID);
			if(animID == 0) entity.setAnimTick(0);
		}
	}

	public void handleServerSide(EntityPlayer player) {
		
	}

}
