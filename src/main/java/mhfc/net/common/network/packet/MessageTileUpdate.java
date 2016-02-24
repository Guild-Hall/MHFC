package mhfc.net.common.network.packet;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import mhfc.net.common.tile.TileMHFCUpdateStream;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class MessageTileUpdate extends MessageTileLocation {

	private NBTTagCompound updateNBT;

	public MessageTileUpdate() {}

	public MessageTileUpdate(TileEntity tile) {
		super(tile);
		updateNBT = new NBTTagCompound();
		if (tile instanceof TileMHFCUpdateStream) {
			((TileMHFCUpdateStream) tile).storeCustomUpdate(updateNBT);
		} else {
			tile.writeToNBT(updateNBT);
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		updateNBT = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		ByteBufUtils.writeTag(buf, updateNBT);
	}

	public NBTTagCompound getNBTTag() {
		return updateNBT;
	}

}
