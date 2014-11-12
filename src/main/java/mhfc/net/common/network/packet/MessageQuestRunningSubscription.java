package mhfc.net.common.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class MessageQuestRunningSubscription implements IMessage {

	private boolean hasSubscribed;
	private String playerUUID;

	public MessageQuestRunningSubscription() {
	}

	public MessageQuestRunningSubscription(boolean newSubscribed,
			EntityPlayer thePlayer) {
		if (thePlayer == null)
			thePlayer = Minecraft.getMinecraft().thePlayer;
		this.hasSubscribed = newSubscribed;
		this.playerUUID = thePlayer.getGameProfile().getName();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		hasSubscribed = buf.readBoolean();
		try (ByteBufInputStream in = new ByteBufInputStream(buf);) {
			playerUUID = in.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(hasSubscribed);
		try (ByteBufOutputStream out = new ByteBufOutputStream(buf);) {
			out.writeUTF(playerUUID);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public EntityPlayerMP getPlayer() {
		return MessageQuestInteraction.getPlayer(this.playerUUID);
	}

	public boolean isSubscribed() {
		return hasSubscribed;
	}
}
