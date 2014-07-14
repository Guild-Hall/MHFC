package mhfc.heltrato.common.network.message;

import io.netty.buffer.ByteBuf;
import mhfc.heltrato.common.entity.mob.EntityRathalos;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageAIRathalos implements IMessage {
	
	private static byte animID;
	private static int entityID;
	public MessageAIRathalos() {
		
	}
	
	public MessageAIRathalos(byte anim, EntityRathalos e){
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
		EntityRathalos entity = (EntityRathalos)Minecraft.getMinecraft().theWorld.getEntityByID(entityID);
		if(entity != null && animID != -1){
			entity.setAnimID(animID);
			if(animID == 0) entity.setAnimTick(0);
		}
	}
	
	public static class PacketAIRathalosHandler implements IMessageHandler<MessageAIRathalos, IMessage> {
		
		public IMessage onMessage(MessageAIRathalos message,MessageContext ctx) {
			handleTheClient();
			return null;
		}
		
	}
	}

