package mhfc.net.common.world.area;

import mhfc.net.common.world.controller.CornerPosition;
import net.minecraft.world.World;

public interface IAreaType {

	/**
	 * Called to populate the world<br>
	 *
	 * This implies generating the terrain, spawning structures but not necessarily spawning entities. On the contrary:
	 * The entities should not be spawned until after the Area is being taken into use by a group of hunters.<br>
	 * Returns the {@link IArea} that is later being used to interact with the generated instace of this
	 * {@link IAreaType}.
	 *
	 * @param world
	 *            the world
	 * @param lowerLeftCorner
	 *            the position of the lower left corner of the area that has been assigned to this Area
	 * @param configuration
	 *            the configuration previously returned from {@link #configureNewArea()}
	 * @return the {@link IArea} controller.
	 */
	IArea populate(World world, CornerPosition lowerLeftCorner, AreaConfiguration configuration);

	IArea provideForLoading(World world);

	AreaConfiguration configureNewArea();

	@Override
	int hashCode();
}
