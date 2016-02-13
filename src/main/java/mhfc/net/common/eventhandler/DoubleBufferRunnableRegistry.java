package mhfc.net.common.eventhandler;

import java.util.ArrayList;
import java.util.List;

public class DoubleBufferRunnableRegistry {
	private Object guard = new Object();

	private List<Runnable> current = new ArrayList<>();
	private List<Runnable> other = new ArrayList<>();

	public void register(Runnable r) {
		synchronized (guard) {
			current.add(r);
		}
	}

	public void runAll() {
		List<Runnable> toRun;
		synchronized (guard) {
			toRun = current;
			current = other;
			current.clear();
			other = toRun;
		}
		for (Runnable r : toRun) {
			r.run();
		}
	}
}
