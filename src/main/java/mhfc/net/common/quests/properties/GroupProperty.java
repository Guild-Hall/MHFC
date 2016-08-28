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
import mhfc.net.common.util.parsing.IValueHolder;
import mhfc.net.common.util.parsing.proxies.MapProxy;
import mhfc.net.common.util.parsing.proxies.MemberMethodProxy;
import mhfc.net.common.util.reflection.MethodHelper;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class GroupProperty extends Property {

	private Map<String, Property> subProperties = new HashMap<>();
	private Map<String, IValueHolder> supplementVisuals = new HashMap<>();
	private Lazy<Holder> propertyProxy;

	private GroupProperty(Runnable setDirtyParent) {
		super(setDirtyParent);
		propertyProxy = new Lazy<>(() -> Holder.valueOf(new MapProxy(subProperties, supplementVisuals)));
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
	 * Registers a visual-only entry in the group-properties. This is important to be able to introduce display
	 * functions and/or constants into the parsing context.<br>
	 * As they are visual-only, it will not be checked whether a name already exists, or if a member of the same name
	 * exists. When a member of the same name exists, the context refers to the member rather than the value holder.<br>
	 *
	 * @param notShared
	 *            a value holder that will not be synced between client and server
	 * @see MemberMethodProxy to register functions as variables
	 */
	public void newVisualSupplement(String name, IValueHolder notShared) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(notShared);
		supplementVisuals.put(name, notShared);
	}

	/**
	 * Searches clazz for a static method "methodName" and introduces the method into the context under the name of
	 * "alias". It is an error if that method is not public or can not be found.
	 *
	 * @param alias
	 * @param clazz
	 * @param methodName
	 */
	public void newVisualSupplementMethod(String alias, Class<?> clazz, String methodName) {
		newVisualSupplement(
				alias,
				Holder.valueOf(new MemberMethodProxy(MethodHelper.findStatic(clazz, methodName).get())));
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

	@Override
	public String toString() {
		return this.subProperties.toString();
	}
}
