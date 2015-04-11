package mhfc.net.common.tile;

import net.minecraft.nbt.NBTTagCompound;

public interface TileMHFCUpdateStream {
	public void storeCustomUpdate(NBTTagCompound nbt);

	public void readCustomUpdate(NBTTagCompound nbt);
}
