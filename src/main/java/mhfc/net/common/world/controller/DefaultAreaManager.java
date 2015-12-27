package mhfc.net.common.world.controller;

import java.util.Objects;

import org.apache.commons.lang3.tuple.Pair;

import mhfc.net.common.world.area.AreaConfiguration;
import mhfc.net.common.world.proxies.IWorldProxy;
import net.minecraft.world.World;

public class DefaultAreaManager extends AreaManagerAdapter {
	private static class RegionManager {
		/**
		 * Reserves a rectangle of the size (sizeX, sizeZ) in this {@link RegionManager).
		 * 
		 * @param sizeX
		 *            the size of the reserved rectangle
		 * @param sizeZ
		 *            the size of the reserved rectangle
		 * @return the offset of that rectangle
		 */
		public Pair<Integer, Integer> reserve(int sizeX, int sizeZ) {
			// FIXME: figure out the offset of the consumed terrain, not (0, 0)
			return Pair.of(0, 0);
		}
	}

	private World world;
	private RegionManager regionManager;

	public DefaultAreaManager(World world) {
		this.world = Objects.requireNonNull(world);
		this.regionManager = new RegionManager();
	}

	public World getWorld() {
		return this.world;
	}

	@Override
	protected IWorldProxy fitNewArea(AreaConfiguration config) {
		// TODO Auto-generated method stub
		return null;
	}

}
