package mhfc.net.common.network.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;

public class MessageTileLocation implements IMessage {

	public MessageTileLocation() {}

	public MessageTileLocation(TileEntity entity) {
		this.x = entity.xCoord;
		this.y = entity.yCoord;
		this.z = entity.zCoord;
		this.worldID = entity.getWorldObj().provider.dimensionId;
	}

	protected int x;
	protected int y;
	protected int z;
	protected int worldID;

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public int getDimensionID() {
		return worldID;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		worldID = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(worldID);
	}

	public TileEntity getTileEntity() {
		return null;

	}
}
