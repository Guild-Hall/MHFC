package mhfc.net.common.world.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.tuple.Pair;

import mhfc.net.common.world.area.ActiveAreaAdapter;
import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IArea;
import mhfc.net.common.world.area.IAreaType;

public abstract class AreaManagerAdapter implements IAreaManager {

	private static class Active extends ActiveAreaAdapter {
		private IArea area;
		private IAreaType type;
		private AreaManagerAdapter ref;

		public Active(IArea area, IAreaType type, AreaManagerAdapter ref) {
			this.area = Objects.requireNonNull(area);
			this.type = Objects.requireNonNull(type);
			this.ref = Objects.requireNonNull(ref);
		}

		@Override
		public IArea getArea() {
			return area;
		}

		@Override
		public IAreaType getType() {
			return type;
		}

		@Override
		protected void onDismiss() {
			ref.dismiss(this);
		}

	}

	private Map<IAreaType, List<IArea>> spawnedAreas = new HashMap<>();

	public AreaManagerAdapter() {
	}

	/**
	 * Gets a proxy for the world represented by this Manager. Read and write
	 * should both be possible.
	 * 
	 * @return a proxy for the world
	 */
	protected abstract IWorldProxy getProxiedWorld();

	private void dismiss(IActiveArea active) {
		this.spawnedAreas.get(active.getType()).add(active.getArea());
	}

	/**
	 * Finds a place int the world for the rectangular area of sizeX*sizeZ.
	 * 
	 * @param sizeX
	 *            the x-size of the area to fit
	 * @param sizeZ
	 *            the z-size of the area to fit
	 * @return an offset (in chunks) to place the area in
	 */
	protected abstract Pair<Integer, Integer> getEmptyPlaceFor(int sizeX, int sizeZ);

	@Override
	public Active getUnusedInstance(IAreaType type) {
		List<IArea> list = spawnedAreas.get(type);
		if (list == null) {
			IArea newArea = newArea(type);
			spawnedAreas.put(type, Arrays.asList(newArea));
			return new Active(newArea, type, this);
		}
		for (IArea area : list) {
			if (area.isBlocked())
				continue;
			return new Active(area, type, this);
		}
		IArea newArea = newArea(type);
		return new Active(newArea, type, this);
	}

	private IArea newArea(IAreaType type) {
		WorldProxies.RecordingProxy recorder = new WorldProxies.RecordingProxy();
		WorldProxies.WorldProxyProxy proxy = new WorldProxies.WorldProxyProxy(recorder);
		IWorldProxy actual = getProxiedWorld();

		IArea contoller = type.populate(proxy);

		// TODO: figure out the offset of the consumed terrain, not (0, 0)
		IWorldProxy finishedProxy = recorder.applyTo(actual, 0, 0);
		proxy.setProxy(finishedProxy);
		return contoller;
	}

}
