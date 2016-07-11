package mhfc.net.common.eventhandler;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;

import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.RunContext;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import mhfc.net.MHFCMain;
import mhfc.net.common.network.NetworkTracker;
import mhfc.net.common.util.services.IServiceAccess;
import mhfc.net.common.util.services.IServiceHandle;
import mhfc.net.common.util.services.IServiceKey;
import mhfc.net.common.util.services.IServicePhaseHandle;
import mhfc.net.common.util.services.Services;

public class MHFCTickHandler {
	public static final MHFCTickHandler instance = new MHFCTickHandler();

	public static void staticInit() {}

	private static class OperationWrapper implements Runnable {
		private Operation op;
		private RunContext context;
		private DoubleBufferRunnableRegistry registry;
		private Runnable cancel;

		public OperationWrapper(Operation op, DoubleBufferRunnableRegistry registry) {
			this.op = Objects.requireNonNull(op);
			this.context = new RunContext();
			this.registry = Objects.requireNonNull(registry);
			this.cancel = this::cancelOp;
		}

		protected void cancelOp() {
			if (op != null) {
				this.op.cancel();
			}
		}

		@Override
		public void run() {
			try {
				this.op = op.resume(context);
			} catch (WorldEditException e) {
				MHFCMain.logger().error("Exception when handling an operation", e);
				return;
			}
			if (this.op != null) {
				registry.register(this, cancel);
			}
		}
	}

	private static Map<TickPhase, IServiceKey<DoubleBufferRunnableRegistry>> jobQueue = new EnumMap<>(TickPhase.class);

	static {
		for (TickPhase phase : TickPhase.values()) {
			boolean isServerTick = phase == TickPhase.SERVER_POST || phase == TickPhase.SERVER_PRE;
			boolean isClientTick = phase == TickPhase.CLIENT_POST || phase == TickPhase.CLIENT_PRE;
			IServiceAccess<DoubleBufferRunnableRegistry> phaseHandler = Services.instance
					.registerService("tick " + phase, new IServiceHandle<DoubleBufferRunnableRegistry>() {
						@Override
						public void startup(DoubleBufferRunnableRegistry instance) {}

						@Override
						public void shutdown(DoubleBufferRunnableRegistry instance) {
							instance.cancel();
						}
					}, DoubleBufferRunnableRegistry::new);
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

	private DoubleBufferRunnableRegistry getQueueFor(TickPhase phase) {
		assert phase != null;
		return jobQueue.get(phase).getService();
	}

	public void registerRunnable(TickPhase phase, Runnable run, Runnable cancel) {
		Objects.requireNonNull(phase);
		getQueueFor(phase).register(run, cancel);
	}

	public void registerOperation(TickPhase phase, final Operation op) {
		registerOperation(phase, op, new RunContext());
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
	public void registerOperation(TickPhase phase, final Operation op, final RunContext context) {
		Objects.requireNonNull(phase);
		if (op == null) {
			return;
		}
		DoubleBufferRunnableRegistry queue = getQueueFor(phase);
		OperationWrapper runnable = new OperationWrapper(op, queue);
		queue.register(runnable, runnable.cancel);
	}

	public Executor poolFor(final TickPhase phase) {
		return command -> registerRunnable(phase, command, null);
	}

	@SubscribeEvent
	public void onRenderTick(TickEvent.RenderTickEvent event) {
		onTick(event);
	}

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		onTick(event);
	}

	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event) {
		onTick(event);
	}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		onTick(event);
	}

	private void onTick(TickEvent event) {
		TickPhase phase = TickPhase.forEvent(event);
		IServiceKey<DoubleBufferRunnableRegistry> key = jobQueue.get(phase);
		key.getServiceProvider().getServiceFor(key).ifPresent(DoubleBufferRunnableRegistry::runAll);
	}
}
