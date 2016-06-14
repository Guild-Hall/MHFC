package mhfc.net.common.world.area;

import java.util.Objects;

import mhfc.net.common.util.ISavableToNBT;
import mhfc.net.common.world.controller.CornerPosition;
import mhfc.net.common.world.controller.IAreaManager;
import net.minecraft.nbt.NBTTagCompound;

public final class AreaConfiguration implements ISavableToNBT {
	private int chunkSizeX;
	private int chunkSizeZ;
	private CornerPosition lowerChunkPos;
	private IExtendedConfiguration extendedConfig;
	@SuppressWarnings("unused")
	private boolean isLoaded;

	private AreaConfiguration(IExtendedConfiguration config) {
		this.extendedConfig = Objects.requireNonNull(config);
		isLoaded = false;
	}

	public AreaConfiguration(int sizeX, int sizeZ, IExtendedConfiguration config) {
		this.chunkSizeX = sizeX;
		this.chunkSizeZ = sizeZ;
		this.extendedConfig = Objects.requireNonNull(config);
		this.isLoaded = true;
	}

	@Override
	public void readFrom(NBTTagCompound nbtTag) {
		this.chunkSizeX = nbtTag.getInteger("sizeX");
		this.chunkSizeZ = nbtTag.getInteger("sizeZ");
		this.lowerChunkPos = new CornerPosition(nbtTag.getInteger("posX"), nbtTag.getInteger("posZ"));
		this.extendedConfig.readFrom(nbtTag.getCompoundTag("extendedConfig"));
		this.isLoaded = true;
	}

	@Override
	public void saveTo(NBTTagCompound nbtTag) {
		nbtTag.setInteger("sizeX", this.chunkSizeX);
		nbtTag.setInteger("sizeZ", this.chunkSizeZ);
		nbtTag.setInteger("posX", this.lowerChunkPos.posX);
		nbtTag.setInteger("posZ", this.lowerChunkPos.posY);
		NBTTagCompound extendedConfig = new NBTTagCompound();
		this.extendedConfig.saveTo(extendedConfig);
		nbtTag.setTag("extendedConfig", extendedConfig);
	}

	public int getChunkSizeX() {
		return chunkSizeX;
	}

	public int getChunkSizeZ() {
		return chunkSizeZ;
	}

	/**
	 * Don't call this if you are not an {@link IAreaManager} that sets up the configuration for an {@link IArea}.
	 *
	 * @param newPos
	 *            the new position that the {@link IAreaManager} assigns to this config
	 */
	public void setPosition(CornerPosition newPos) {
		this.lowerChunkPos = Objects.requireNonNull(newPos);
	}

	public CornerPosition getPosition() {
		return this.lowerChunkPos;
	}

	public IExtendedConfiguration getExtendedConfig() {
		return this.extendedConfig;
	}

	public static AreaConfiguration newConfigForLoading(IExtendedConfiguration extendedConfigToLoad) {
		return new AreaConfiguration(extendedConfigToLoad);
	}
}
