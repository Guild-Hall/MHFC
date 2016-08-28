package mhfc.net.common.util.parsing;

import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

import mhfc.net.MHFCMain;
import mhfc.net.common.util.ExceptionLessFunctions;
import mhfc.net.common.util.ExceptionLessFunctions.ThrowingSupplier;
import mhfc.net.common.util.function.ByteSupplier;
import mhfc.net.common.util.function.CharSupplier;
import mhfc.net.common.util.function.FloatSupplier;
import mhfc.net.common.util.function.ShortSupplier;
import mhfc.net.common.util.parsing.valueholders.Any;

/**
 * A Holder is the <b>immutable</b> version of an Any and the general basecase.
 *
 * @author WorldSEnder
 */
public final class Holder implements IValueHolder {
	private static class FailedComputationTag {}

	private static class ClassTag {}

	private static FailedComputationTag failedTag = new FailedComputationTag();
	private static ClassTag classTag = new ClassTag();

	private static interface Wrapper {
		/**
		 *
		 * @return <code>null</code> if not convertible, else a supplier
		 */
		BooleanSupplier asBool();

		CharSupplier asChar();

		ByteSupplier asByte();

		ShortSupplier asShort();

		IntSupplier asInt();

		LongSupplier asLong();

		FloatSupplier asFloat();

		DoubleSupplier asDouble();

		<T> Supplier<T> asObject(Class<T> clazz);

		Class<?> getWrappedClass();

		Throwable checkError();
	}

	private static abstract class BasicWrapper implements Wrapper {

		@SuppressWarnings("unchecked")
		public <T> Supplier<T> asBoxedPrimitive(Class<T> clazz) {
			if (!clazz.isPrimitive()) {
				throw new IllegalArgumentException("clazz must be primitive");
			}
			if (boolean.class.equals(clazz)) {
				BooleanSupplier supp = asBool();
				return supp == null ? null : () -> (T) Boolean.valueOf(supp.getAsBoolean());
			}
			if (char.class.equals(clazz)) {
				CharSupplier supp = asChar();
				return supp == null ? null : () -> (T) Character.valueOf(supp.getAsChar());
			}
			if (byte.class.equals(clazz)) {
				ByteSupplier supp = asByte();
				return supp == null ? null : () -> (T) Byte.valueOf(supp.getAsByte());
			}
			if (short.class.equals(clazz)) {
				ShortSupplier supp = asShort();
				return supp == null ? null : () -> (T) Short.valueOf(supp.getAsShort());
			}
			if (int.class.equals(clazz)) {
				IntSupplier supp = asInt();
				return supp == null ? null : () -> (T) Integer.valueOf(supp.getAsInt());
			}
			if (long.class.equals(clazz)) {
				LongSupplier supp = asLong();
				return supp == null ? null : () -> (T) Long.valueOf(supp.getAsLong());
			}
			if (float.class.equals(clazz)) {
				FloatSupplier supp = asFloat();
				return supp == null ? null : () -> (T) Float.valueOf(supp.getAsFloat());
			}
			if (double.class.equals(clazz)) {
				DoubleSupplier supp = asDouble();
				return supp == null ? null : () -> (T) Double.valueOf(supp.getAsDouble());
			}
			throw new IllegalArgumentException("Unreachable. Did java add another primitive type?");
		}
	}

	private static abstract class PrimitiveWrapper<B> extends BasicWrapper {
		private Class<B> boxedClazz;

		public PrimitiveWrapper() {
			boxedClazz = Objects.requireNonNull(getBoxedClass());
		}

		protected abstract B boxed();

		protected abstract Class<B> getBoxedClass();

		@Override
		public BooleanSupplier asBool() {
			return null;
		}

		@Override
		public CharSupplier asChar() {
			return null;
		}

		@Override
		public ByteSupplier asByte() {
			return null;
		}

		@Override
		public ShortSupplier asShort() {
			return null;
		}

		@Override
		public IntSupplier asInt() {
			return null;
		}

		@Override
		public LongSupplier asLong() {
			return null;
		}

		@Override
		public FloatSupplier asFloat() {
			return null;
		}

		@Override
		public DoubleSupplier asDouble() {
			return null;
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> Supplier<T> asObject(Class<T> clazz) {
			if (clazz.isPrimitive()) {
				return asBoxedPrimitive(clazz);
			}
			// 5.2
			// A boxing conversion, optionally followed by a widening reference conversion
			if (clazz.isAssignableFrom(boxedClazz)) {
				return () -> (T) boxed();
			}
			// TODO: maybe extend the cases of 5.2 to
			// A widening primitive conversion, then a boxing conversion (no widening reference conversion)
			return null;
		}

		@Override
		public Throwable checkError() {
			return null;
		}
	}

	private static class BooleanWrapper extends PrimitiveWrapper<Boolean> {
		private final boolean bool;

		public BooleanWrapper(boolean bool) {
			this.bool = bool;
		}

		@Override
		public BooleanSupplier asBool() {
			return () -> bool;
		}

		@Override
		protected Boolean boxed() {
			return Boolean.valueOf(bool);
		}

		@Override
		protected Class<Boolean> getBoxedClass() {
			return Boolean.class;
		}

		@Override
		public Class<?> getWrappedClass() {
			return boolean.class;
		}
	}

	private static class CharWrapper extends PrimitiveWrapper<Character> {
		private final char ch;

		public CharWrapper(char ch) {
			this.ch = ch;
		}

		@Override
		protected Character boxed() {
			return Character.valueOf(ch);
		}

		@Override
		protected Class<Character> getBoxedClass() {
			return Character.class;
		}

		@Override
		public Class<?> getWrappedClass() {
			return char.class;
		}
	}

	private static class ByteWrapper extends PrimitiveWrapper<Byte> {
		private final byte b;

		public ByteWrapper(byte b) {
			this.b = b;
		}

		@Override
		public ByteSupplier asByte() {
			return () -> this.b;
		}

		@Override
		public ShortSupplier asShort() {
			return () -> this.b;
		}

		@Override
		public IntSupplier asInt() {
			return () -> this.b;
		}

		@Override
		public LongSupplier asLong() {
			return () -> this.b;
		}

		@Override
		public FloatSupplier asFloat() {
			return () -> this.b;
		}

		@Override
		public DoubleSupplier asDouble() {
			return () -> this.b;
		}

		@Override
		protected Byte boxed() {
			return Byte.valueOf(b);
		}

		@Override
		protected Class<Byte> getBoxedClass() {
			return Byte.class;
		}

		@Override
		public Class<?> getWrappedClass() {
			return byte.class;
		}
	}

	private static class ShortWrapper extends PrimitiveWrapper<Short> {
		private final short sh;

		public ShortWrapper(short sh) {
			this.sh = sh;
		}

		@Override
		public ShortSupplier asShort() {
			return () -> this.sh;
		}

		@Override
		public IntSupplier asInt() {
			return () -> this.sh;
		}

		@Override
		public LongSupplier asLong() {
			return () -> this.sh;
		}

		@Override
		public FloatSupplier asFloat() {
			return () -> this.sh;
		}

		@Override
		public DoubleSupplier asDouble() {
			return () -> this.sh;
		}

		@Override
		protected Short boxed() {
			return Short.valueOf(sh);
		}

		@Override
		protected Class<Short> getBoxedClass() {
			return Short.class;
		}

		@Override
		public Class<?> getWrappedClass() {
			return short.class;
		}
	}

	private static class IntWrapper extends PrimitiveWrapper<Integer> {
		private final int i;

		public IntWrapper(int i) {
			this.i = i;
		}

		@Override
		public IntSupplier asInt() {
			return () -> this.i;
		}

		@Override
		public LongSupplier asLong() {
			return () -> this.i;
		}

		@Override
		public FloatSupplier asFloat() {
			return () -> this.i;
		}

		@Override
		public DoubleSupplier asDouble() {
			return () -> this.i;
		}

		@Override
		protected Integer boxed() {
			return Integer.valueOf(i);
		}

		@Override
		protected Class<Integer> getBoxedClass() {
			return Integer.class;
		}

		@Override
		public Class<?> getWrappedClass() {
			return int.class;
		}
	}

	private static class LongWrapper extends PrimitiveWrapper<Long> {
		private final long l;

		public LongWrapper(long l) {
			this.l = l;
		}

		@Override
		public LongSupplier asLong() {
			return () -> this.l;
		}

		@Override
		public FloatSupplier asFloat() {
			return () -> this.l;
		}

		@Override
		public DoubleSupplier asDouble() {
			return () -> this.l;
		}

		@Override
		protected Long boxed() {
			return Long.valueOf(l);
		}

		@Override
		protected Class<Long> getBoxedClass() {
			return Long.class;
		}

		@Override
		public Class<?> getWrappedClass() {
			return void.class;
		}
	}

	private static class FloatWrapper extends PrimitiveWrapper<Float> {
		private float f;

		public FloatWrapper(float f) {
			this.f = f;
		}

		@Override
		public FloatSupplier asFloat() {
			return () -> this.f;
		}

		@Override
		public DoubleSupplier asDouble() {
			return () -> this.f;
		}

		@Override
		protected Float boxed() {
			return Float.valueOf(f);
		}

		@Override
		protected Class<Float> getBoxedClass() {
			return Float.class;
		}

		@Override
		public Class<?> getWrappedClass() {
			return float.class;
		}
	}

	private static class DoubleWrapper extends PrimitiveWrapper<Double> {
		private final double d;

		public DoubleWrapper(double d) {
			this.d = d;
		}

		@Override
		public DoubleSupplier asDouble() {
			return () -> this.d;
		}

		@Override
		protected Double boxed() {
			return Double.valueOf(d);
		}

		@Override
		protected Class<Double> getBoxedClass() {
			return Double.class;
		}

		@Override
		public Class<?> getWrappedClass() {
			return double.class;
		}
	}

	private static class VoidWrapper extends PrimitiveWrapper<Void> {
		@Override
		public <T> Supplier<T> asObject(Class<T> clazz) {
			return null;
		}

		@Override
		protected Void boxed() {
			return null;
		}

		@Override
		protected Class<Void> getBoxedClass() {
			return Void.class;
		}

		@Override
		public Class<?> getWrappedClass() {
			return EMPTY_CLASS;
		}
	}

	private static final Wrapper VOID_WRAPPER = new VoidWrapper();

	private static class ThrowableWrapper extends VoidWrapper {
		private final Throwable caught;

		public ThrowableWrapper(Throwable thr) {
			this.caught = thr;
		}

		@Override
		public Throwable checkError() {
			return this.caught;
		}
	}

	private static class ObjectWrapper extends BasicWrapper {
		private final Object obj;
		private final Class<?> clazz;
		private BooleanSupplier boolS;
		private CharSupplier charS;
		private ByteSupplier byteS;
		private ShortSupplier shortS;
		private IntSupplier intS;
		private LongSupplier longS;
		private FloatSupplier floatS;
		private DoubleSupplier doubleS;

		public ObjectWrapper(Object nonNull) {
			this(nonNull, nonNull.getClass());
		}

		public ObjectWrapper(Class<?> of, ClassTag overload) {
			this(null, of);
			if (of.isPrimitive()) {
				throw new IllegalArgumentException("of can't be primitive for null-values");
			}
		}

		private ObjectWrapper(Object obj, Class<?> clazz) {
			this.obj = clazz.cast(obj);
			this.clazz = Objects.requireNonNull(clazz);
			updateSuppliers();
		}

		@SuppressWarnings("unchecked")
		private <T> T retrieveUnsafe() {
			return (T) obj;
		}

		private void updateSuppliers() {
			// 5.2 Allow unboxing, optionally followed by widening primitive conversion
			if (Boolean.class.isAssignableFrom(clazz)) {
				this.boolS = () -> this.<Boolean>retrieveUnsafe().booleanValue();
			} else if (Character.class.isAssignableFrom(clazz)) {
				this.charS = () -> this.<Character>retrieveUnsafe().charValue();
			} else if (Byte.class.isAssignableFrom(clazz)) {
				this.byteS = () -> this.<Byte>retrieveUnsafe().byteValue();
				this.shortS = () -> this.<Byte>retrieveUnsafe().shortValue();
				this.intS = () -> this.<Byte>retrieveUnsafe().intValue();
				this.longS = () -> this.<Byte>retrieveUnsafe().longValue();
				this.floatS = () -> this.<Byte>retrieveUnsafe().floatValue();
				this.doubleS = () -> this.<Byte>retrieveUnsafe().doubleValue();
			} else if (Short.class.isAssignableFrom(clazz)) {
				this.shortS = () -> this.<Short>retrieveUnsafe().shortValue();
				this.intS = () -> this.<Short>retrieveUnsafe().intValue();
				this.longS = () -> this.<Short>retrieveUnsafe().longValue();
				this.floatS = () -> this.<Short>retrieveUnsafe().floatValue();
				this.doubleS = () -> this.<Short>retrieveUnsafe().doubleValue();
			} else if (Integer.class.isAssignableFrom(clazz)) {
				this.intS = () -> this.<Integer>retrieveUnsafe().intValue();
				this.longS = () -> this.<Integer>retrieveUnsafe().longValue();
				this.floatS = () -> this.<Integer>retrieveUnsafe().floatValue();
				this.doubleS = () -> this.<Integer>retrieveUnsafe().doubleValue();
			} else if (Long.class.isAssignableFrom(clazz)) {
				this.longS = () -> this.<Long>retrieveUnsafe().longValue();
				this.floatS = () -> this.<Long>retrieveUnsafe().floatValue();
				this.doubleS = () -> this.<Long>retrieveUnsafe().longValue();
			} else if (Float.class.isAssignableFrom(clazz)) {
				this.floatS = () -> this.<Float>retrieveUnsafe().floatValue();
				this.doubleS = () -> this.<Float>retrieveUnsafe().doubleValue();
			} else if (Double.class.isAssignableFrom(clazz)) {
				this.doubleS = () -> this.<Double>retrieveUnsafe().doubleValue();
			}
		}

		@Override
		public BooleanSupplier asBool() {
			return boolS;
		}

		@Override
		public CharSupplier asChar() {
			return charS;
		}

		@Override
		public ByteSupplier asByte() {
			return byteS;
		}

		@Override
		public ShortSupplier asShort() {
			return shortS;
		}

		@Override
		public IntSupplier asInt() {
			return intS;
		}

		@Override
		public LongSupplier asLong() {
			return longS;
		}

		@Override
		public FloatSupplier asFloat() {
			return floatS;
		}

		@Override
		public DoubleSupplier asDouble() {
			return doubleS;
		}

		@Override
		public <T> Supplier<T> asObject(Class<T> clazz) {
			if (clazz.isPrimitive()) {
				return asBoxedPrimitive(clazz);
			}
			if (clazz.isAssignableFrom(this.clazz)) {
				return () -> this.retrieveUnsafe();
			}
			return null;
		}

		@Override
		public Throwable checkError() {
			return null;
		}

		@Override
		public Class<?> getWrappedClass() {
			return clazz;
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

	public static Holder valueOf(Object nonNull) {
		return new Holder(nonNull);
	}

	/**
	 * Constructs a Holder of the object if the object is not null, else an empty Holder.
	 *
	 * @param o
	 * @return
	 */
	public static Holder valueOrEmpty(Object o) {
		return o == null ? empty() : new Holder(o);
	}

	/**
	 * If clazz represents a primitive class, unboxes o, otherwise wraps o into a Holder. Note that o shall always
	 * represent the boxed form of clazz, but may be null if clazz is not primitve.<br>
	 * If unboxing fails, returns a Holder with the conversion exception.<br>
	 * This is primarily aimed at the unboxing of value retrieved by reflection, in which case you can write
	 * #makeUnboxer(fieldType).apply(fieldValue) to retrieve the correct Holder wrapping the field.
	 *
	 * @param clazz
	 * @return
	 */
	public static <T> Function<Object, Holder> makeUnboxer(Class<T> clazz) {
		if (void.class.isAssignableFrom(clazz)) {
			return t -> Holder.empty();
		}
		if (boolean.class.isAssignableFrom(clazz)) {
			return t -> Holder.valueOf(Boolean.class.cast(t).booleanValue());
		}
		if (char.class.isAssignableFrom(clazz)) {
			return t -> Holder.valueOf(Character.class.cast(t).charValue());
		}
		if (byte.class.isAssignableFrom(clazz)) {
			return t -> Holder.valueOf(Byte.class.cast(t).byteValue());
		}
		if (short.class.isAssignableFrom(clazz)) {
			return t -> Holder.valueOf(Short.class.cast(t).shortValue());
		}
		if (int.class.isAssignableFrom(clazz)) {
			return t -> Holder.valueOf(Integer.class.cast(t).intValue());
		}
		if (long.class.isAssignableFrom(clazz)) {
			return t -> Holder.valueOf(Long.class.cast(t).longValue());
		}
		if (float.class.isAssignableFrom(clazz)) {
			return t -> Holder.valueOf(Float.class.cast(t).floatValue());
		}
		if (double.class.isAssignableFrom(clazz)) {
			return t -> Holder.valueOf(Double.class.cast(t).doubleValue());
		}
		return t -> Holder.valueOf(clazz.cast(t), clazz);
	}

	public static Holder typedNull(Class<?> clazz) {
		return new Holder(clazz, classTag);
	}

	/**
	 * Constructs a Holder of the value, or if null, one for the given class.
	 *
	 * @param object
	 *            the object to hold, possibly null
	 * @param clazz
	 *            the exact class to hold if object is null
	 * @return an engaged Holder that holds an object of the given Class.
	 */
	public static <F> Holder valueOf(F object, Class<? super F> clazz) {
		return object == null ? new Holder(clazz, classTag) : new Holder(object);
	}

	/**
	 * Executes the supplier, if it throws an exception of type excClazz, it is caught and returned in a Holder, else
	 * the computed Holder is returned.<br>
	 * Usage:
	 *
	 * <pre>
	 * <code>
	 * return Holder.catching(NullPointerException.class, () -> {
	 *     return possiblyNull.compute();
	 * });
	 * </code>
	 * </pre>
	 *
	 * Keep in mind that all exceptions <b>but E</b> are still thrown.
	 *
	 * @param excClazz
	 * @param supplier
	 * @return
	 */
	public static <E extends Throwable> Holder catching(
			Class<? extends E> excClazz,
			ThrowingSupplier<Holder, E> supplier) {
		try {
			return supplier.get();
		} catch (Throwable thr) {
			MHFCMain.logger().catching(thr);
			Objects.requireNonNull(excClazz);
			if (excClazz.isInstance(thr)) {
				return Holder.catching(thr);
			}
			return ExceptionLessFunctions.throwUnchecked(thr);
		}
	}

	/**
	 * The same as {@link #catching(Class, ThrowingSupplier)} but for supplier that only throw
	 * {@link RuntimeException}s. Most of the time, instead of using this directly, try
	 * {@link #snapshotSafely(IValueHolder)}.
	 *
	 * @param supplier
	 * @return
	 */
	public static <E extends Throwable> Holder catching(ThrowingSupplier<Holder, E> supplier) {
		return Holder.catching(RuntimeException.class, supplier::get);
	}

	public static Holder catching(Throwable cause) {
		return new Holder(cause, failedTag);
	}

	public static Holder snapshotSafely(IValueHolder holder) {
		return Holder.catching(Throwable.class, holder::snapshot);
	}

	private final Wrapper wrap;

	/**
	 * Any empty Any
	 */
	private Holder() {
		this.wrap = VOID_WRAPPER;
	}

	private Holder(boolean bool) {
		this(new BooleanWrapper(bool));
	}

	private Holder(byte b) {
		this(new ByteWrapper(b));
	}

	private Holder(char c) {
		this(new CharWrapper(c));
	}

	private Holder(short s) {
		this(new ShortWrapper(s));
	}

	private Holder(int i) {
		this(new IntWrapper(i));
	}

	private Holder(long l) {
		this(new LongWrapper(l));
	}

	private Holder(float f) {
		this(new FloatWrapper(f));
	}

	private Holder(double d) {
		this(new DoubleWrapper(d));
	}

	/**
	 * An empty Any that is empty for a reason
	 *
	 * @param tag
	 *            ignored tag for overload
	 * @param cause
	 *            the cause why this {@link Holder} is empty
	 */
	private Holder(Throwable cause, FailedComputationTag tag) {
		this(new ThrowableWrapper(cause));
	}

	/**
	 * Initializes the Any with an Object. If the value is <code>null</code>, this does the same as {@link #Holder()}.
	 * If you want to enforce a type with the value, use {@link #Holder(Object, Class)}.
	 *
	 * @param value
	 *            the value to assign.
	 * @see #valueOf(Object, Class)
	 */
	private Holder(Object value) {
		this.wrap = value == null ? VOID_WRAPPER : new ObjectWrapper(value);
	}

	private <F> Holder(Class<?> clazz, ClassTag tag) {
		this.wrap = new ObjectWrapper(clazz, tag);
	}

	private Holder(Wrapper wrap) {
		this.wrap = wrap;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Any)) {
			return false;
		}
		Holder o = (Holder) obj;
		// return this.contained.equals(other.contained);
		if (!this.isValid() || !o.isValid()) {
			return false;
		}
		Object thisBoxed = this.boxed();
		Object otherBoxed = o.boxed();
		return thisBoxed == null ? otherBoxed == null : thisBoxed.equals(otherBoxed);
	}

	public Object boxed() {
		return getAs(Object.class);
	}

	public Object boxedOrNull() {
		return getAs(Object.class, null);
	}

	/**
	 * Check if an error is stored, if yes, throw, if no, return
	 */
	private void throwIfError() {
		Throwable th = this.wrap.checkError();
		if (th == null) {
			return;
		}
		throw new IllegalStateException(th.toString(), th);
	}

	private ClassCastException failedConversion(Class<?> to) {
		return new ClassCastException("Can't convert from " + wrap.getWrappedClass() + " to " + to);
	}

	public boolean asBool() {
		throwIfError();
		BooleanSupplier supply = this.wrap.asBool();
		if (supply == null) {
			throw failedConversion(boolean.class);
		}
		return supply.getAsBoolean();
	}

	public byte asByte() {
		throwIfError();
		ByteSupplier supply = this.wrap.asByte();
		if (supply == null) {
			throw failedConversion(byte.class);
		}
		return supply.getAsByte();
	}

	public char asChar() {
		throwIfError();
		CharSupplier supply = this.wrap.asChar();
		if (supply == null) {
			throw failedConversion(char.class);
		}
		return supply.getAsChar();
	}

	public short asShort() {
		throwIfError();
		ShortSupplier supply = this.wrap.asShort();
		if (supply == null) {
			throw failedConversion(short.class);
		}
		return supply.getAsShort();
	}

	public int asInt() {
		throwIfError();
		IntSupplier supply = this.wrap.asInt();
		if (supply == null) {
			throw failedConversion(int.class);
		}
		return supply.getAsInt();
	}

	public long asLong() {
		throwIfError();
		LongSupplier supply = this.wrap.asLong();
		if (supply == null) {
			throw failedConversion(long.class);
		}
		return supply.getAsLong();
	}

	public float asFloat() {
		throwIfError();
		FloatSupplier supply = this.wrap.asFloat();
		if (supply == null) {
			throw failedConversion(float.class);
		}
		return supply.getAsFloat();
	}

	public double asDouble() {
		throwIfError();
		DoubleSupplier supply = this.wrap.asDouble();
		if (supply == null) {
			throw failedConversion(double.class);
		}
		return supply.getAsDouble();
	}

	/**
	 * Returns the object stored in this any if it could be assigned to an object of the given class in a simple
	 * expression <code>F object = getAs<F>(F.class)</code>. This includes auto-boxing. For example, when this class
	 * holds an int, it is possible to say getAs(Integer.class) because <code>Integer a = 5;</code> is a legal
	 * expression.
	 *
	 * @param fClazz
	 *            the class to cast to. It is legal to give primitive classes - although that won't get you far because
	 *            the value is boxed and unboxed anyway because with type erasure this method returns an Object
	 * @return
	 * @throws Throwable
	 */
	public <F> F getAs(Class<F> fClazz) {
		throwIfError();
		Supplier<F> supply = this.wrap.asObject(fClazz);
		if (supply == null) {
			throw failedConversion(fClazz);
		}
		return supply.get();
	}

	public <F> F getAs(Class<F> fClazz, F otherwise) {
		throwIfError();
		Supplier<F> supply = this.wrap.asObject(fClazz);
		if (supply == null) {
			return otherwise;
		}
		return supply.get();
	}

	/**
	 * This Holder is said to be "valid" when it doesn't hold an error.
	 *
	 * @return
	 */
	public boolean isValid() {
		return this.wrap.checkError() == null;
	}

	/**
	 * This Holder is said to be "engaged" when it isn't empty and doesn't hold an error.
	 *
	 * @return
	 */
	public boolean isEngaged() {
		return !EMPTY_CLASS.isAssignableFrom(wrap.getClass());
	}

	/**
	 * Returns true if this holder's value can be converted to the class clazz. <b>This does not mean that no exceptions
	 * may be thrown during conversion</b>. For example, if this holder holds null of type {@link Integer}, this would
	 * return <code>true</code> but during execution an {@link NullPointerException} will be thrown.
	 *
	 * @param clazz
	 * @return
	 */
	public boolean holdsClass(Class<?> clazz) {
		return this.wrap.asObject(clazz) != null;
	}

	public Class<?> getType() {
		throwIfError();
		return this.wrap.getWrappedClass();
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

	public Throwable getFailCause() {
		if (!isValid()) {
			return this.wrap.checkError();
		}
		return null;
	}

	/**
	 * "{error: &lt;errorstring&gt;}" if this holds an error.<br>
	 * "void" if this is empty.<br>
	 * otherwise the (boxed) held object.toString()<br>
	 */
	@Override
	public String toString() {
		if (!isValid()) {
			return "{error: " + this.wrap.checkError().toString() + "}";
		}
		Object hold = boxedOrNull();
		return hold == null ? "void" : hold.toString();
	}

	/**
	 * If this holds an exception, return this, otherwise call the function with this and return the result.
	 *
	 * @param supplier
	 * @return
	 */
	public Holder ifValid(Function<Holder, Holder> mapping) {
		if (!this.isValid()) {
			return this;
		}
		return mapping.apply(this);
	}

	/**
	 * Flat maps the function application. If the function returns null, an empty Holder is returned instead.
	 *
	 * @param classT
	 * @param func
	 * @return
	 */
	public <T> Holder ifValidMap(Class<T> classT, Function<T, Holder> func) {
		if (!this.isValid()) {
			return this;
		}
		Supplier<T> supp = this.wrap.asObject(classT);
		if (supp == null) {
			throw failedConversion(classT);
		}
		Holder ret = func.apply(supp.get());
		return ret == null ? Holder.empty() : ret;
	}

	/**
	 * Utility method to supply a back-up if normal calculations should fail.<br>
	 * If {@link #isValid()}, return this, otherwise return {@link Supplier#get()} of the supplier given.
	 *
	 * @param supplier
	 * @return
	 * @see {@link #ifNotEngaged(Supplier)}: uses {@link #isEngaged()} to determine validity of itself
	 */
	public Holder ifInvalid(Supplier<Holder> supplier) {
		if (this.isValid()) {
			return this;
		}
		return supplier.get();
	}

	/**
	 * Utility method to supply a back-up if normal calculations returns no result.<br>
	 * If {@link #isEngaged()}, return this, otherwise return {@link Supplier#get()} of the supplier given.
	 *
	 * @param supplier
	 * @return
	 * @see {@link #ifInvalid(Supplier)}: uses {@link #isValid()} to determine validity of itself
	 */
	public Holder ifNotEngaged(Supplier<Holder> supplier) {
		if (this.isEngaged()) {
			return this;
		}
		return supplier.get();
	}

	/**
	 * If this Holder represents an exception, return one that holds that exception as value, else return an empty
	 * Holder.
	 *
	 * @return
	 */
	public Holder exceptionAsValue() {
		return Holder.valueOrEmpty(this.wrap.checkError());
	}
}
