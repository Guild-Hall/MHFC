package mhfc.net.common.world.area;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * Provides a proxy for a world object which can potentially transform or check the parameters of method calls.
 * Coordinates, for which {@link IWorldView#isManaged(int, int, int)} return false may result in null or defaulted
 * return values that do not correspond with the actual values as given by the world object. The exact behaviour is
 * implementation defined.
 */
public interface IWorldView extends IWorld {

	/**
	 * Tells the caller if this world view sees and manages the block given.
	 */
	public boolean isManaged(double posX, double posY, double posZ);

	/**
	 * Fall back method where the interface is not enough to provide the access needed.
	 */
	public World getWorldObject();

	public Vec3d convertToLocal(Vec3d pos);

	public Vec3d convertToGlobal(Vec3d local);

}
