package mhfc.net.common.world.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import com.sk89q.worldedit.function.operation.Operation;

import mhfc.net.MHFCMain;
import mhfc.net.common.eventhandler.MHFCTickHandler;
import mhfc.net.common.eventhandler.TickPhase;
import mhfc.net.common.util.Operations;
import mhfc.net.common.world.MHFCWorldData;
import mhfc.net.common.world.MHFCWorldData.AreaInformation;
import mhfc.net.common.world.area.ActiveAreaAdapter;
import mhfc.net.common.world.area.AreaConfiguration;
import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IArea;
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
			IArea loadingArea = info.type.provideForLoading(world, info.config);
			this.nonactiveAreas.computeIfAbsent(info.type, (k) -> new ArrayList<>()).add(loadingArea);
		}
	}

	private Ticket getLoadingTicket() {
		if (loadingTicket == null) {
			loadingTicket = ForgeChunkManager.requestTicket(MHFCMain.instance(), world, Type.NORMAL);
		}
		return loadingTicket;
	}

	private void dismiss(IActiveArea active) {
		this.nonactiveAreas.get(active.getType()).add(active.getArea());
	}

	@Override
	public CompletionStage<IActiveArea> getUnusedInstance(IAreaType type) {
		Optional<IArea> chosen = nonactiveAreas.computeIfAbsent(type, (k) -> new ArrayList<>()).stream()
				.filter(a -> !a.isUnusable()).findFirst();
		if (chosen.isPresent()) {
			Active active = new Active(chosen.get(), type, this);
			nonactiveAreas.get(type).remove(active.getArea());
			return CompletableFuture.completedFuture(active);
		}
		AreaConfiguration config = newArea(type);
		CornerPosition position = config.getPosition();

		final Operation plan = type.populate(world, config);
		final ChunkCoordIntPair chunkPos = new ChunkCoordIntPair(position.posX, position.posY);
		ForgeChunkManager.forceChunk(getLoadingTicket(), chunkPos);
		final CompletableFuture<IActiveArea> areaFuture = new CompletableFuture<>();
		final Operation operation = Operations.withCallback(Operations.timingOperation(plan, 20), o -> {
			areaFuture.complete(new Active(type.provideForLoading(world, config), type, this));
			ForgeChunkManager.unforceChunk(getLoadingTicket(), chunkPos);
			MHFCMain.logger().debug("Area of type {} completed", type);
		}, o -> {
			areaFuture.cancel(true);
			ForgeChunkManager.unforceChunk(getLoadingTicket(), chunkPos);
			MHFCMain.logger().debug("Area of type {} cancelled", type);
		});

		areaFuture.whenComplete((a, ex) -> {
			if (ex != null) {
				operation.cancel();
			}
		});

		MHFCTickHandler.instance.registerOperation(TickPhase.SERVER_PRE, operation);

		return areaFuture;
	}

	private AreaConfiguration newArea(IAreaType type) {
		return saveData.newArea(type, type.configForNewArea());
	}
}
