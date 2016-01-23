package mhfc.net.common.world.area;

import mhfc.net.common.util.ISavableToNBT;
import net.minecraft.nbt.NBTTagCompound;

public class AreaConfiguration implements ISavableToNBT {
	private int sizeX;
	private int sizeZ;
	private boolean initialized;

	public AreaConfiguration() {
		this(0, 0);
		initialized = false;
	}

	public AreaConfiguration(int sizeX, int sizeZ) {
		this.sizeX = sizeX;
		this.sizeZ = sizeZ;
		initialized = true;
	}

	@Override
	public void readFrom(NBTTagCompound nbtTag) {
		this.sizeX = nbtTag.getInteger("sizeX");
		this.sizeZ = nbtTag.getInteger("sizeZ");
		initialized = true;
	}

	@Override
	public void saveTo(NBTTagCompound nbtTag) {
		nbtTag.setInteger("sizeX", this.sizeX);
		nbtTag.setInteger("sizeZ", this.sizeZ);
	}

	public int getSizeX() {
		if (!initialized) {
			throw new IllegalStateException("Not initialized");
		}
		return sizeX;
	}

	public int getSizeZ() {
		if (!initialized) {
			throw new IllegalStateException("Not initialized");
		}
		return sizeZ;
	}
}
