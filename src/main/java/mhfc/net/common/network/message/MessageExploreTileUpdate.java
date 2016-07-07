package mhfc.net.common.network.message;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import mhfc.net.MHFCMain;
import mhfc.net.common.network.packet.MessageTileLocation;
import mhfc.net.common.quests.world.QuestFlair;
import mhfc.net.common.tile.TileExploreArea;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;

public class MessageExploreTileUpdate extends MessageTileLocation {

	private String targetArea;
	private QuestFlair flair;

	public MessageExploreTileUpdate() {}

	public MessageExploreTileUpdate(TileExploreArea tileEntity, String targetArea, QuestFlair flair) {
		super(tileEntity);
		this.targetArea = targetArea;
		this.flair = flair;
	}

	public String getTargetArea() {
		return targetArea;
	}

	public QuestFlair getFlair() {
		return flair;

	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		try (ByteBufOutputStream oStream = new ByteBufOutputStream(buf)) {
			oStream.writeUTF(targetArea);
			oStream.writeUTF(flair.name());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		try (ByteBufInputStream iStream = new ByteBufInputStream(buf)) {
			targetArea = iStream.readUTF();
			String flairStr = iStream.readUTF();
			flair = QuestFlair.DAYTIME;
			try {
				flair = QuestFlair.valueOf(flairStr);
			} catch (IllegalArgumentException x) {
				MHFCMain.logger().error(
						"Invalid flair {} in update packet for explore tile {} {} {} in world {}",
						flairStr,
						x,
						y,
						z,
						worldID);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public TileExploreArea getTileEntity() {
		WorldServer worldServerForDimension = MinecraftServer.getServer().worldServerForDimension(worldID);
		TileEntity tileEntity = worldServerForDimension.getTileEntity(x, y, z);
		if (tileEntity instanceof TileExploreArea) {
			return (TileExploreArea) tileEntity;
		} else {
			MHFCMain.logger().error("Received invalid update for explore tile at {} {} {} in world {}", x, y, z, worldID);
			return null;
		}
	}
}
