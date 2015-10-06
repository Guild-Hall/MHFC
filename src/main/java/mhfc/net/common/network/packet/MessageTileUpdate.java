package mhfc.net.common.network.packet;

import io.netty.buffer.ByteBuf;
import mhfc.net.common.tile.TileMHFCUpdateStream;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.ByteBufUtils;

public class MessageTileUpdate extends MessageTileLocation {

	private NBTTagCompound updateNBT;

	public MessageTileUpdate() {}

	public <T extends TileEntity & TileMHFCUpdateStream> MessageTileUpdate(
			T tile) {
		super(tile);
		updateNBT = new NBTTagCompound();
		tile.storeCustomUpdate(updateNBT);
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
