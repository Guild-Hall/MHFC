package mhfc.net.common.world.controller;

import java.util.Objects;

import org.apache.commons.lang3.tuple.Pair;

import mhfc.net.common.world.controller.WorldProxies.Recorder;
import net.minecraft.world.World;

public class DefaultAreaManager extends AreaManagerAdapter {
	private static class RegionManager {
		/**
		 * Reserves a rectangle of the size (sizeX, sizeZ) in this
		 * {@link RegionManager).
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
	private WorldProxies.WorldProxy proxied;
	private RegionManager regionManager;

	public DefaultAreaManager(World world) {
		this.world = Objects.requireNonNull(world);
		this.proxied = new WorldProxies.WorldProxy(world);
		this.regionManager = new RegionManager();
	}

	public World getWorld() {
		return this.world;
	}

	@Override
	protected IWorldProxy applyRecorder(Recorder recorder) {
		Pair<Integer, Integer> sizeXZ = recorder.getCurrentSize();
		Pair<Integer, Integer> offset = regionManager.reserve(sizeXZ.getLeft(), sizeXZ.getRight());
		return recorder.applyTo(new WorldProxies.OffsetProxy(proxied, offset.getLeft(), offset.getRight()));
	}

}
