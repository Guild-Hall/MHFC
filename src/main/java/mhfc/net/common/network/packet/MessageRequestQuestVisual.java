package mhfc.net.common.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class MessageRequestQuestVisual implements IMessage {

	protected String identifier;

	public MessageRequestQuestVisual() {

	}

	public MessageRequestQuestVisual(String identifier) {
		this.identifier = identifier;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		try (ByteBufInputStream in = new ByteBufInputStream(buf)) {
			identifier = in.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		try (ByteBufOutputStream out = new ByteBufOutputStream(buf)) {
			out.writeUTF(identifier);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getIdentifier() {
		return identifier;
	}

}
