package mhfc.net.common.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public abstract class MessageTileLocation implements IMessage {

	public MessageTileLocation() {}

	public MessageTileLocation(TileEntity entity) {
		this.pos = entity.getPos();
		this.worldID = entity.getWorld().provider.getDimension();
	}

	protected BlockPos pos;
	protected int worldID;

	public BlockPos getPos() {
		return pos;
	}

	public int getX() {
		return pos.getX();
	}

	public int getY() {
		return pos.getY();
	}

	public int getZ() {
		return pos.getZ();
	}

	public int getDimensionID() {
		return worldID;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		worldID = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		buf.writeInt(worldID);
	}

	public TileEntity getTileEntity() {
		return null;
	}
}
