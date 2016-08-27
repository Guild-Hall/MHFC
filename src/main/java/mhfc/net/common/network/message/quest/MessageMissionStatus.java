package mhfc.net.common.network.message.quest;

import java.util.Objects;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class MessageMissionStatus implements IMessage {
	public static enum Status {
		MISSION_CREATED,
		MISSION_ENDED,
		MISSION_JOINED,
		MISSION_DEPARTED;
	}

	private static Status[] statusValues = Status.values();

	private String questID;
	private String missionID;
	private Status statusType;

	public MessageMissionStatus() {}

	private MessageMissionStatus(String questID, String missionID, Status type) {
		this.questID = questID;
		this.missionID = Objects.requireNonNull(missionID);
		this.statusType = Objects.requireNonNull(type);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(statusType.ordinal());
		if (statusType == Status.MISSION_CREATED) {
			ByteBufUtils.writeUTF8String(buf, questID);
		}
		ByteBufUtils.writeUTF8String(buf, missionID);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		statusType = statusValues[buf.readInt()];
		questID = statusType == Status.MISSION_CREATED ? ByteBufUtils.readUTF8String(buf) : null;
		missionID = ByteBufUtils.readUTF8String(buf);
	}

	public Status getStatusType() {
		return this.statusType;
	}

	public String getQuestID() {
		return this.questID;
	}

	public String getMissionID() {
		return this.missionID;
	}

	/**
	 * Suitable when a new mission of type questID is being started. In future messages this will be refered to by
	 * missionID
	 *
	 * @param questID
	 * @param missionID
	 * @return
	 */
	public static MessageMissionStatus creation(String questID, String missionID) {
		Objects.requireNonNull(questID);
		return new MessageMissionStatus(questID, missionID, Status.MISSION_CREATED);
	}

	/**
	 * Suitable when a mission is canceled or otherwise discarded.
	 *
	 * @param missionID
	 *            the same as in the respective {@link #creation(String, String)} message
	 * @return
	 */
	public static MessageMissionStatus destruction(String missionID) {
		return new MessageMissionStatus(null, missionID, Status.MISSION_ENDED);
	}

	/**
	 * Suitable when the player this is sent to joins the mission
	 *
	 * @param missionID
	 *            the same as in the respective {@link #creation(String, String)} message
	 * @return
	 */
	public static MessageMissionStatus joining(String missionID) {
		return new MessageMissionStatus(null, missionID, Status.MISSION_JOINED);
	}

	/**
	 * Suitable when the player this is sent to departs from the mission
	 *
	 * @param missionID
	 *            the same as in the respective {@link #creation(String, String)} message
	 * @return
	 */
	public static MessageMissionStatus departing(String missionID) {
		return new MessageMissionStatus(null, missionID, Status.MISSION_DEPARTED);
	}
}
