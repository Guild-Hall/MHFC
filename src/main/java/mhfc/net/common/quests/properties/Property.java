package mhfc.net.common.quests.properties;

import mhfc.net.common.util.parsing.IValueHolder;
import net.minecraft.nbt.NBTBase;

public abstract class Property implements IValueHolder {
	public static boolean signalsNoUpdates(NBTBase nbtTag) {
		return nbtTag == null;
	}

	private boolean isDirty;
	private final Runnable setParentDirty;
	private final Runnable setDirty;

	public Property(Runnable setParentDirty) {
		this.setParentDirty = setParentDirty;
		if (setParentDirty == null) {
			this.setDirty = () -> {
				setThisDirty(true);
			};
		} else {
			this.setDirty = () -> {
				setThisDirty(true);
				this.setParentDirty.run();
			};
		}
		setDirty();
	}

	private void setThisDirty(boolean newValue) {
		this.isDirty = newValue;
	}

	/**
	 * Marks this property and all its parents as dirty so that they are updated.
	 */
	protected void setDirty() {
		setDirty.run();
	}

	/**
	 * Returns if the property was marked dirty before calling this method. Then the dirty status is <b>reset</b>, the
	 * update is not propagated.
	 *
	 * @return
	 */
	protected boolean pollDirty() {
		boolean wasDirty = isDirty;
		// TODO: concurrency problems?
		setThisDirty(false);
		return wasDirty;
	}

	/**
	 * Returns a runnable to be propagated to children as a parent call. Calling the runnable sets this element's dirty
	 * status and propagates the call upwards.
	 *
	 * @return
	 */
	protected Runnable getDirtyPropagator() {
		return setDirty;
	}

	protected <E extends NBTBase> E signalNoUpdates() {
		return null;
	}

	/**
	 * Dumps _only_ dirty attributes into a NBTTree. If the property is not dirty, then {@link #signalNoUpdates()}
	 * should be returned.
	 *
	 * @return
	 */
	public abstract NBTBase dumpUpdates();

	public abstract NBTBase dumpAll();

	public abstract void updateFrom(NBTBase nbtTag);
}
