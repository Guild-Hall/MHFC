package mhfc.heltrato.common.network.message;

import io.netty.buffer.ByteBuf;
import mhfc.heltrato.common.interfaces.iMHFC;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageAIAnim implements IMessage {
	
	private static byte animID;
	private static int entityID;
	public MessageAIAnim() {
		
	}
	
	public MessageAIAnim(byte anim, int e){
		animID = anim;
		entityID = e;
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
		iMHFC entity = (iMHFC)Minecraft.getMinecraft().theWorld.getEntityByID(entityID);
		if(entity != null && animID != -1){
			entity.setAnimID(animID);
			if(animID == 0) entity.setAnimTick(0);
		}
	}
	
	public static class PacketAIAnimHandler implements IMessageHandler<MessageAIAnim, IMessage> {
		
		public IMessage onMessage(MessageAIAnim message,MessageContext ctx) {
			handleTheClient();
			return null;
		}
		
	}
	}

