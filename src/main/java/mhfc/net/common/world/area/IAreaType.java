package mhfc.net.common.world.area;

import com.sk89q.worldedit.WorldEditException;

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
	 * @param configuration
	 *            the configuration previously returned from {@link #configForNewArea()}
	 * @return the {@link IArea} controller.
	 * @throws WorldEditException
	 */
	IArea populate(World world, AreaConfiguration configuration) throws Exception;

	IArea provideForLoading(World world);

	AreaConfiguration configForNewArea();

	IExtendedConfiguration configForLoading();

	@Override
	int hashCode();
}
