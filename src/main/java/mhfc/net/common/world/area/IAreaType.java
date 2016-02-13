package mhfc.net.common.world.area;

import net.minecraft.world.World;

public interface IAreaType {

	/**
	 * Called to populate the world<br>
	 *
	 * This implies generating the terrain, spawning structures but not necessarily spawning entities. On the contrary:
	 * The entities should not be spawned until after the Area is being taken into use by a group of hunters.<br>
	 * Returns the {@link IAreaPlan} that is later being used to actually generate the structures.
	 *
	 * @param world
	 *            the world
	 * @param configuration
	 *            the configuration previously returned from {@link #configForNewArea()}
	 * @return the {@link IAreaPlan} to construct the area.
	 */
	IAreaPlan populate(World world, AreaConfiguration configuration);

	IArea provideForLoading(World world);

	AreaConfiguration configForNewArea();

	IExtendedConfiguration configForLoading();

	@Override
	int hashCode();
}
