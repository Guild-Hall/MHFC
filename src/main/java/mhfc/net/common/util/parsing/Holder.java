package mhfc.net.common.util.parsing;

import java.util.Objects;

import mhfc.net.common.util.parsing.valueholders.Any;

/**
 * A Holder is the <b>immutable</b> version of an Any and the general basecase.
 *
 * @author WorldSEnder
 */
public final class Holder implements IValueHolder {
	private static class FailedComputation {
	}

	private static FailedComputation failedTag = new FailedComputation();

	private static boolean representsNothing(Class<?> clazz) {
		return clazz.isAssignableFrom(void.class);
	}

	private static <F> F throwNotAssignable(Class<?> from, Class<?> to) {
		throw new ClassCastException(from.getName() + " not assignable to " + to.getName());
	}

	public static enum DefaultPolicies implements FailPolicy {
		/**
		 * If the request fails, return the default of the value
		 */
		REALLY_LENIENT {
			@Override
			public boolean failedBoolean(Class<?> existing) {
				return false;
			}

			@Override
			public byte failedByte(Class<?> existing) {
				return 0;
			}

			@Override
			public char failedChar(Class<?> existing) {
				return 0;
			}

			@Override
			public double failedDouble(Class<?> existing) {
				return 0;
			}

			@Override
			public float failedFloat(Class<?> existing) {
				return 0;
			}

			@Override
			public int failedInt(Class<?> existing) {
				return 0;
			}

			@Override
			public long failedLong(Class<?> existing) {
				return 0;
			}

			@Override
			public <F> F failedObject(Class<?> existing, Class<F> request) {
				return null;
			}

			@Override
			public short failedShort(Class<?> existing) {
				return 0;
			}
		},
		/**
		 * If the any is not engaged, return the default value for the request
		 */
		LENIENT {
			@Override
			public boolean failedBoolean(Class<?> existing) {
				if (representsNothing(existing))
					return Boolean.FALSE;
				return throwNotAssignable(existing, boolean.class);
			}

			@Override
			public byte failedByte(Class<?> existing) {
				if (representsNothing(existing))
					return (byte) 0;
				return throwNotAssignable(existing, byte.class);
			}

			@Override
			public char failedChar(Class<?> existing) {
				if (representsNothing(existing))
					return '\0';
				return throwNotAssignable(existing, char.class);
			}

			@Override
			public double failedDouble(Class<?> existing) {
				if (representsNothing(existing))
					return 0d;
				return throwNotAssignable(existing, double.class);
			}

			@Override
			public float failedFloat(Class<?> existing) {
				if (representsNothing(existing))
					return 0f;
				return throwNotAssignable(existing, float.class);
			}

			@Override
			public int failedInt(Class<?> existing) {
				if (representsNothing(existing))
					return 0;
				return throwNotAssignable(existing, int.class);
			}

			@Override
			public long failedLong(Class<?> existing) {
				if (representsNothing(existing))
					return 0;
				return throwNotAssignable(existing, long.class);
			}

			@Override
			public <F> F failedObject(Class<?> existing, Class<F> request) {
				if (representsNothing(existing))
					return null;
				return throwNotAssignable(existing, request);
			}

			@Override
			public short failedShort(Class<?> existing) {
				if (representsNothing(existing))
					return 0;
				return throwNotAssignable(existing, short.class);
			}
		},
		/**
		 * If the request fails, throw
		 */
		STRICT {
			@Override
			public boolean failedBoolean(Class<?> existing) {
				return throwNotAssignable(existing, boolean.class);
			}

			@Override
			public byte failedByte(Class<?> existing) {
				return throwNotAssignable(existing, byte.class);
			}

			@Override
			public char failedChar(Class<?> existing) {
				return throwNotAssignable(existing, char.class);
			}

			@Override
			public double failedDouble(Class<?> existing) {
				return throwNotAssignable(existing, double.class);
			}

			@Override
			public float failedFloat(Class<?> existing) {
				return throwNotAssignable(existing, float.class);
			}

			@Override
			public int failedInt(Class<?> existing) {
				return throwNotAssignable(existing, int.class);
			}

			@Override
			public long failedLong(Class<?> existing) {
				return throwNotAssignable(existing, long.class);
			}

			@Override
			public <F> F failedObject(Class<?> existing, Class<F> request) {
				return throwNotAssignable(existing, request);
			}

			@Override
			public short failedShort(Class<?> existing) {
				return throwNotAssignable(existing, short.class);
			}
		}
	}

	public static final Holder EMPTY = new Holder();

	public static Holder empty() {
		return EMPTY;
	}

	public static final Holder FALSE = new Holder(false);
	public static final Holder TRUE = new Holder(true);

	public static Holder valueOf(boolean bool) {
		return bool ? TRUE : FALSE;
	}

	public static Holder valueOf(byte b) {
		return new Holder(b);
	}

	public static Holder valueOf(char c) {
		return new Holder(c);
	}

	public static Holder valueOf(short s) {
		return new Holder(s);
	}

	public static Holder valueOf(int i) {
		return new Holder(i);
	}

	public static Holder valueOf(long l) {
		return new Holder(l);
	}

	public static Holder valueOf(float f) {
		return new Holder(f);
	}

	public static Holder valueOf(double d) {
		return new Holder(d);
	}

	public static Holder valueOf(Object o) {
		return o == null ? empty() : new Holder(o);
	}

	/**
	 * Constructs a Holder of the given class. The class can't denote a primitve
	 * type such as int.class. The object given has to be an instance of the
	 * class given. The Holder returned may be a cached one.
	 *
	 * @param object
	 *            the object to hold, possibly null
	 * @param clazz
	 *            the exact class to hold
	 * @return an engaged Holder that holds an object of the given Class.
	 */
	public static <F> Holder valueOf(F object, Class<F> clazz) {
		Holder ret = new Holder(object, clazz);
		return ret;
	}

	public static <F> Holder valueOfUnsafe(F object, Class<?> clazz) {
		Holder ret = new Holder(object, clazz);
		return ret;
	}

	public static Holder failedComputation(Throwable cause) {
		return new Holder(failedTag, cause);
	}

	private final Object value;
	// Sad, that Java has no union
	private final boolean boolValue;
	private final byte bValue;
	private final char cValue;
	private final short sValue;
	private final int iValue;
	private final long lValue;
	private final float fValue;
	private final double dValue;

	private final Class<?> clazz;
	/**
	 * If the Holder is empty, it may have a cause to why it is.
	 */
	private final Throwable cause;

	/**
	 * Any empty Any
	 */
	private Holder() {
		this.value = null;
		this.cause = null;
		this.clazz = void.class;
		this.boolValue = false;
		this.bValue = 0;
		this.cValue = 0;
		this.dValue = 0;
		this.fValue = 0;
		this.iValue = 0;
		this.lValue = 0;
		this.sValue = 0;
	}

	/**
	 * An empty Any that is empty for a reason
	 * 
	 * @param tag
	 *            ignored tag for overload
	 * @param cause
	 *            the cause why this {@link Holder} is empty
	 */
	private Holder(FailedComputation tag, Throwable cause) {
		this.cause = Objects.requireNonNull(cause);
		this.value = null;
		this.clazz = void.class;
		this.boolValue = false;
		this.bValue = 0;
		this.cValue = 0;
		this.dValue = 0;
		this.fValue = 0;
		this.iValue = 0;
		this.lValue = 0;
		this.sValue = 0;
	}

	private Holder(boolean bool) {
		this.value = null;
		this.cause = null;
		this.clazz = boolean.class;
		this.boolValue = bool;
		this.bValue = 0;
		this.cValue = 0;
		this.dValue = 0;
		this.fValue = 0;
		this.iValue = 0;
		this.lValue = 0;
		this.sValue = 0;
	}

	private Holder(byte b) {
		this.value = null;
		this.cause = null;
		this.clazz = byte.class;
		this.boolValue = false;
		this.bValue = b;
		this.cValue = 0;
		this.dValue = 0;
		this.fValue = 0;
		this.iValue = 0;
		this.lValue = 0;
		this.sValue = 0;
	}

	private Holder(char c) {
		this.value = null;
		this.cause = null;
		this.clazz = char.class;
		this.boolValue = false;
		this.bValue = 0;
		this.cValue = c;
		this.dValue = 0;
		this.fValue = 0;
		this.iValue = 0;
		this.lValue = 0;
		this.sValue = 0;
	}

	private Holder(short s) {
		this.value = null;
		this.cause = null;
		this.clazz = short.class;
		this.boolValue = false;
		this.bValue = 0;
		this.cValue = 0;
		this.dValue = 0;
		this.fValue = 0;
		this.iValue = 0;
		this.lValue = 0;
		this.sValue = s;
	}

	private Holder(int i) {
		this.value = null;
		this.cause = null;
		this.clazz = int.class;
		this.boolValue = false;
		this.bValue = 0;
		this.cValue = 0;
		this.dValue = 0;
		this.fValue = 0;
		this.iValue = i;
		this.lValue = 0;
		this.sValue = 0;
	}

	private Holder(long l) {
		this.value = null;
		this.cause = null;
		this.clazz = long.class;
		this.boolValue = false;
		this.bValue = 0;
		this.cValue = 0;
		this.dValue = 0;
		this.fValue = 0;
		this.iValue = 0;
		this.lValue = l;
		this.sValue = 0;
	}

	private Holder(float f) {
		this.value = null;
		this.cause = null;
		this.clazz = float.class;
		this.boolValue = false;
		this.bValue = 0;
		this.cValue = 0;
		this.dValue = 0;
		this.fValue = f;
		this.iValue = 0;
		this.lValue = 0;
		this.sValue = 0;
	}

	public Holder(double d) {
		this.value = null;
		this.cause = null;
		this.clazz = double.class;
		this.boolValue = false;
		this.bValue = 0;
		this.cValue = 0;
		this.dValue = d;
		this.fValue = 0;
		this.iValue = 0;
		this.lValue = 0;
		this.sValue = 0;
	}

	/**
	 * Initializes the Any with an Object. If the value is <code>null</code>,
	 * this does the same as {@link #Holder()}. If you want to enforce a type
	 * with the value, use {@link #Holder(Object, Class)}.
	 *
	 * @param value
	 *            the value to assign.
	 * @see #valueOf(Object, Class)
	 */
	private Holder(Object value) {
		this.value = value;
		this.cause = null;
		this.clazz = value == null ? void.class : value.getClass();
		this.boolValue = false;
		this.bValue = 0;
		this.cValue = 0;
		this.dValue = 0;
		this.fValue = 0;
		this.iValue = 0;
		this.lValue = 0;
		this.sValue = 0;
	}

	private <F> Holder(F value, Class<?> clazz) {
		Objects.requireNonNull(clazz);
		this.cause = null;
		this.value = clazz.cast(value);
		this.clazz = clazz;
		this.boolValue = false;
		this.bValue = 0;
		this.cValue = 0;
		this.dValue = 0;
		this.fValue = 0;
		this.iValue = 0;
		this.lValue = 0;
		this.sValue = 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Any))
			return false;
		Holder o = (Holder) obj;
		// return this.contained.equals(other.contained);
		if (!this.isValid() || !o.isValid())
			return false;
		Object thisBoxed = this.boxed();
		Object otherBoxed = o.boxed();
		return thisBoxed == null ? otherBoxed == null : thisBoxed.equals(otherBoxed);
	}

	public Object boxed() {
		checkError();
		if (!isEngaged())
			return null;
		if (boolean.class.isAssignableFrom(clazz)) {
			return Boolean.valueOf(boolValue);
		} else if (byte.class.isAssignableFrom(clazz)) {
			return Byte.valueOf(bValue);
		} else if (char.class.isAssignableFrom(clazz)) {
			return Character.valueOf(cValue);
		} else if (short.class.isAssignableFrom(clazz)) {
			return Short.valueOf(sValue);
		} else if (int.class.isAssignableFrom(clazz)) {
			return Integer.valueOf(iValue);
		} else if (long.class.isAssignableFrom(clazz)) {
			return Long.valueOf(lValue);
		} else if (float.class.isAssignableFrom(clazz)) {
			return Float.valueOf(fValue);
		} else if (double.class.isAssignableFrom(clazz)) {
			return Double.valueOf(dValue);
		}
		return this.value;
	}

	/**
	 * Check if an error is stored, if yes, throw, if no, return
	 */
	private void checkError() {
		if (this.isValid())
			return;
		throw new IllegalStateException(this.cause.toString(), this.cause);
	}

	@Override
	public FailPolicy getDefaultPolicy() {
		return DefaultPolicies.STRICT;
	}

	@Override
	public boolean asBool(FailPolicy onFail) {
		checkError();
		if (Boolean.class.isAssignableFrom(clazz))
			return ((Boolean) this.value).booleanValue();
		if (boolean.class.isAssignableFrom(clazz))
			return this.boolValue;
		return onFail.failedBoolean(clazz);
	}

	@Override
	public byte asByte(FailPolicy onFail) {
		checkError();
		if (byte.class.isAssignableFrom(clazz))
			return this.bValue;
		if (Byte.class.isAssignableFrom(clazz))
			return ((Number) this.value).byteValue();
		return onFail.failedByte(clazz);
	}

	@Override
	public char asChar(FailPolicy onFail) {
		checkError();
		if (char.class.isAssignableFrom(clazz))
			return this.cValue;
		if (Character.class.isAssignableFrom(clazz))
			return ((Character) this.value).charValue();
		return onFail.failedChar(clazz);
	}

	@Override
	public short asShort(FailPolicy onFail) {
		checkError();
		if (short.class.isAssignableFrom(clazz))
			return this.sValue;
		if (byte.class.isAssignableFrom(clazz))
			return this.bValue;
		if (Number.class.isAssignableFrom(clazz))
			return ((Number) this.value).shortValue();
		return onFail.failedShort(clazz);
	}

	@Override
	public int asInt(FailPolicy onFail) {
		checkError();
		if (int.class.isAssignableFrom(clazz))
			return this.iValue;
		if (byte.class.isAssignableFrom(clazz))
			return this.bValue;
		if (char.class.isAssignableFrom(clazz))
			return this.cValue;
		if (short.class.isAssignableFrom(clazz))
			return this.sValue;
		if (Number.class.isAssignableFrom(clazz))
			return ((Number) this.value).intValue();
		return onFail.failedInt(clazz);
	}

	@Override
	public long asLong(FailPolicy onFail) {
		checkError();
		if (long.class.isAssignableFrom(clazz))
			return this.lValue;
		if (byte.class.isAssignableFrom(clazz))
			return this.bValue;
		if (char.class.isAssignableFrom(clazz))
			return this.cValue;
		if (short.class.isAssignableFrom(clazz))
			return this.sValue;
		if (int.class.isAssignableFrom(clazz))
			return this.iValue;
		if (Number.class.isAssignableFrom(clazz))
			return ((Number) this.value).longValue();
		return onFail.failedLong(clazz);
	}

	@Override
	public float asFloat(FailPolicy onFail) {
		checkError();
		if (float.class.isAssignableFrom(clazz))
			return this.fValue;
		if (byte.class.isAssignableFrom(clazz))
			return this.bValue;
		if (char.class.isAssignableFrom(clazz))
			return this.cValue;
		if (short.class.isAssignableFrom(clazz))
			return this.sValue;
		if (int.class.isAssignableFrom(clazz))
			return this.iValue;
		if (long.class.isAssignableFrom(clazz))
			return this.lValue;
		if (Number.class.isAssignableFrom(clazz))
			return ((Number) this.value).floatValue();
		return onFail.failedFloat(clazz);
	}

	@Override
	public double asDouble(FailPolicy onFail) {
		checkError();
		if (double.class.isAssignableFrom(clazz))
			return this.dValue;
		if (byte.class.isAssignableFrom(clazz))
			return this.bValue;
		if (char.class.isAssignableFrom(clazz))
			return this.cValue;
		if (short.class.isAssignableFrom(clazz))
			return this.sValue;
		if (int.class.isAssignableFrom(clazz))
			return this.iValue;
		if (long.class.isAssignableFrom(clazz))
			return this.lValue;
		if (float.class.isAssignableFrom(clazz))
			return this.fValue;
		if (Number.class.isAssignableFrom(clazz))
			return ((Number) this.value).doubleValue();
		return onFail.failedDouble(clazz);
	}

	/**
	 * Returns the object stored in this any if it could be assigned to an
	 * object of the given class in a simple expression
	 * <code>F object = getAs<F>(F.class)</code>. This includes auto-boxing. For
	 * example, when this class holds an int, it is possible to say
	 * getAs(Integer.class) because <code>Integer a = 5;</code> is a legal
	 * expression.
	 *
	 * @param fClazz
	 *            the class to cast to. It is legal to give primitive classes -
	 *            although that won't get you far because the value is boxed and
	 *            unboxed anyway because with type erasure this method returns
	 *            an Object
	 * @return
	 * @throws Throwable
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <F> F getAs(Class<F> fClazz, FailPolicy onFail) {
		// Unboxing: the given type is primitive
		checkError();
		if (fClazz.isAssignableFrom(boolean.class)) {
			return (F) Boolean.valueOf(asBool(onFail));
		}
		if (fClazz.isAssignableFrom(char.class)) {
			return (F) Character.valueOf(asChar(onFail));
		}
		if (fClazz.isAssignableFrom(byte.class)) {
			return (F) Byte.valueOf(asByte(onFail));
		}
		if (fClazz.isAssignableFrom(short.class)) {
			return (F) Short.valueOf(asShort(onFail));
		}
		if (fClazz.isAssignableFrom(int.class)) {
			return (F) Integer.valueOf(asInt(onFail));
		}
		if (fClazz.isAssignableFrom(long.class)) {
			return (F) Long.valueOf(asLong(onFail));
		}
		if (fClazz.isAssignableFrom(float.class)) {
			return (F) Float.valueOf(asFloat(onFail));
		}
		if (fClazz.isAssignableFrom(double.class)) {
			return (F) Double.valueOf(asDouble(onFail));
		}
		// Boxing: The stored type is primitive, the given is not
		if (clazz.isAssignableFrom(boolean.class)) {
			if (fClazz.isAssignableFrom(Boolean.class))
				return (F) Boolean.valueOf(this.boolValue);
		} else if (clazz.isAssignableFrom(char.class)) {
			if (fClazz.isAssignableFrom(Character.class))
				return (F) Character.valueOf(this.cValue);
			if (fClazz.isAssignableFrom(Integer.class))
				return (F) Integer.valueOf(this.cValue);
			if (fClazz.isAssignableFrom(Long.class))
				return (F) Long.valueOf(this.cValue);
			if (fClazz.isAssignableFrom(Float.class))
				return (F) Float.valueOf(this.cValue);
			if (fClazz.isAssignableFrom(Double.class))
				return (F) Double.valueOf(this.cValue);
		} else if (clazz.isAssignableFrom(byte.class)) {
			if (fClazz.isAssignableFrom(Byte.class))
				return (F) Byte.valueOf(this.bValue);
			if (fClazz.isAssignableFrom(Short.class))
				return (F) Short.valueOf(this.bValue);
			if (fClazz.isAssignableFrom(Integer.class))
				return (F) Integer.valueOf(this.bValue);
			if (fClazz.isAssignableFrom(Long.class))
				return (F) Long.valueOf(this.bValue);
			if (fClazz.isAssignableFrom(Float.class))
				return (F) Float.valueOf(this.bValue);
			if (fClazz.isAssignableFrom(Double.class))
				return (F) Double.valueOf(this.bValue);
		} else if (clazz.isAssignableFrom(short.class)) {
			if (fClazz.isAssignableFrom(Short.class))
				return (F) Short.valueOf(this.sValue);
			if (fClazz.isAssignableFrom(Integer.class))
				return (F) Integer.valueOf(this.sValue);
			if (fClazz.isAssignableFrom(Long.class))
				return (F) Long.valueOf(this.sValue);
			if (fClazz.isAssignableFrom(Float.class))
				return (F) Float.valueOf(this.sValue);
			if (fClazz.isAssignableFrom(Double.class))
				return (F) Double.valueOf(this.sValue);
		} else if (clazz.isAssignableFrom(int.class)) {
			if (fClazz.isAssignableFrom(Integer.class))
				return (F) Integer.valueOf(this.iValue);
			if (fClazz.isAssignableFrom(Long.class))
				return (F) Long.valueOf(this.iValue);
			if (fClazz.isAssignableFrom(Float.class))
				return (F) Float.valueOf(this.iValue);
			if (fClazz.isAssignableFrom(Double.class))
				return (F) Double.valueOf(this.iValue);
		} else if (clazz.isAssignableFrom(long.class)) {
			if (fClazz.isAssignableFrom(Long.class))
				return (F) Long.valueOf(this.lValue);
			if (fClazz.isAssignableFrom(Float.class))
				return (F) Float.valueOf(this.lValue);
			if (fClazz.isAssignableFrom(Double.class))
				return (F) Double.valueOf(this.lValue);
		} else if (clazz.isAssignableFrom(float.class)) {
			if (fClazz.isAssignableFrom(Float.class))
				return (F) Float.valueOf(this.fValue);
			if (fClazz.isAssignableFrom(Double.class))
				return (F) Double.valueOf(this.fValue);
		} else if (clazz.isAssignableFrom(double.class)) {
			if (fClazz.isAssignableFrom(Double.class))
				return (F) Double.valueOf(this.dValue);
		}
		if (fClazz.isAssignableFrom(clazz))
			return (F) this.value;
		return onFail.failedObject(clazz, fClazz);
	}

	/**
	 *
	 * @return
	 */
	public boolean isEngaged() {
		return !representsNothing(this.clazz);
	}

	@Override
	public Class<?> getType() {
		checkError();
		return this.clazz;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @return
	 */
	@Override
	public Holder snapshot() {
		return this;
	}

	@Override
	public boolean isSnapshot() {
		return true;
	}

	@Override
	public Holder snapshotClass() {
		return this;
	}

	@Override
	public boolean isClassSnapshot() {
		return true;
	}

	/**
	 * This Holder is said to be "valid" when it is either engaged or the cause
	 * is <code>null</code>.
	 * 
	 * @return
	 */
	public boolean isValid() {
		return this.isEngaged() || this.cause == null;
	}

	public Throwable getFailCause() {
		if (isValid())
			return null;
		return this.cause;
	}

	@Override
	public String toString() {
		if (!isValid())
			return "Stored Error: " + this.cause.toString();
		Object hold = boxed();
		return hold == null ? "Na/V" : hold.toString();
	}
}
