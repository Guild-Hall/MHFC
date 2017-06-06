package mhfc.net.common.world.area;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public abstract class ActiveAreaAdapter implements IActiveArea {
	private boolean engaged = false;
	private boolean dismissed = false;

	@Override
	public abstract IArea getArea();

	protected abstract void onEngage();
	protected abstract void onDismiss();

	protected boolean isNotPartOfRaid(Entity entity) {
		return !(entity instanceof EntityPlayer);
	}

	@Override
	public void engage() throws IllegalStateException {
		if (!engaged) {
			onEngage();
			// FIXME: chunks may not be loaded at this point, which makes this kind of pointless
			getArea().getSpawnController().clearAreaOf(this::isNotPartOfRaid);
			engaged = true;
		}
	}

	@Override
	public final void dismiss() {
		if (!dismissed && engaged) {
			onDismiss();
			// FIXME: chunks may not be loaded at this point, which makes this kind of pointless
			getArea().getSpawnController().clearQueues();
			getArea().getSpawnController().clearAreaOf(this::isNotPartOfRaid);
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
