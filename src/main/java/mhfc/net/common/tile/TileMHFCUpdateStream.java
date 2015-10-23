package mhfc.net.common.tile;

import net.minecraft.nbt.NBTTagCompound;

public interface TileMHFCUpdateStream {
	/**
	 * Tells a lient side tile entity to refresh itself from the server
	 */
	public void refreshState();

	public void storeCustomUpdate(NBTTagCompound nbt);

	public void readCustomUpdate(NBTTagCompound nbt);
}
