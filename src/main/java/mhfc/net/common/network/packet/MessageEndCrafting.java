package mhfc.net.common.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import mhfc.net.common.tile.TileHunterBench;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;

public class MessageEndCrafting extends MessageTileLocation {

	String outputTag;

	public MessageEndCrafting() {
	}

	public MessageEndCrafting(TileHunterBench bench, ItemStack endStack) {
		super(bench);
		if (endStack != null) {
			NBTTagCompound tag = new NBTTagCompound();
			endStack.writeToNBT(tag);
			outputTag = tag.toString();
		}

	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		try (ByteBufInputStream out = new ByteBufInputStream(buf);) {
			outputTag = out.readUTF();
		} catch (IOException e) {
		}

	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		try (ByteBufOutputStream out = new ByteBufOutputStream(buf);) {
			out.writeUTF(outputTag);
		} catch (IOException e) {
		}
	}

	public NBTTagCompound getNBTTag() {
		NBTTagCompound tag = null;
		try {
			tag = ((NBTTagCompound) JsonToNBT.func_150315_a(outputTag));
		} catch (NBTException e) {
			e.printStackTrace();
		}
		return tag;
	}
}