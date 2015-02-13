package mhfc.net.common.network.message.bench;

import mhfc.net.common.network.packet.MessageTileLocation;
import mhfc.net.common.tile.TileHunterBench;

public class MessageBenchRefreshRequest extends MessageTileLocation {

	public MessageBenchRefreshRequest(TileHunterBench bench) {
		super(bench);
	}

	public MessageBenchRefreshRequest() {
	}

}
