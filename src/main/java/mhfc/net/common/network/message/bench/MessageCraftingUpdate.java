package mhfc.net.common.network.message.bench;

import mhfc.net.common.network.message.MessageTileUpdate;
import mhfc.net.common.tile.TileHunterBench;

public class MessageCraftingUpdate extends MessageTileUpdate {

	public MessageCraftingUpdate() {
	}

	public MessageCraftingUpdate(TileHunterBench bench) {
		super(bench);
	}
}