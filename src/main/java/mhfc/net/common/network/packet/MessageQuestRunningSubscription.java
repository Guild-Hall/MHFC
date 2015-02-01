package mhfc.net.common.network.packet;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class MessageQuestRunningSubscription implements IMessage {

	private boolean hasSubscribed;

	public MessageQuestRunningSubscription() {
	}

	public MessageQuestRunningSubscription(boolean newSubscribed) {
		this.hasSubscribed = newSubscribed;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		hasSubscribed = buf.readBoolean();

	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(hasSubscribed);
	}

	public boolean isSubscribed() {
		return hasSubscribed;
	}
}
