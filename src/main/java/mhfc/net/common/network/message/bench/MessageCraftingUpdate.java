package mhfc.net.common.network.message.bench;

import mhfc.net.common.network.packet.MessageTileUpdate;
import mhfc.net.common.tile.TileHunterBench;

public class MessageCraftingUpdate extends MessageTileUpdate {

	public MessageCraftingUpdate() {
	}

	public MessageCraftingUpdate(TileHunterBench bench) {
		super(bench);
	}
}