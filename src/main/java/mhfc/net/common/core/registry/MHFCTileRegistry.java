package mhfc.net.common.core.registry;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.registry.GameRegistry;
import mhfc.net.common.network.packet.MessageTileUpdate;
import mhfc.net.common.tile.TileBBQSpit;
import mhfc.net.common.tile.TileExploreArea;
import mhfc.net.common.tile.TileHunterBench;
import mhfc.net.common.tile.TileMHFCUpdateStream;
import mhfc.net.common.tile.TileQuestBoard;
import mhfc.net.common.tile.TileStunTrap;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.tileentity.TileEntity;

public class MHFCTileRegistry {

	public static class TileUpdateHandler implements IMessageHandler<MessageTileUpdate, IMessage> {

		@Override
		public IMessage onMessage(MessageTileUpdate message, MessageContext ctx) {
			TileEntity tile = message.getTileEntity();
			if (tile == null)
				return null;
			if (tile instanceof TileMHFCUpdateStream) {
				((TileMHFCUpdateStream) tile).readCustomUpdate(message.getNBTTag());
			} else {
				tile.readFromNBT(message.getNBTTag());
			}
			return null;
		}

	}

	public static void init() {
		GameRegistry.registerTileEntity(TileHunterBench.class, MHFCReference.tile_huntersbench_id);
		GameRegistry.registerTileEntity(TileStunTrap.class, MHFCReference.tile_stuntrap_id);
		GameRegistry.registerTileEntity(TileBBQSpit.class, MHFCReference.tile_bbqspit_id);
		GameRegistry.registerTileEntity(TileQuestBoard.class, MHFCReference.tile_questboard_id);
		GameRegistry.registerTileEntity(TileExploreArea.class, MHFCReference.tile_exploreArea_id);
	}
}
