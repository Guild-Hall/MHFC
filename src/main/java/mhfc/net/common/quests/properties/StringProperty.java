package mhfc.net.common.quests.properties;

import java.util.function.Function;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagString;

public class StringProperty extends ImmutableProperty<String> {
	private static NBTBase dump(String value) {
		return new NBTTagString(value);
	}

	private static String load(String s, NBTBase nbt) {
		return NBTType.TAG_STRING.assureTagType(nbt).func_150285_a_();
	}

	private StringProperty(Runnable parentSetDirty, String initialValue) {
		super(parentSetDirty, StringProperty::dump, StringProperty::load, initialValue);
	}

	/**
	 * Can be used in {@link GroupProperty#newMember(String, Function)}
	 *
	 * @param initialValue
	 *            the initial value of the property
	 * @return
	 */
	public static Function<Runnable, StringProperty> construct(String initialValue) {
		return r -> new StringProperty(r, initialValue);
	}

	@Override
	public String toString() {
		return "\"" + get() + "\"";
	}
}
