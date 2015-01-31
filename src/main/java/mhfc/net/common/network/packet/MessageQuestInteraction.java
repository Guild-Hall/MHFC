package mhfc.net.common.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class MessageQuestInteraction implements IMessage {

	public enum Interaction {
		START_NEW, // Used to start a new quest
		ACCEPT, // Used to accept a quest that is waiting for its start
		VOTE_START, // Used to set status to ready (pre-quest to reset end vote
		VOTE_END, // Used to set status to not ready (pre-quest) or to vote for
					// resigning
		GIVE_UP, // Used to instantly resign from a quest as a single person
		MOD_RELOAD, // Used by any play OR THE COMMAND BLOCK to reload all
					// quests
		INVALID; //
	}

	protected Interaction interaction;
	protected String[] options;

	public MessageQuestInteraction() {
	}

	public MessageQuestInteraction(Interaction action, String... options) {
		interaction = action;
		this.options = options;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		try (ByteBufOutputStream out = new ByteBufOutputStream(buf);) {
			byte toWrite;
			switch (interaction) {
				case START_NEW :
					toWrite = 0;
					break;
				case ACCEPT :
					toWrite = 1;
					break;
				case VOTE_START :
					toWrite = 2;
					break;
				case VOTE_END :
					toWrite = 3;
					break;
				case GIVE_UP :
					toWrite = 4;
					break;
				default :
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
				case 0 :
					interaction = Interaction.START_NEW;
					break;
				case 1 :
					interaction = Interaction.ACCEPT;
					break;
				case 2 :
					interaction = Interaction.VOTE_START;
					break;
				case 3 :
					interaction = Interaction.VOTE_END;
					break;
				case 4 :
					interaction = Interaction.GIVE_UP;
					break;
				default :
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
