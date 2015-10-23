package mhfc.net.common.world.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import mhfc.net.common.world.area.ActiveAreaAdapter;
import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IArea;
import mhfc.net.common.world.area.IAreaType;
import mhfc.net.common.world.controller.WorldProxies.Recorder;

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

	private void dismiss(IActiveArea active) {
		this.spawnedAreas.get(active.getType()).add(active.getArea());
	}

	/**
	 * Applies the {@link Recorder} to the world represented by this
	 * {@link IAreaManager} and returns an area manger that represents the
	 * equivalent of the recorded blocks in the "real world".
	 * 
	 * @param recorder
	 *            the recorder to apply.
	 * @return a representation of the recorder in the world this AreaManager
	 *         manages. The {@link IWorldProxy} may chose not to allow any
	 *         set-operations
	 */
	protected abstract IWorldProxy applyRecorder(Recorder recorder);

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
		WorldProxies.Recorder recorder = new WorldProxies.Recorder();
		WorldProxies.WorldProxyProxy proxy = new WorldProxies.WorldProxyProxy(recorder);

		IArea contoller = type.populate(proxy);

		IWorldProxy finishedProxy = applyRecorder(recorder);
		proxy.setProxy(finishedProxy);
		return contoller;
	}

}
