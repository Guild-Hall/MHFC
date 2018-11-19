package mhfc.net.common.eventhandler;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

import com.google.common.util.concurrent.Runnables;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.RunContext;

import mhfc.net.MHFCMain;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.network.NetworkTracker;
import mhfc.net.common.util.Operations;
import mhfc.net.common.util.services.IServiceAccess;
import mhfc.net.common.util.services.IServiceHandle;
import mhfc.net.common.util.services.IServiceKey;
import mhfc.net.common.util.services.IServicePhaseHandle;
import mhfc.net.common.util.services.Services;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber(modid = ResourceInterface.main_modid)
public class MHFCTickHandler {

	private static class OperationWrapper implements Runnable {
		private Operation op;
		private final RunContext context;
		private final TickPhase phase;
		private final Runnable cancel;
		private final CompletableFuture<Void> future;

		public OperationWrapper(Operation op, RunContext context, TickPhase phase) {
			this.op = Objects.requireNonNull(op);
			this.context = context;
			this.phase = Objects.requireNonNull(phase);
			cancel = this::cancelOp;
			future = new CompletableFuture<>();
		}

		protected void cancelOp() {
			assert op != null;
			future.cancel(true);
			op.cancel();
		}

		@Override
		public void run() {
			Operation followup;
			try {
				followup = op.resume(context);
			} catch (final Throwable e) {
				MHFCMain.logger().error("Exception when handling an operation", e);
				future.completeExceptionally(e);
				return;
			}
			if (followup != null) {
				op = followup;
				final CompletionStage<Void> registeredFuture = MHFCTickHandler.registerWrapper(phase, this);
				assert future == registeredFuture;
			} else {
				future.complete(null);
			}
		}

		public CompletionStage<Void> getCompletionStage() {
			return future;
		}

		public Runnable getRun() {
			return this;
		}

		public Runnable getCancel() {
			return cancel;
		}
	}

	private static Map<TickPhase, IServiceKey<DoubleBufferRunnableRegistry>> jobQueue = new EnumMap<>(TickPhase.class);

	static {
		for (final TickPhase phase : TickPhase.values()) {
			final boolean isServerTick = phase == TickPhase.SERVER_POST || phase == TickPhase.SERVER_PRE;
			final boolean isClientTick = phase == TickPhase.CLIENT_POST || phase == TickPhase.CLIENT_PRE;
			final IServiceAccess<DoubleBufferRunnableRegistry> phaseHandler = Services.instance
					.registerService("tick " + phase, new IServiceHandle<DoubleBufferRunnableRegistry>() {
						@Override
						public DoubleBufferRunnableRegistry createInstance() {
							return new DoubleBufferRunnableRegistry();
						}

						@Override
						public void startup(DoubleBufferRunnableRegistry instance) {}

						@Override
						public void shutdown(DoubleBufferRunnableRegistry instance) {
							instance.cancel();
						}
					});
			if (isServerTick) {
				phaseHandler.addTo(MHFCMain.serverActivePhase, IServicePhaseHandle.noInit());
			} else if (isClientTick) {
				phaseHandler.addTo(NetworkTracker.clientConnectedPhase, IServicePhaseHandle.noInit());
			} else {
				phaseHandler.addTo(MHFCMain.preInitPhase, IServicePhaseHandle.noInit());
			}
			jobQueue.put(phase, phaseHandler);
		}
	}

	// Force static initialization
	public static void staticInit() {}

	private static Optional<DoubleBufferRunnableRegistry> getQueueFor(TickPhase phase) {
		assert phase != null;
		return jobQueue.get(phase).get();
	}

	public static CompletionStage<Void> registerRunnable(TickPhase phase, Runnable run, Runnable cancel) {
		return registerOperation(phase, Operations.wrapping(run, cancel));
	}

	public static CompletionStage<Void> registerOperation(TickPhase phase, final Operation op) {
		return registerOperation(phase, op, new RunContext());
	}

	/**
	 * Registers an operation to get called every time the {@link TickPhase} phase triggers. Then
	 * <code>op.resume(context)</code> will get called with the context given. If the result is not <code>null</code>,
	 * the returned {@link Operation} will get registered for the same phase and with the same context.
	 *
	 * @param phase
	 *            the phase to tick in, non-null
	 * @param op
	 *            the operation to call, <code>null</code> will be ignored.
	 * @param context
	 *            the context to feed to the operation
	 */
	public static CompletionStage<Void> registerOperation(
			TickPhase phase,
			final Operation op,
			final RunContext context) {
		if (op == null) {
			return CompletableFuture.completedFuture(null);
		}
		final OperationWrapper wrapper = new OperationWrapper(op, context, phase);
		return registerWrapper(phase, wrapper);
	}

	private static CompletionStage<Void> registerWrapper(TickPhase phase, OperationWrapper wrapper) {
		Objects.requireNonNull(phase);
		final Optional<DoubleBufferRunnableRegistry> serviceQueue = getQueueFor(phase);
		if (serviceQueue.isPresent()) {
			serviceQueue.get().register(wrapper.getRun(), wrapper.getCancel());
		} else {
			MHFCMain.logger().error("Tried registering for phase {} but service is not running", phase);
			wrapper.cancelOp();
		}
		return wrapper.getCompletionStage();
	}

	public static Executor poolFor(final TickPhase phase) {
		return command -> registerRunnable(phase, command, null);
	}

	@SubscribeEvent
	public static void onRenderTick(TickEvent.RenderTickEvent event) {
		onTick(event);
	}

	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent event) {
		onTick(event);
	}

	@SubscribeEvent
	public static void onServerTick(TickEvent.ServerTickEvent event) {
		onTick(event);
	}

	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		onTick(event);
	}

	private static void onTick(TickEvent event) {
		final TickPhase phase = TickPhase.forEvent(event);
		final IServiceKey<DoubleBufferRunnableRegistry> key = jobQueue.get(phase);
		key.getServiceProvider().getServiceFor(key).ifPresent(DoubleBufferRunnableRegistry::runAll);
	}

	public static CompletionStage<Void> schedule(TickPhase phase, int ticksDelayed, Runnable run) {
		return schedule(phase, ticksDelayed, run, Runnables.doNothing());
	}

	public static CompletionStage<Void> schedule(TickPhase phase, int ticksDelayed, Runnable run, Runnable cancel) {
		return registerOperation(phase, Operations.delayed(Operations.wrapping(run, cancel), ticksDelayed));
	}
}
