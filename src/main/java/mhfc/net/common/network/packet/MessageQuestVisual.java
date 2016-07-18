package mhfc.net.common.network.packet;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Objects;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import mhfc.net.common.core.builders.BuilderJsonToQuests;
import mhfc.net.common.quests.api.IVisualInformation;

public class MessageQuestVisual implements IMessage {

	public static enum VisualType {
		RUNNING_QUEST,
		PERSONAL_QUEST
	}

	private VisualType messageType;
	private String messageIdentifier;
	private IVisualInformation information;

	public MessageQuestVisual() {}

	public MessageQuestVisual(VisualType messageType, String identifier, IVisualInformation information) {
		this.messageType = Objects.requireNonNull(messageType);
		this.messageIdentifier = Objects.requireNonNull(identifier);
		this.information = information;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.retain();
		try (ByteBufOutputStream out = new ByteBufOutputStream(buf);) {
			OutputStreamWriter writer = new OutputStreamWriter(out);
			out.writeInt(messageType.ordinal());
			out.writeUTF(messageIdentifier);
			BuilderJsonToQuests.gsonInstance.toJson(information, IVisualInformation.class, writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		buf.release();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		buf.retain();
		try (ByteBufInputStream in = new ByteBufInputStream(buf);) {
			InputStreamReader reader = new InputStreamReader(in);
			messageType = VisualType.values()[in.readInt()];
			messageIdentifier = in.readUTF();
			information = BuilderJsonToQuests.gsonInstance.fromJson(reader, IVisualInformation.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		buf.release();
	}

	public VisualType getMessageType() {
		return messageType;
	}

	public String getMessageIdentifier() {
		return messageIdentifier;
	}

	public IVisualInformation getInformation() {
		return information;
	}

}
