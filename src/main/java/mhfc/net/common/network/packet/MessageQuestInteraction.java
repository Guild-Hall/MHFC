package mhfc.net.common.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class MessageQuestInteraction implements IMessage {

	public enum Interaction {
		ACCEPT, VOTE_START, VOTE_END, GIVE_UP;
	}

	private Interaction interaction;
	private String[] options;

	public MessageQuestInteraction() {

	}

	public MessageQuestInteraction(Interaction action, String... options) {
		interaction = action;
		this.options = options;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		try (ByteBufOutputStream out = new ByteBufOutputStream(buf);) {
			byte toWrite;
			switch (interaction) {
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
					toWrite = 0;
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
	public void toBytes(ByteBuf buf) {
		try (ByteBufInputStream in = new ByteBufInputStream(buf);) {
			byte b = in.readByte();
			switch (b) {
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
}
