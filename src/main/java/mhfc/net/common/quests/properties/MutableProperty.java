package mhfc.net.common.quests.properties;

import java.util.function.BiFunction;
import java.util.function.Function;

import net.minecraft.nbt.NBTBase;

public class MutableProperty<E> extends ImmutableProperty<E> {

	protected MutableProperty(
			Runnable setDirtyParent,
			Function<E, NBTBase> dump,
			BiFunction<E, NBTBase, E> updater,
			E initialValue) {
		super(setDirtyParent, dump, updater, initialValue);
	}

	/**
	 * Must be called by the user whenever the value should be considered as dirty.
	 */
	@Override
	public void setDirty() {
		super.setDirty();
	}

}
