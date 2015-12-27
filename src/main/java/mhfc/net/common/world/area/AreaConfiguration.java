package mhfc.net.common.world.area;

public abstract class AreaConfiguration {
	// Not to be altered by any extending class
	public final int sizeX;
	public final int sizeZ;

	public AreaConfiguration(int sizeX, int sizeZ) {
		this.sizeX = sizeX;
		this.sizeZ = sizeZ;
	}
}
