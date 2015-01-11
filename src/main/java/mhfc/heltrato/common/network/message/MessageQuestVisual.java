package mhfc.heltrato.common.network.message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class MessageQuestVisual implements IMessage {

	protected String[] strings = new String[12];

	public MessageQuestVisual() {

	}

	public MessageQuestVisual(String[] strings) {
		if (strings.length != 12)
			throw new IllegalArgumentException(
					"[MHFC] MessageQuestVisual needs 12 strings to match QuestVisualInformation");
		this.strings = strings;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		try (ByteBufInputStream in = new ByteBufInputStream(buf);) {
			for (int i = 0; i < 12; i++) {
				strings[i] = in.readUTF();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		try (ByteBufOutputStream out = new ByteBufOutputStream(buf)) {
			for (int i = 0; i < 12; i++) {
				String s = strings[i];
				out.writeUTF(s == null ? "" : s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String[] getStrings() {
		return strings;
	}

}
