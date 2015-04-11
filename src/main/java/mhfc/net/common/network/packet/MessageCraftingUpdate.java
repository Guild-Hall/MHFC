package mhfc.net.common.network.packet;

import mhfc.net.common.tile.TileHunterBench;

public class MessageCraftingUpdate extends MessageTileUpdate {

	public MessageCraftingUpdate() {
	}

	public MessageCraftingUpdate(TileHunterBench bench) {
		super(bench);
	}
}