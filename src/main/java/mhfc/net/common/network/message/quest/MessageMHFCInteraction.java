package mhfc.net.common.network.message.quest;

import java.io.IOException;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

public class MessageMHFCInteraction implements IMessage {

	public static enum Interaction {
		NEW_QUEST, // Used to start a new quest
		ACCEPT_QUEST, // Used to accept a quest that is waiting for its start
		START_QUEST, // Used to set status to ready (pre-quest to reset end vote
		END_QUEST, // Used to set status to not ready (pre-quest) or to vote for
					// resigning
		FORFEIT_QUEST, // Used to instantly resign from a quest as a single person
		MOD_RELOAD, // Used by any play OR THE COMMAND BLOCK to reload all
					// quests
		INVALID; //
	}

	protected Interaction interaction;
	protected String[] options;

	public MessageMHFCInteraction() {}

	public MessageMHFCInteraction(Interaction action, String... options) {
		interaction = action;
		this.options = options;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		try (ByteBufOutputStream out = new ByteBufOutputStream(buf);) {
			byte toWrite;
			switch (interaction) {
			case NEW_QUEST:
				toWrite = 0;
				break;
			case ACCEPT_QUEST:
				toWrite = 1;
				break;
			case START_QUEST:
				toWrite = 2;
				break;
			case END_QUEST:
				toWrite = 3;
				break;
			case FORFEIT_QUEST:
				toWrite = 4;
				break;
			default:
				toWrite = -1;
				break;
			}
			out.writeByte(toWrite);
			out.writeByte(options.length);
			for (String s : options) {
				out.writeUTF(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		try (ByteBufInputStream in = new ByteBufInputStream(buf);) {
			byte b = in.readByte();
			switch (b) {
			case 0:
				interaction = Interaction.NEW_QUEST;
				break;
			case 1:
				interaction = Interaction.ACCEPT_QUEST;
				break;
			case 2:
				interaction = Interaction.START_QUEST;
				break;
			case 3:
				interaction = Interaction.END_QUEST;
				break;
			case 4:
				interaction = Interaction.FORFEIT_QUEST;
				break;
			default:
				interaction = Interaction.INVALID;
				break;

			}
			b = in.readByte();
			options = new String[b];
			for (byte a = 0; a < b; a++) {
				options[a] = in.readUTF();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Interaction getInteraction() {
		return interaction;
	}

	public String[] getOptions() {
		return options;
	}
}
