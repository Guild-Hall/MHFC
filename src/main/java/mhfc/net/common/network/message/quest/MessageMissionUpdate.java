package mhfc.net.common.network.message.quest;

import java.util.Objects;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import mhfc.net.common.quests.properties.GroupProperty;
import mhfc.net.common.quests.properties.Property;
import net.minecraft.nbt.NBTTagCompound;

public class MessageMissionUpdate implements IMessage {

	private String missionId;
	private NBTTagCompound updateData;

	public MessageMissionUpdate() {}

	private MessageMissionUpdate(String missionId, NBTTagCompound updateData) {
		this.missionId = Objects.requireNonNull(missionId);
		this.updateData = Objects.requireNonNull(updateData);
	}

	public String getMissionId() {
		return missionId;
	}

	/**
	 * Retrieves the received update data. No defensive copy is made.
	 *
	 * @return
	 */
	public NBTTagCompound getUpdateData() {
		return updateData;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		missionId = ByteBufUtils.readUTF8String(buf);
		updateData = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, missionId);
		ByteBufUtils.writeTag(buf, updateData);
	};

	/**
	 * Creates a message containing all updates since the last time invoked <b>or null</b> if no updates are required.
	 *
	 * @param id
	 * @param rootProperty
	 * @return
	 */
	public static MessageMissionUpdate createUpdate(String id, GroupProperty rootProperty) {
		NBTTagCompound updates = rootProperty.dumpUpdates();
		if (Property.signalsNoUpdates(updates)) {
			return null;
		}
		return new MessageMissionUpdate(id, updates);
	}

	public static MessageMissionUpdate createFullDump(String id, GroupProperty rootProperty) {
		return new MessageMissionUpdate(id, rootProperty.dumpAll());
	}
}
