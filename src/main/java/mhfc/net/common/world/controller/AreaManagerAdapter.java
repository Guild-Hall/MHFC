package mhfc.net.common.world.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import mhfc.net.common.world.area.ActiveAreaAdapter;
import mhfc.net.common.world.area.AreaConfiguration;
import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IArea;
import mhfc.net.common.world.area.IAreaType;
import mhfc.net.common.world.proxies.IWorldProxy;

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

	@Override
	public Active getUnusedInstance(IAreaType type) {
		List<IArea> list = spawnedAreas.get(type);
		if (list == null) {
			IArea newArea = newArea(type);
			spawnedAreas.put(type, Arrays.asList(newArea));
			return new Active(newArea, type, this);
		}
		IArea newArea = list.stream().filter(a -> !a.isUnusable()).findFirst().orElseGet(() -> newArea(type));
		return new Active(newArea, type, this);
	}

	private IArea newArea(IAreaType type) {
		AreaConfiguration config = type.configureNewArea();
		IWorldProxy proxy = fitNewArea(config);
		IArea contoller = type.populate(proxy, config);
		return contoller;
	}

	protected abstract IWorldProxy fitNewArea(AreaConfiguration config);
}
