package mhfc.net.common.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import mhfc.net.common.entity.mob.EntityTigrex;
import mhfc.net.common.network.AbstractPacket;
import net.minecraft.entity.player.EntityPlayer;

public class PacketAITigrex extends AbstractPacket{

	private byte animID;
	private int entityID;

	public PacketAITigrex() {

	}

	public PacketAITigrex(byte anim, EntityTigrex entity) {
		animID = anim;
		entityID = entity.getEntityId();
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
		EntityTigrex entity = (EntityTigrex)player.worldObj.getEntityByID(entityID);
		if(entity != null && animID != -1){
			entity.setAnimID(animID);
			if(animID == 0) entity.setAnimTick(0);
		}
	}

	public void handleServerSide(EntityPlayer player) {

	}

}
