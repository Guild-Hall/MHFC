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

	private Map<TickPhase, DoubleBufferRunnableRegistry> jobQueue = new EnumMap<>(TickPhase.class);

	private DoubleBufferRunnableRegistry getQueueFor(TickPhase phase) {
		phase = Objects.requireNonNull(phase);
		return jobQueue.computeIfAbsent(phase, (p) -> new DoubleBufferRunnableRegistry());
	}

	public void registerRunnable(TickPhase phase, Runnable run) {
		run = Objects.requireNonNull(run);
		getQueueFor(phase).register(run);
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
		registerRunnable(phase, () -> {
			try {
				Operation followUp = op.resume(context);
				if (followUp != null) {
					registerOperation(phase, followUp, context);
				}
			} catch (WorldEditException e) {
				MHFCMain.logger.error("Exception when handling an operation", e);
			}
		});
	}

	public Executor poolFor(final TickPhase phase) {
		return command -> registerRunnable(phase, command);
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
