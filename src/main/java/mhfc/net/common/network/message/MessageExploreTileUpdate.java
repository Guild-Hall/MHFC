package mhfc.net.common.network.message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import mhfc.net.MHFCMain;
import mhfc.net.common.quests.world.QuestFlair;
import mhfc.net.common.tile.TileExploreArea;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import java.io.IOException;

public class MessageExploreTileUpdate extends MessageTileLocation {

	private ResourceLocation targetArea;
	private QuestFlair flair;

	public MessageExploreTileUpdate() {}

	public MessageExploreTileUpdate(TileExploreArea tileEntity, ResourceLocation targetArea, QuestFlair flair) {
		super(tileEntity);
		this.targetArea = targetArea;
		this.flair = flair;
	}

	public ResourceLocation getTargetArea() {
		return targetArea;
	}

	public QuestFlair getFlair() {
		return flair;

	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		try (ByteBufOutputStream oStream = new ByteBufOutputStream(buf)) {
			oStream.writeUTF(targetArea.toString());
			oStream.writeUTF(flair.name());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		try (ByteBufInputStream iStream = new ByteBufInputStream(buf)) {
			targetArea = new ResourceLocation(iStream.readUTF());
			String flairStr = iStream.readUTF();
			flair = QuestFlair.DAYTIME;
			try {
				flair = QuestFlair.valueOf(flairStr);
			} catch (IllegalArgumentException ex) {
				MHFCMain.logger().error(
						"Invalid flair {} in update packet for explore tile {} in world {}",
						flairStr,
						getPos(),
						worldID);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public TileExploreArea getTileEntity() {
		assert FMLCommonHandler.instance().getSide() == Side.SERVER;
		WorldServer worldServerForDimension = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(this.worldID);
		TileEntity tileEntity = worldServerForDimension.getTileEntity(getPos());
		if (tileEntity instanceof TileExploreArea) {
			return (TileExploreArea) tileEntity;
		}
		MHFCMain.logger().error("Received invalid update for explore tile at {} in world {}", getPos(), worldID);
		return null;
	}
}
