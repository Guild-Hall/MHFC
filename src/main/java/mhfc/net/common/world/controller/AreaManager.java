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

import mhfc.net.MHFCMain;
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
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;

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
	protected Ticket loadingTicket;

	public AreaManager(World world, MHFCWorldData saveData) {
		this.world = Objects.requireNonNull(world);
		this.saveData = Objects.requireNonNull(saveData);
		Collection<AreaInformation> loadedAreas = this.saveData.getAllSpawnedAreas();
		for (AreaInformation info : loadedAreas) {
			IArea loadingArea = info.type.provideForLoading(world);
			loadingArea.loadFromConfig(info.config);
			this.nonactiveAreas.computeIfAbsent(info.type, (k) -> new ArrayList<>()).add(loadingArea);
		}
		loadingTicket = ForgeChunkManager.requestTicket(MHFCMain.class, world, Type.NORMAL);
	}

	private void dismiss(IActiveArea active) {
		this.nonactiveAreas.get(active.getType()).add(active.getArea());
	}

	@Override
	public StagedFuture<IActiveArea> getUnusedInstance(IAreaType type) {
		Optional<IArea> chosen = nonactiveAreas.computeIfAbsent(type, (k) -> new ArrayList<>()).stream()
				.filter(a -> !a.isUnusable()).findFirst();
		if (chosen.isPresent()) {
			Active active = new Active(chosen.get(), type, this);
			nonactiveAreas.get(type).remove(active.getArea());
			return StagedFuture.wrap(CompletableFuture.completedFuture(active));
		}
		CompletableFuture<IActiveArea> area = new CompletableFuture<>();
		AreaConfiguration config = newArea(type);
		CornerPosition position = config.getPosition();

		final IAreaPlan plan = type.populate(world, config);
		final ChunkCoordIntPair chunk = new ChunkCoordIntPair(position.posX, position.posY);
		ForgeChunkManager.forceChunk(loadingTicket, chunk);

		final Operation op = Operations.timingOperation(plan.getFirstOperation(), 20);
		MHFCTickHandler.instance.registerOperation(TickPhase.SERVER_PRE, Operations.withCallback(op, () -> {
			area.complete(new Active(plan.getFinishedArea(), type, this));
			ForgeChunkManager.unforceChunk(loadingTicket, chunk);
			MHFCMain.logger.info("Area of type {} completed", type);
		} , () -> {
			ForgeChunkManager.unforceChunk(loadingTicket, chunk);
		}));
		return StagedFuture.wrap(area);
	}

	private AreaConfiguration newArea(IAreaType type) {
		AreaConfiguration config = type.configForNewArea();
		CornerPosition position = saveData.newArea(type, config);

		config.setPosition(position);

		return config;
	}
}
