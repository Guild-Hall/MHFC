package mhfc.net.common.world.area;

import com.sk89q.worldedit.function.operation.Operation;

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
	 * @return the {@link Operation} to construct the area.
	 */
	Operation populate(World world, AreaConfiguration configuration);

	/**
	 * Called to load an area
	 * <p>
	 * The configuration given is the one read from NBT.
	 *
	 * @param world
	 *            the world to load in
	 * @param configuration
	 *            the config to load
	 * @return
	 * @see #configForLoading()
	 */
	IArea provideForLoading(World world, AreaConfiguration configuration);

	AreaConfiguration configForNewArea();

	IExtendedConfiguration configForLoading();

	String getUnlocalizedName();

	@Override
	int hashCode();
}
