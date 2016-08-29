package mhfc.net.common.quests.properties;

import java.util.function.Function;

import mhfc.net.common.util.parsing.Holder;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;

public class IntProperty extends Property {
	private int value;

	private IntProperty(Runnable setDirtyParent, int initialValue) {
		super(setDirtyParent);
		value = initialValue;
	}

	public int get() {
		return value;
	}

	public void set(int newValue) {
		this.value = newValue;
		setDirty();
	}

	public void inc() {
		set(get() + 1);
	}

	public void decr() {
		set(get() - 1);
	}

	@Override
	public NBTBase dumpUpdates() {
		if (!pollDirty()) {
			return signalNoUpdates();
		}
		return dumpAll();
	}

	@Override
	public NBTBase dumpAll() {
		return new NBTTagInt(value);
	}

	@Override
	public void updateFrom(NBTBase nbtTag) {
		value = NBTType.TAG_INT.assureTagType(nbtTag).func_150287_d();
	}

	@Override
	public Holder snapshot() throws Throwable {
		return Holder.valueOf(value);
	}

	/**
	 * Can be used in {@link GroupProperty#newMember(String, Function)}
	 *
	 * @param initialValue
	 *            the initial value of the property
	 * @return
	 */
	public static Function<Runnable, IntProperty> construct(int initialValue) {
		return r -> new IntProperty(r, initialValue);
	}

	@Override
	public String toString() {
		return Integer.toString(value);
	}
}
