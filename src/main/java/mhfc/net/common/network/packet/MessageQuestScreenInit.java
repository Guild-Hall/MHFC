package mhfc.net.common.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class MessageQuestScreenInit implements IMessage {

	protected Map<String, List<String>> identifierListsMap;
	protected List<String> identifiersInOrder;

	public MessageQuestScreenInit() {
		identifierListsMap = new HashMap<String, List<String>>();
		identifiersInOrder = new ArrayList<String>();
	}

	public MessageQuestScreenInit(Map<String, List<String>> mapping,
			List<String> identifiersInOrder) {
		identifierListsMap = mapping;
		this.identifiersInOrder = identifiersInOrder;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		identifierListsMap.clear();
		buf.retain();
		try (ByteBufInputStream in = new ByteBufInputStream(buf);) {
			String listID = null;
			boolean constructNextList = true;
			boolean breakThis = false;
			while (!breakThis) {
				try {
					String line = in.readUTF();
					if (constructNextList) {
						identifierListsMap.put(line, new ArrayList<String>());
						identifiersInOrder.add(line);
						constructNextList = false;
						listID = line;
						continue;
					}
					if (line.equals(":")) {
						constructNextList = true;
						continue;
					}
					identifierListsMap.get(listID).add(line);
				} catch (EOFException eof) {
					breakThis = true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		buf.release();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		try (ByteBufOutputStream out = new ByteBufOutputStream(buf);) {
			boolean first = true;
			for (String id : identifiersInOrder) {
				if (!first) {
					out.writeUTF(":");
				}
				first = false;
				out.writeUTF(id);
				for (String string : identifierListsMap.get(id)) {
					out.writeUTF(string);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<String> getIdentifiers() {
		return identifiersInOrder;
	}

	public Map<String, List<String>> getIdentifierListMap() {
		return identifierListsMap;
	}
}
