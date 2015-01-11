package mhfc.heltrato.common.network.message;

import io.netty.buffer.ByteBuf;
import mhfc.heltrato.common.entity.mob.EntityTigrex;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageAITigrex implements IMessage {
	
	private static byte animID;
	private static int entityID;
	public MessageAITigrex() {
		
	}
	
	
	public MessageAITigrex(byte anim, EntityTigrex e){
		animID = anim;
		entityID = e.getEntityId();
	}

	public void fromBytes(ByteBuf buf) {
		animID = buf.readByte();
		entityID = buf.readInt();
		
	}

	public void toBytes(ByteBuf buf) {
		buf.writeByte(animID);
		buf.writeInt(entityID);
	}
	
	public static void handleTheClient() {
		EntityTigrex entity = (EntityTigrex)Minecraft.getMinecraft().theWorld.getEntityByID(entityID);
		if(entity != null && animID != -1){
			entity.setAnimID(animID);
			if(animID == 0) entity.setAnimTick(0);
		}
	}
	
	public static class PacketAITigrexHandler implements IMessageHandler<MessageAITigrex, IMessage> {
		
		public IMessage onMessage(MessageAITigrex message,MessageContext ctx) {
			handleTheClient();
			return null;
		}
		
	}
	}

