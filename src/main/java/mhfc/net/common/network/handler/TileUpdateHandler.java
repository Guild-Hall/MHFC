package mhfc.net.common.network.handler;

import mhfc.net.common.network.message.MessageTileUpdate;
import mhfc.net.common.tile.TileMHFCUpdateStream;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class TileUpdateHandler extends ThreadSafeMessageHandler<MessageTileUpdate, IMessage> {

	@Override
	public void handleLater(MessageTileUpdate message, MessageContext ctx) {
		TileEntity tile = message.getTileEntity();
		if (tile == null) {
			return;
		}
		if (tile instanceof TileMHFCUpdateStream) {
			((TileMHFCUpdateStream) tile).readCustomUpdate(message.getNBTTag());
		} else {
			tile.readFromNBT(message.getNBTTag());
		}
	}

}
