package mhfc.net.common.quests.properties;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Function;

import com.google.common.base.Preconditions;

import mhfc.net.common.util.Lazy;
import mhfc.net.common.util.parsing.Context;
import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.proxies.MapProxy;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class GroupProperty extends Property {

	private Map<String, Property> subProperties = new HashMap<>();
	private Lazy<Holder> propertyProxy;

	private GroupProperty(Runnable setDirtyParent) {
		super(setDirtyParent);
		propertyProxy = new Lazy<>(() -> Holder.valueOf(new MapProxy(subProperties)));
	}

	@Override
	public NBTTagCompound dumpUpdates() {
		if (!pollDirty()) {
			return signalNoUpdates();
		}

		NBTTagCompound updateTag = new NBTTagCompound();
		for (Entry<String, Property> mapping : subProperties.entrySet()) {
			NBTBase elementUpdate = mapping.getValue().dumpUpdates();
			if (signalsNoUpdates(elementUpdate)) {
				continue;
			}
			String name = mapping.getKey();
			updateTag.setTag(name, elementUpdate);
		}

		return updateTag;
	}

	@Override
	public NBTTagCompound dumpAll() {
		NBTTagCompound updateTag = new NBTTagCompound();
		for (Entry<String, Property> mapping : subProperties.entrySet()) {
			NBTBase elementUpdate = mapping.getValue().dumpAll();
			String name = mapping.getKey();
			updateTag.setTag(name, elementUpdate);
		}
		return updateTag;
	}

	@Override
	public void updateFrom(NBTBase nbt) {
		NBTTagCompound tag = NBTType.TAG_COMPOUND.assureTagType(nbt);
		for (Entry<String, Property> mapping : subProperties.entrySet()) {
			String name = mapping.getKey();
			if (!tag.hasKey(name)) {
				continue;
			}
			mapping.getValue().updateFrom(tag.getTag(name));
		}
	}

	@Override
	public Holder snapshot() throws Throwable {
		return propertyProxy.get();
	}

	/**
	 * Registers a new member in the property group. The second parameter is usually by a static method call to the type
	 * of property. E.g:
	 *
	 * <pre>
	 * <code>
	 * group.newMember("endTime", IntProperty.construct(3));
	 * </code>
	 * </pre>
	 *
	 * @param name
	 *            the name of th property. must be unique in this group and conform to the specs of {@link Context}.
	 * @param constructor
	 * @return the property to be used.
	 */
	public <P extends Property> P newMember(String name, Function<Runnable, P> constructor) {
		name = Context.checkKeySyntax(name);
		Preconditions.checkArgument(!subProperties.containsKey(name), "Member name " + name + " already taken");
		P prop = Objects.requireNonNull(constructor.apply(getDirtyPropagator()));
		subProperties.put(name, prop);
		return prop;
	}

	/**
	 * Can be used in {@link GroupProperty#newMember(String, Function)}
	 *
	 * @param initialValue
	 *            the initial value of the property
	 * @return
	 */
	public static Function<Runnable, GroupProperty> construct() {
		return GroupProperty::new;
	}

	public static GroupProperty makeRootProperty() {
		return new GroupProperty(null);
	}
}
