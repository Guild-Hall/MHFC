package mhfc.net.common.network.message.quest;

import java.util.Objects;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageMissionStatus implements IMessage {
	public static enum Status {
		MISSION_CREATED,
		MISSION_ENDED,
		MISSION_JOINED,
		MISSION_DEPARTED;
	}

	private static Status[] statusValues = Status.values();

	private ResourceLocation questID;
	private String missionID;
	private Status statusType;

	public MessageMissionStatus() {}

	private MessageMissionStatus(ResourceLocation questID, String missionID, Status type) {
		this.questID = questID;
		this.missionID = Objects.requireNonNull(missionID);
		this.statusType = Objects.requireNonNull(type);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(statusType.ordinal());
		if (statusType == Status.MISSION_CREATED) {
			ByteBufUtils.writeUTF8String(buf, questID.toString());
		}
		ByteBufUtils.writeUTF8String(buf, missionID);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		statusType = statusValues[buf.readInt()];
		questID = statusType == Status.MISSION_CREATED ? new ResourceLocation(ByteBufUtils.readUTF8String(buf)) : null;
		missionID = ByteBufUtils.readUTF8String(buf);
	}

	public Status getStatusType() {
		return this.statusType;
	}

	public ResourceLocation getQuestID() {
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
	public static MessageMissionStatus creation(ResourceLocation questID, String missionID) {
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
