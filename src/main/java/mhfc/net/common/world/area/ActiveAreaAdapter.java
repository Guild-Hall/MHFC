package mhfc.net.common.world.area;

public abstract class ActiveAreaAdapter implements IActiveArea {
	private boolean dismissed = false;

	@Override
	public abstract IArea getArea();

	protected abstract void onDismiss();

	@Override
	public final boolean dismiss() {
		if (!dismissed) {
			onDismiss();
			return dismissed = true;
		}
		return false;
	}
	/**
	 * Alias to {@link #dismiss()} to implement {@link AutoCloseable}.
	 */
	@Override
	public void close() throws Exception {
		dismiss();
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		dismiss();
	}

}
