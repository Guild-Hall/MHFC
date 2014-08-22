package mhfc.net.common.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import mhfc.net.common.quests.QuestRunningInformation;
import mhfc.net.common.quests.QuestVisualInformation;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class MessageQuestVisual implements IMessage {

	private static final int[] lengthsPerID = {12, 14};

	protected int typeID;
	protected String[] strings = new String[12];

	public MessageQuestVisual() {
		this(0);
	}

	protected MessageQuestVisual(int typeID) {
		this.typeID = typeID;
	}

	public MessageQuestVisual(String[] strings) {
		this(0, strings);
	}

	public <E extends QuestVisualInformation> MessageQuestVisual(
			String identifier, E visual) {
		this(visual == null ? new String[]{""} : new String[]{identifier,
				visual.getName(), visual.getDescription(), visual.getClient(),
				visual.getAims(), visual.getFails(), visual.getAreaID(),
				visual.getTimeLimitAsString(), visual.getType().getAsString(),
				visual.getRewardString(), visual.getFeeString(),
				visual.getMaxPartySize()});
	}

	public <E extends QuestRunningInformation> MessageQuestVisual(
			String identifier, E visual) {
		this(1, visual == null ? new String[]{""} : new String[]{identifier,
				visual.getTrueName(), visual.getTrueDescription(),
				visual.getTrueClient(), visual.getTrueAims(),
				visual.getTrueFails(), visual.getTrueAreaID(),
				visual.getTrueTimeLimitAsString(),
				visual.getType().getAsString(), visual.getTrueRewardString(),
				visual.getTrueFeeString(), visual.getTrueMaxPartySize(),
				visual.getTrueShortStatus(), visual.getTrueLongStatus()});
	}

	public MessageQuestVisual(int typeID, String[] strings) {
		this(typeID);
		// TODO DEBUG code only, remove this later on
		if (strings.length != lengthsPerID[typeID])
			throw new IllegalArgumentException(
					"[MHFC] MessageQuestVisual needs appropriate amount of strings for type "
							+ typeID);
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
