package mhfc.net.common.world.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.sk89q.worldedit.function.operation.Operation;

import mhfc.net.common.eventhandler.MHFCTickHandler;
import mhfc.net.common.eventhandler.TickPhase;
import mhfc.net.common.util.Operations;
import mhfc.net.common.util.StagedFuture;
import mhfc.net.common.world.MHFCWorldData;
import mhfc.net.common.world.MHFCWorldData.AreaInformation;
import mhfc.net.common.world.area.ActiveAreaAdapter;
import mhfc.net.common.world.area.AreaConfiguration;
import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IArea;
import mhfc.net.common.world.area.IAreaPlan;
import mhfc.net.common.world.area.IAreaType;
import net.minecraft.world.World;

public class AreaManager implements IAreaManager {

	private static class Active extends ActiveAreaAdapter {
		private IArea area;
		private IAreaType type;
		private AreaManager ref;

		public Active(IArea area, IAreaType type, AreaManager ref) {
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

	public AreaManager(World world, MHFCWorldData saveData) {
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
	public StagedFuture<IActiveArea> getUnusedInstance(IAreaType type) {
		Optional<IArea> chosen = nonactiveAreas.computeIfAbsent(type, (k) -> new ArrayList<>()).stream()
				.filter(a -> !a.isUnusable()).findFirst();
		if (chosen.isPresent()) {
			return StagedFuture.wrap(CompletableFuture.completedFuture(new Active(chosen.get(), type, this)));
		}
		CompletableFuture<IActiveArea> area = new CompletableFuture<>();
		final IAreaPlan plan = newArea(type);
		final Operation op = plan.getFirstOperation();
		// FIXME: maybe loop over a few more or less per tick, or with a timelimit, not a total
		final Operation fewReps = Operations.limitedLoop(op, 2000);
		MHFCTickHandler.instance.registerOperation(TickPhase.SERVER_PRE, Operations.withCallback(fewReps, () -> {
			area.complete(new Active(plan.getFinishedArea(), type, this));
		}));
		return StagedFuture.wrap(area);
	}

	private IAreaPlan newArea(IAreaType type) {
		AreaConfiguration config = type.configForNewArea();
		CornerPosition position = saveData.newArea(type, config);
		// Clear the area
		for (int cX = 0; cX < config.getChunkSizeX(); cX++) {
			for (int cZ = 0; cZ < config.getChunkSizeZ(); cZ++) {
				if (world.getChunkFromChunkCoords(cX, cZ).isEmpty()) {
					continue;
				}
				for (int x = 0; x < 16; x++) {
					for (int y = 0; y < world.getActualHeight(); y++) {
						for (int z = 0; z < 16; z++) {
							world.setBlockToAir(x, y, z);
						}
					}
				}
			}
		}
		config.setPosition(position);
		IAreaPlan plan = type.populate(world, config);
		return plan;
	}
}
