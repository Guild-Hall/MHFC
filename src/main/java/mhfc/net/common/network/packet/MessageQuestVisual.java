package mhfc.net.common.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class MessageQuestVisual implements IMessage {

	private static final int[] lengthsPerID = {12};

	protected int typeID;
	protected String[] strings = new String[12];

	protected MessageQuestVisual() {
		this(0);
	}

	protected MessageQuestVisual(int typeID) {
		this.typeID = typeID;
	}

	public MessageQuestVisual(String[] strings) {
		this(0, strings);
	}

	public MessageQuestVisual(int typeID, String[] strings) {
		this(typeID);
		// TODO DEBUG code only, remove this later on
		if (strings.length != lengthsPerID[typeID])
			throw new IllegalArgumentException(
					"[MHFC] MessageQuestVisual needs appropriate amount of strings");
		this.strings = strings;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		try (ByteBufInputStream in = new ByteBufInputStream(buf);) {
			typeID = in.readInt();
			if (typeID > 0 && typeID < lengthsPerID.length)
				strings = new String[lengthsPerID[typeID]];
			for (int i = 0; i < strings.length; i++) {
				strings[i] = in.readUTF();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		try (ByteBufOutputStream out = new ByteBufOutputStream(buf)) {
			out.writeInt(typeID);
			for (int i = 0; i < strings.length; i++) {
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

	public int getTypeID() {
		return typeID;
	}

}
