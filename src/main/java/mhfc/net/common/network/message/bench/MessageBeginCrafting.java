package mhfc.net.common.network.message.bench;

import mhfc.net.common.network.message.MessageTileLocation;
import mhfc.net.common.tile.TileHunterBench;

public class MessageBeginCrafting extends MessageTileLocation {

	public MessageBeginCrafting() {
	}

	public MessageBeginCrafting(TileHunterBench bench) {
		super(bench);
	}
}