package mhfc.net.common.quests.properties;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import mhfc.net.common.util.parsing.Holder;
import net.minecraft.nbt.NBTBase;

/**
 * Provides a way to store arbitrary properties.
 *
 * @author WorldSEnder
 *
 * @param <E>
 */
public class ImmutableProperty<E> extends Property {
	private E value;
	private final Function<E, NBTBase> dumpFunction;
	private final BiFunction<E, NBTBase, E> updateFunction;

	protected ImmutableProperty(
			Runnable setDirtyParent,
			Function<E, NBTBase> dump,
			BiFunction<E, NBTBase, E> updater,
			E initialValue) {
		super(setDirtyParent);
		value = initialValue;
		this.updateFunction = Objects.requireNonNull(updater);
		this.dumpFunction = Objects.requireNonNull(dump);
	}

	/**
	 * Sets a new value
	 *
	 * @param newValue
	 */
	public void set(E newValue) {
		value = newValue;
		setDirty();
	}

	public E get() {
		return value;
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
		return dumpFunction.apply(value);
	}

	@Override
	public void updateFrom(NBTBase nbtTag) {
		value = updateFunction.apply(value, nbtTag);
	}

	@Override
	public Holder snapshot() throws Throwable {
		return Holder.valueOf(value);
	}
}
