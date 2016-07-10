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

public class MHFCTickHandler {
	public static final MHFCTickHandler instance = new MHFCTickHandler();

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
			this.op.cancel();
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

	private Map<TickPhase, DoubleBufferRunnableRegistry> jobQueue = new EnumMap<>(TickPhase.class);

	private DoubleBufferRunnableRegistry getQueueFor(TickPhase phase) {
		phase = Objects.requireNonNull(phase);
		return jobQueue.computeIfAbsent(phase, p -> new DoubleBufferRunnableRegistry());
	}

	public void registerRunnable(TickPhase phase, Runnable run, Runnable cancel) {
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
		getQueueFor(phase).runAll();
	}
}
