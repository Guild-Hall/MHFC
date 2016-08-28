package mhfc.net.common.quests.properties;

import java.util.function.Function;

import mhfc.net.common.util.parsing.Holder;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;

public class BooleanProperty extends Property {

	private boolean value;

	private BooleanProperty(Runnable parentSetDirty, boolean initialValue) {
		super(parentSetDirty);
		this.value = initialValue;
	}

	public boolean get() {
		return value;
	}

	public void set(boolean newValue) {
		this.value = newValue;
		setDirty();
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
		return new NBTTagByte((byte) (value ? 1 : 0));
	}

	@Override
	public void updateFrom(NBTBase nbtTag) {
		value = NBTType.TAG_BYTE.assureTagType(nbtTag).func_150289_e() != 0;
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
	public static Function<Runnable, BooleanProperty> construct(boolean initialValue) {
		return r -> new BooleanProperty(r, initialValue);
	}

	@Override
	public String toString() {
		return Boolean.toString(value);
	}

}
