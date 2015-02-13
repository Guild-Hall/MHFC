package mhfc.net.common.network.message.bench;

import mhfc.net.common.network.packet.MessageTileLocation;
import mhfc.net.common.tile.TileHunterBench;

public class MessageCancelRecipe extends MessageTileLocation {

	public MessageCancelRecipe() {
	}

	public MessageCancelRecipe(TileHunterBench bench) {
		super(bench);
	}
}