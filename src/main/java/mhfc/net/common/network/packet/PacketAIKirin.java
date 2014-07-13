package mhfc.net.common.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import mhfc.net.common.entity.mob.EntityKirin;
import mhfc.net.common.network.AbstractPacket;
import net.minecraft.entity.player.EntityPlayer;

public class PacketAIKirin extends AbstractPacket {
	private byte animID;
	private int entityID;

	public PacketAIKirin() {

	}

	public PacketAIKirin(byte anim, EntityKirin entity) {
		animID = anim;
		entityID = entity.getEntityId();
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeByte(animID);
		buffer.writeInt(entityID);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		animID = buffer.readByte();
		entityID = buffer.readInt();
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		EntityKirin entity = (EntityKirin) player.worldObj
				.getEntityByID(entityID);
		if (entity != null && animID != -1) {
			entity.setAnimID(animID);
			if (animID == 0) entity.setAnimTick(0);
		}
	}

	@Override
	public void handleServerSide(EntityPlayer player) {

	}

}
