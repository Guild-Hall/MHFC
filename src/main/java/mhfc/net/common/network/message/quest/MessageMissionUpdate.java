package mhfc.net.common.network.message.quest;

import java.util.Objects;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import mhfc.net.common.quests.properties.GroupProperty;
import net.minecraft.nbt.NBTTagCompound;

public class MessageMissionUpdate implements IMessage {

	private String missionId;
	private NBTTagCompound updateData;

	public MessageMissionUpdate() {
		updateData = null;
	}

	private MessageMissionUpdate(String missionId, NBTTagCompound updateData) {
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
		updateData = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeTag(buf, updateData);
	};

	public static MessageMissionUpdate createUpdate(String id, GroupProperty rootProperty) {
		return new MessageMissionUpdate(id, rootProperty.dumpUpdates());
	}

	public static MessageMissionUpdate createFullDump(String id, GroupProperty rootProperty) {
		return new MessageMissionUpdate(id, rootProperty.dumpAll());
	}
}
