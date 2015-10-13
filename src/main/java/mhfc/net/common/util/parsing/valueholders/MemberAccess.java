package mhfc.net.common.util.parsing.valueholders;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.IValueHolder;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.google.common.collect.ComputationException;

/**
 * Represents a member of an {@link IValueHolder}. This is dynamically
 * determined based on the currently present origin in the origin.<br>
 * The name of the field on the other hand is <b>not</b> dynamic.
 *
 * @author WorldSEnder
 */
public class MemberAccess implements IValueHolder {
	// TODO: use a cache to speedup performance

	private static Field resolveField(Class<?> clazz, String name) {
		if (clazz.isArray()) {
			// Special because reflection doesn't find the field "length"
			// see http://stackoverflow.com/q/11097658/
			if (!name.equals("length"))
				throw new ComputationException(new IllegalArgumentException(
						clazz.getName() + " has no field " + name));
			return null; // Marker for ARRAY_LENGTH
		}
		Field f = FieldUtils.getField(clazz, name);
		if (f == null)
			throw new ComputationException(new IllegalArgumentException(
					clazz.getName() + " has no field " + name));
		return f;
	}

	private static Holder readField(Field f, Object obj) {
		try {
			if (f == null) { // Marker for array_length
				return Holder.valueOf(Array.getLength(obj));
			}
			Class<?> classOfField = f.getType();
			Object o = FieldUtils.readField(f, obj);
			if (boolean.class.isAssignableFrom(classOfField)) {
				Boolean wrapper = (Boolean) o;
				return Holder.valueOf(wrapper.booleanValue());
			}
			if (char.class.isAssignableFrom(classOfField)) {
				Character wrapper = (Character) o;
				return Holder.valueOf(wrapper.charValue());
			}
			if (byte.class.isAssignableFrom(classOfField)) {
				Byte wrapper = (Byte) o;
				return Holder.valueOf(wrapper.byteValue());
			}
			if (short.class.isAssignableFrom(classOfField)) {
				Short wrapper = (Short) o;
				return Holder.valueOf(wrapper.shortValue());
			}
			if (int.class.isAssignableFrom(classOfField)) {
				Integer wrapper = (Integer) o;
				return Holder.valueOf(wrapper.intValue());
			}
			if (long.class.isAssignableFrom(classOfField)) {
				Long wrapper = (Long) o;
				return Holder.valueOf(wrapper.longValue());
			}
			if (float.class.isAssignableFrom(classOfField)) {
				Float wrapper = (Float) o;
				return Holder.valueOf(wrapper.floatValue());
			}
			if (double.class.isAssignableFrom(classOfField)) {
				Double wrapper = (Double) o;
				return Holder.valueOf(wrapper.doubleValue());
			}
			return Holder.valueOfUnsafe(o, classOfField);
		} catch (IllegalAccessException | IllegalArgumentException e) {
			throw new ComputationException(e);
		}
	}

	private static Class<?> classOfField(Field f) {
		if (f == null) // Marker for array_length
			return int.class;
		return f.getType();
	}
	/**
	 * Represents a member of a snapshotted {@link IValueHolder}. The current
	 * value of the member is determined at the time {@link #snapshot()} and
	 * therelike is called, but the origin's Class is snapshot.
	 *
	 * @author WorldSEnder
	 *
	 */
	public static class BoundMemberAccess implements IValueHolder {
		private final IValueHolder origin;
		private final Field field;
		private final Class<?> classOfField;
		/**
		 *
		 * @param object
		 * @param memberName
		 */
		public BoundMemberAccess(IValueHolder object, String memberName) {
			this.origin = object.snapshotClass();
			Class<?> originC = this.origin.getContainedClass();
			this.field = resolveField(originC, memberName);
			assert (!originC.isPrimitive());
			this.classOfField = classOfField(field);
		}

		@Override
		public Holder snapshot() {
			Object o = origin.getAs(Object.class);
			return readField(field, o);
		}

		@Override
		public Class<?> getContainedClass() {
			return classOfField;
		}
		@Override
		public BoundMemberAccess snapshotClass() {
			return this;
		}
	}
	private IValueHolder origin;
	private String name;

	public MemberAccess(IValueHolder object, String memberName) {
		this.origin = object;
		this.name = memberName;
	}

	@Override
	public BoundMemberAccess snapshotClass() {
		return new BoundMemberAccess(origin, name);
	}

	@Override
	public Holder snapshot() {
		Holder value = this.origin.snapshot();
		Class<?> clazz = value.getContainedClass();
		Field f = resolveField(clazz, name);
		Object asObject = value.getAs(Object.class);
		return readField(f, asObject);
	}

	@Override
	public Class<?> getContainedClass() {
		Class<?> storedC = this.origin.getContainedClass();
		Field f = resolveField(storedC, name);
		return classOfField(f);
	}
}
