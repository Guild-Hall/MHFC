package mhfc.net.common.util.parsing.valueholders;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Objects;

import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.IValueHolder;

/**
 * Represents a member of an {@link IValueHolder}. This is dynamically determined based on the currently present origin
 * in the origin.<br>
 * The name of the field on the other hand is <b>not</b> dynamic.
 *
 * @author WorldSEnder
 */
public class MemberAccess implements IValueHolder {
	private static class FieldProxy {
		private final Field parent;
		private final Class<?> declaringClass;
		private final String fieldName;
		private final Class<?> fieldC;
		private final boolean isArrayLength; // Special case....
		private final Throwable fieldNotFound;

		public FieldProxy() {
			this.parent = null;
			this.fieldName = "length";
			this.declaringClass = Object.class; // Best guess for an array
			this.fieldC = int.class;
			this.isArrayLength = true;
			this.fieldNotFound = null;
			;
		}

		public FieldProxy(Field f) {
			this.parent = Objects.requireNonNull(f);
			this.fieldName = f.getName();
			this.declaringClass = f.getDeclaringClass();
			this.fieldC = f.getType();
			this.isArrayLength = false;
			this.fieldNotFound = null;
		}

		public FieldProxy(Class<?> notFoundIn, String name) {
			this.parent = null;
			this.fieldName = Objects.requireNonNull(name);
			this.declaringClass = Objects.requireNonNull(notFoundIn);
			this.fieldC = IValueHolder.EMPTY_CLASS;
			this.isArrayLength = false;
			this.fieldNotFound = new IllegalArgumentException(
					String.format("Couldn't find the field %s.%s", this.declaringClass.getName(), this.fieldName));
		}

		public Class<?> getType() {
			return this.fieldC;
		}

		public Holder get(Holder instance) {
			if (!this.isArrayLength && this.parent == null) {
				return Holder.failedComputation(this.fieldNotFound);
			}
			if (!instance.isValid()) {
				return Holder
						.failedComputation(
								new IllegalArgumentException(
										String.format(
												"Instance for field %s.%s could not be computed",
												this.declaringClass.getName(),
												this.fieldName),
										instance.getFailCause()));
			}
			assert (!declaringClass.isPrimitive());
			Object inst = instance.getAs(this.declaringClass);
			if (inst == null) {
				return Holder.failedComputation(
						new IllegalArgumentException(
								String.format(
										"Can't access field %s.%s on null instance",
										this.declaringClass.getName(),
										this.fieldName)));
			}
			if (this.isArrayLength) {
				return Holder.valueOf(Array.getLength(inst));
			}
			try {
				this.parent.setAccessible(true);
				Object fieldValue = this.parent.get(inst);
				if (void.class.isAssignableFrom(fieldC)) {
					return Holder.empty();
				}
				if (boolean.class.isAssignableFrom(fieldC)) {
					Boolean boxed = (Boolean) fieldValue;
					return Holder.valueOf(boxed.booleanValue());
				}
				if (char.class.isAssignableFrom(fieldC)) {
					return Holder.valueOf(((Character) fieldValue).charValue());
				}
				if (byte.class.isAssignableFrom(fieldC)) {
					return Holder.valueOf(((Byte) fieldValue).byteValue());
				}
				if (short.class.isAssignableFrom(fieldC)) {
					return Holder.valueOf(((Short) fieldValue).shortValue());
				}
				if (int.class.isAssignableFrom(fieldC)) {
					return Holder.valueOf(((Integer) fieldValue).intValue());
				}
				if (long.class.isAssignableFrom(fieldC)) {
					return Holder.valueOf(((Long) fieldValue).longValue());
				}
				if (float.class.isAssignableFrom(fieldC)) {
					return Holder.valueOf(((Float) fieldValue).floatValue());
				}
				if (double.class.isAssignableFrom(fieldC)) {
					return Holder.valueOf(((Double) fieldValue).doubleValue());
				}
				// Java compiler, ty
				// Holder.valueOf(fieldC.cast(fieldValue), fieldC);
				return fieldValue == null ? Holder.typedNull(fieldC) : Holder.valueOfIfPresent(fieldValue);
			} catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
				return Holder.failedComputation(
						new IllegalArgumentException(
								String.format(
										"Accessing the field %s.%s failed on %s",
										this.declaringClass.getName(),
										this.fieldName,
										inst),
								e));
			}
		}
	}

	private static final FieldProxy arrayLengthProxy = new FieldProxy();

	private static FieldProxy resolveField(Class<?> originC2, String memberName) {
		// TODO: use a cache to speedup performance
		if (originC2.isArray() && memberName.equals("length")) {
			return arrayLengthProxy;
		}
		try {
			return new FieldProxy(originC2.getField(memberName));
		} catch (NoSuchFieldException e) {
			return new FieldProxy(originC2, memberName);
		} catch (SecurityException e) {
			return new FieldProxy(originC2, memberName);
		}
	}

	/**
	 * Represents a member of a snapshotted {@link IValueHolder}. The current value of the member is determined at the
	 * time {@link #snapshot()} and therelike is called, but the origin's Class is snapshot.
	 *
	 * @author WorldSEnder
	 *
	 */
	public static class BoundMemberAccess implements IValueHolder {
		private final IValueHolder origin;
		private final Class<?> originC;
		private final FieldProxy field;
		private final Class<?> fieldC;

		/**
		 *
		 * @param object
		 * @param memberName
		 */
		public BoundMemberAccess(IValueHolder object, String memberName) {
			if (!object.isClassSnapshot()) {
				throw new IllegalArgumentException("objects class must be snapshot");
			}
			this.origin = object;
			this.originC = this.origin.getType();
			this.field = resolveField(originC, memberName);
			this.fieldC = field.getType();
		}

		@Override
		public Holder snapshot() {
			return this.field.get(this.origin.snapshot());
		}

		@Override
		public Class<?> getType() {
			return fieldC;
		}

		@Override
		public boolean isClassSnapshot() {
			return true;
		}
	}

	public static IValueHolder makeMemberAccess(IValueHolder holder, String memberName) {
		if (holder.isClassSnapshot()) {
			return new BoundMemberAccess(holder, memberName);
		}
		return new MemberAccess(holder, memberName);
	}

	private IValueHolder origin;
	private String name;

	private MemberAccess(IValueHolder object, String memberName) {
		this.origin = object;
		this.name = memberName;
	}

	@Override
	public Holder snapshot() {
		Holder instance = this.origin.snapshot();
		FieldProxy field = resolveField(instance.getType(), this.name);
		return field.get(instance);
	}

	@Override
	public Class<?> getType() {
		Class<?> instanceC = this.origin.getType();
		FieldProxy field = resolveField(instanceC, this.name);
		return field.getType();
	}
}
