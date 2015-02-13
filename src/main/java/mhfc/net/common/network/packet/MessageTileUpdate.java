package mhfc.net.common.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import mhfc.net.MHFCMain;
import mhfc.net.common.tile.TileMHFCUpdateStream;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class MessageTileUpdate extends MessageTileLocation {

	String nbtString;

	public MessageTileUpdate() {
	}

	public <T extends TileEntity & TileMHFCUpdateStream> MessageTileUpdate(
			T tile) {
		super(tile);
		NBTTagCompound tag = new NBTTagCompound();
		tile.storeCustomUpdate(tag);
		nbtString = tag.toString();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		try (ByteBufInputStream out = new ByteBufInputStream(buf);) {
			nbtString = out.readUTF();
		} catch (IOException e) {
			MHFCMain.logger.info("Error trying to receive update HunterBench");
		}

	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		try (ByteBufOutputStream out = new ByteBufOutputStream(buf);) {
			out.writeUTF(nbtString);
		} catch (IOException e) {
			MHFCMain.logger.info("Error trying to update HunterBench");
		}
	}

	public NBTTagCompound getNBTTag() {
		NBTTagCompound tag = null;
		try {
			tag = ((NBTTagCompound) JsonToNBT.func_150315_a(nbtString));
		} catch (NBTException e) {
			e.printStackTrace();
		}
		return tag;
	}

}
