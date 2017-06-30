package mhfc.net.common.eventhandler;

import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public interface DelayedJob {
	public abstract class PreTickJob implements DelayedJob {
		public abstract void executeJob();

		@Override
		public void executeJob(Phase phase) {
			if (phase != Phase.START) {
				return;
			}
			executeJob();
		}
	}

	public abstract class PostTickJob implements DelayedJob {
		public abstract void executeJob();

		@Override
		public void executeJob(Phase phase) {
			if (phase != Phase.END) {
				return;
			}
			executeJob();
		}
	}

	public void executeJob(Phase phase);
}
