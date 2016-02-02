package mhfc.net.common.world.controller;

import java.util.*;

import mhfc.net.common.world.MHFCWorldData;
import mhfc.net.common.world.MHFCWorldData.AreaInformation;
import mhfc.net.common.world.area.*;
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
			loadingArea.loadFromConfig(info.position, info.config);
			this.nonactiveAreas.computeIfAbsent(info.type, (k) -> new ArrayList<>()).add(loadingArea);
		}
	}

	private void dismiss(IActiveArea active) {
		this.nonactiveAreas.get(active.getType()).add(active.getArea());
	}

	@Override
	public Active getUnusedInstance(IAreaType type) {
		IArea chosen = nonactiveAreas.computeIfAbsent(type, (k) -> new ArrayList<>()).stream()
				.filter(a -> !a.isUnusable()).findFirst().orElse(newArea(type));
		return new Active(chosen, type, this);
	}

	private IArea newArea(IAreaType type) {
		AreaConfiguration config = type.configForNewArea();
		CornerPosition pos = saveData.newArea(type, config);
		for (int cX = 0; cX < config.getChunkSizeX(); cX++) {
			for (int cZ = 0; cZ < config.getChunkSizeZ(); cZ++) {
				if (world.getChunkFromChunkCoords(cX, cZ).isEmpty())
					continue;
				for (int x = 0; x < 16; x++) {
					for (int y = 0; y < world.getActualHeight(); y++) {
						for (int z = 0; z < 16; z++) {
							world.setBlockToAir(x, y, z);
						}
					}
				}
			}
		}
		IArea controller = type.populate(world, pos, config);
		return controller;
	}
}
