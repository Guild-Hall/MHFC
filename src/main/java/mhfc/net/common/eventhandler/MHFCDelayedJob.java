package mhfc.net.common.eventhandler;

public interface MHFCDelayedJob {
	public void executeJob();

	public int getInitialDelay();
}
