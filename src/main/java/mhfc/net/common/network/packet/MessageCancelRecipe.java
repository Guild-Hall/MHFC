package mhfc.net.common.network.packet;

import mhfc.net.common.tile.TileHunterBench;

public class MessageCancelRecipe extends MessageTileLocation {

	public MessageCancelRecipe() {
	}

	public MessageCancelRecipe(TileHunterBench bench) {
		super(bench);
	}
}