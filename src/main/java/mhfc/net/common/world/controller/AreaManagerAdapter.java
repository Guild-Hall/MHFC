package mhfc.net.common.world.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import mhfc.net.common.world.MHFCWorldData;
import mhfc.net.common.world.MHFCWorldData.AreaInformation;
import mhfc.net.common.world.area.ActiveAreaAdapter;
import mhfc.net.common.world.area.AreaConfiguration;
import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IArea;
import mhfc.net.common.world.area.IAreaType;
import net.minecraft.world.World;

public class AreaManagerAdapter implements IAreaManager {

	private static class Active extends ActiveAreaAdapter {
		private IArea area;
		private IAreaType type;
		private AreaManagerAdapter ref;

		public Active(IArea area, IAreaType type, AreaManagerAdapter ref) {
			this.area = Objects.requireNonNull(area);
			this.type = Objects.requireNonNull(type);
			this.ref = Objects.requireNonNull(ref);
			this.area.onAcquire();
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
			this.area.onDismiss();
			ref.dismiss(this);
		}

	}

	protected Map<IAreaType, List<IArea>> nonactiveAreas = new HashMap<>();
	private MHFCWorldData saveData;
	protected final World world;

	public AreaManagerAdapter(World world, MHFCWorldData saveData) {
		this.world = Objects.requireNonNull(world);
		this.saveData = Objects.requireNonNull(saveData);
		Collection<AreaInformation> loadedAreas = this.saveData.getAllSpawnedAreas();
		for (AreaInformation info : loadedAreas) {
			IArea loadingArea = info.type.provideForLoading(world);
			loadingArea.loadFromConfig(info.config);
			this.nonactiveAreas.computeIfAbsent(info.type, (k) -> new ArrayList<>()).add(loadingArea);
		}
	}

	private void dismiss(IActiveArea active) {
		this.nonactiveAreas.get(active.getType()).add(active.getArea());
	}

	@Override
	public Active getUnusedInstance(IAreaType type) {
		List<IArea> list = nonactiveAreas.computeIfAbsent(type, (k) -> new ArrayList<>());
		IArea chosen = null;
		for (Iterator<IArea> it = list.iterator(); it.hasNext();) {
			IArea area = it.next();
			if (area.isUnusable()) {
				continue;
			}
			chosen = area;
			it.remove();
		}
		if (chosen == null) {
			chosen = newArea(type);
		}
		return new Active(chosen, type, this);
	}

	private IArea newArea(IAreaType type) {
		AreaConfiguration config = type.configForNewArea();
		CornerPosition pos = saveData.newArea(type, config);
		// TODO make sure that chunks are cleared?
		IArea controller = type.populate(world, pos, config);
		return controller;
	}

}
