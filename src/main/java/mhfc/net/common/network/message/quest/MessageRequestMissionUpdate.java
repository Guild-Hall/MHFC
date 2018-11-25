package mhfc.net.common.network.message.quest;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.io.IOException;

public class MessageRequestMissionUpdate implements IMessage {

	protected String identifier;

	public MessageRequestMissionUpdate() {}

	public MessageRequestMissionUpdate(String identifier) {
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
