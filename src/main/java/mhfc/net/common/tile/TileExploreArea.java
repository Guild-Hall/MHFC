package mhfc.net.common.tile;

import mhfc.net.MHFCMain;
import mhfc.net.common.network.message.MessageExploreTileUpdate;
import mhfc.net.common.network.message.MessageTileUpdate;
import mhfc.net.common.quests.world.QuestFlair;
import mhfc.net.common.world.area.AreaRegistry;
import mhfc.net.common.world.area.IAreaType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class TileExploreArea extends TileEntity implements TileMHFCUpdateStream {

	private static final String KEY_TARGET = "targetArea";
	private static final String KEY_FLAIR = "targetFlair";

	public static class UpdateRequestHandler implements IMessageHandler<MessageExploreTileUpdate, IMessage> {

		@Override
		public IMessage onMessage(MessageExploreTileUpdate message, MessageContext ctx) {
			TileExploreArea tile = message.getTileEntity();
			if (tile == null) {
				return null;
			}
			tile.targetArea = message.getTargetArea();
			tile.flair = message.getFlair();
			tile.world.markChunkDirty(tile.getPos(), tile);
			return new MessageTileUpdate(tile);
		}

	}

	private ResourceLocation targetArea;
	private QuestFlair flair;

	public TileExploreArea() {
		targetArea = new ResourceLocation("mhfc:invalid");
		flair = QuestFlair.DAYTIME;
	}

	public ResourceLocation getTargetAreaName() {
		return targetArea;
	}

	public IAreaType getTargetArea() {
		return AreaRegistry.getType(getTargetAreaName());
	}

	public void setTargetArea(ResourceLocation targetArea) {
		this.targetArea = targetArea;
	}

	public QuestFlair getFlair() {
		return flair;
	}

	public void setFlair(String newFlair) {
		flair = QuestFlair.DAYTIME;
		try {
			flair = QuestFlair.valueOf(newFlair);
		} catch (IllegalArgumentException iae) {
			MHFCMain.logger().error(
					"Flair " + newFlair
							+ " was not recognized, for allowed values see documentation of MHFCQuestBuildRegistry. Falling back to DAYTIME.");
		}
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound answerNbt = new NBTTagCompound();
		this.writeToNBT(answerNbt);
		return new SPacketUpdateTileEntity(getPos(), 1, answerNbt);
	}

	@Override
	public void refreshState() {

	}

	@Override
	public void readCustomUpdate(NBTTagCompound nbt) {
		readFromNBT(nbt);
	}

	@Override
	public void storeCustomUpdate(NBTTagCompound nbt) {
		writeToNBT(nbt);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt = super.writeToNBT(nbt);
		nbt.setString(KEY_TARGET, targetArea.toString());
		nbt.setString(KEY_FLAIR, flair.name());
		return nbt;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		setTargetArea(new ResourceLocation(nbt.getString(KEY_TARGET)));
		setFlair(nbt.getString(KEY_FLAIR));
	}

}
