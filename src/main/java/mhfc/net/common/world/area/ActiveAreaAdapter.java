package mhfc.net.common.world.area;

public abstract class ActiveAreaAdapter implements IActiveArea {
	private boolean dismissed = false;

	@Override
	public abstract IArea getArea();

	protected abstract void onDismiss();

	@Override
	public final void dismiss() {
		if (!dismissed) {
			onDismiss();
			getArea().getSpawnController().clearQueues();
			getArea().getSpawnController().clearArea();
			dismissed = true;
		}
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		dismiss();
	}

	@Override
	final public void you_should_probably_inherit_from_ActiveAreaAdapter() {}
}
