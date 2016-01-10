package mhfc.net.common.util;

import net.minecraft.nbt.NBTTagCompound;

public interface ISavableToNBT {
	void saveTo(NBTTagCompound nbtTag);

	void readFrom(NBTTagCompound nbtTag);
}
