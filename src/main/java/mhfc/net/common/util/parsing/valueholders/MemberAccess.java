package mhfc.net.common.util.parsing.valueholders;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.IValueHolder;
import mhfc.net.common.util.reflection.FieldHelper;
import mhfc.net.common.util.reflection.MethodHelper;
import mhfc.net.common.util.reflection.OverloadedMethod;
import scala.actors.threadpool.Arrays;

/**
 * Represents a member of an {@link IValueHolder}. This is dynamically determined based on the currently present origin
 * in the origin.<br>
 * The name of the field on the other hand is <b>not</b> dynamic.
 *
 * @author WorldSEnder
 */
public class MemberAccess implements IValueHolder {
	private static interface IFieldAccess {
		boolean isTypeFinal();

		Class<?> getType();

		Holder get(Object instance);
	}

	private static class FieldArrayLength implements IFieldAccess {

		@Override
		public Class<?> getType() {
			return int.class;
		}

		@Override
		public Holder get(Object instance) {
			Objects.requireNonNull(instance);
			return Holder.valueOf(Array.getLength(instance));
		}

		@Override
		public boolean isTypeFinal() {
			return true;
		}

	}

	private static class FieldArrayClone<T> implements IFieldAccess {
		private final Class<? extends T[]> arrayClazz;

		public FieldArrayClone(Class<? extends T[]> arrayClazz) {
			assert arrayClazz.isArray();
			this.arrayClazz = arrayClazz;
		}

		@Override
		public Class<?> getType() {
			return arrayClazz;
		}

		@Override
		public Holder get(Object instance) {
			T[] arr = arrayClazz.cast(instance);
			return Holder.valueOf(Arrays.copyOf(arr, arr.length));
		}

		@SuppressWarnings("unchecked")
		public static IFieldAccess forClass(Class<?> arrClass) {
			assert arrClass.isArray();
			@SuppressWarnings("rawtypes")
			FieldArrayClone c = new FieldArrayClone(arrClass);
			return c;
		}

		@Override
		public boolean isTypeFinal() {
			return true;
		}
	}

	private static class FieldProxy<T> implements IFieldAccess {

		private final Field field;
		private final Class<?> fieldType;

		private final Function<Object, Holder> rawToHolder;

		public FieldProxy(Field f) {
			this.field = Objects.requireNonNull(f);
			this.fieldType = f.getType();
			this.rawToHolder = Holder.makeUnboxer(fieldType);
		}

		@Override
		public Class<?> getType() {
			return this.fieldType;
		}

		@Override
		public Holder get(Object instance) {
			return Holder.catching(IllegalAccessException.class, () -> {
				Object fieldValue = this.field.get(instance);
				return rawToHolder.apply(fieldValue);
			});
		}

		@Override
		public boolean isTypeFinal() {
			return true;
		}
	}

	private static class FieldNotFound implements IFieldAccess {
		private final Class<?> clazz;
		private final String member;

		public FieldNotFound(Class<?> clazz, String member) {
			this.clazz = clazz;
			this.member = member;
		}

		@Override
		public Holder get(Object instance) {
			return Holder.failedComputation(new NoSuchFieldException(clazz.getName() + "." + member + " not found"));
		}

		@Override
		public Class<?> getType() {
			return IValueHolder.EMPTY_CLASS;
		}

		@Override
		public boolean isTypeFinal() {
			return true;
		}
	}

	private static class MethodProxy implements IFieldAccess {
		private final OverloadedMethod method;

		public MethodProxy(OverloadedMethod methods) {
			this.method = methods;
		}

		@Override
		public Holder get(Object instance) {
			return Holder.valueOf(new MemberMethodProxy(method, instance));
		}

		@Override
		public Class<?> getType() {
			return MemberMethodProxy.class;
		}

		@Override
		public boolean isTypeFinal() {
			return true;
		}
	}

	private static class SpecialAccessProxy implements IFieldAccess {
		private final Method getattr;
		private final String name;
		private final Supplier<RuntimeException> error;

		public SpecialAccessProxy(Method getter, String memberName) {
			if (!getter.getReturnType().equals(Holder.class)) {
				error = () -> new IllegalArgumentException("__getattr__ must return Holder");
			} else {
				error = null;
			}
			this.getattr = getter;
			this.name = memberName;
		}

		@Override
		public Class<?> getType() {
			return IValueHolder.EMPTY_CLASS;
		}

		@Override
		public Holder get(Object instance) {
			if (error != null) {
				return Holder.failedComputation(error.get());
			}
			try {
				return Holder.class.cast(getattr.invoke(instance, name));
			} catch (InvocationTargetException | IllegalAccessException ite) {
				throw new RuntimeException(ite);
			}
		}

		@Override
		public boolean isTypeFinal() {
			return error != null;
		}
	}

	private static final IFieldAccess arrayLengthProxy = new FieldArrayLength();

	private static Table<Class<?>, String, IFieldAccess> fieldCache;
	private static LoadingCache<Class<?>, Boolean> proxyCache;

	static {
		fieldCache = HashBasedTable.create();
		proxyCache = CacheBuilder.newBuilder().maximumSize(1000)
				.removalListener(n -> fieldCache.rowKeySet().remove(n.getKey()))
				.build(new CacheLoader<Class<?>, Boolean>() {
					@Override
					public Boolean load(Class<?> key) {
						return Boolean.TRUE;
					}
				});
	}

	private static IFieldAccess resolveField(Class<?> clazz, String member) {
		if (clazz.isPrimitive()) {
			return new FieldNotFound(clazz, member);
		}
		proxyCache.getUnchecked(clazz); // Touch the cache for this class
		IFieldAccess field = fieldCache.get(clazz, member);
		if (field == null) {
			field = computeFieldAccess(clazz, member);
			fieldCache.put(clazz, member, field);
		}
		return field;
	}

	private static IFieldAccess computeFieldAccess(Class<?> clazz, String member) {
		if (clazz.isArray()) {
			if (member.equals("length")) {
				return arrayLengthProxy;
			}
			if (member.equals("clone")) {
				return FieldArrayClone.forClass(clazz);
			}
		}
		Optional<Field> f = FieldHelper.findMatching(clazz, member);
		if (f.isPresent()) {
			return new FieldProxy<>(f.get());
		}
		Optional<OverloadedMethod> m = MethodHelper.findMatching(clazz, member);
		if (m.isPresent()) {
			return new MethodProxy(m.get());
		}
		Optional<OverloadedMethod> getattr = MethodHelper.findMatching(clazz, "__getattr__");
		Optional<Method> specialgetter = getattr.flatMap(o -> o.disambiguate(String.class));
		if (specialgetter.isPresent()) {
			return new SpecialAccessProxy(specialgetter.get(), member);
		}
		return new FieldNotFound(clazz, member);
	}

	private static Holder accessField(IFieldAccess field, Holder instance) {
		return instance.ifValid(inst -> {
			return field.get(inst.boxed());
		});
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
		private final IFieldAccess field;

		/**
		 *
		 * @param object
		 * @param memberName
		 */
		public BoundMemberAccess(IValueHolder object, String memberName) {
			if (!object.isTypeFinal()) {
				throw new IllegalArgumentException("object's class must be final");
			}
			this.origin = object;
			this.originC = this.origin.getType();
			this.field = resolveField(originC, memberName);
		}

		@Override
		public Holder snapshot() {
			return accessField(field, origin.snapshot());
		}

		@Override
		public boolean isTypeFinal() {
			return field.isTypeFinal();
		}

		@Override
		public Class<?> getType() {
			return field.getType();
		}

	}

	public static IValueHolder makeMemberAccess(IValueHolder holder, String memberName) {
		if (holder.isTypeFinal()) {
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
		Holder holder = this.origin.snapshot();
		return accessField(resolveField(holder.getType(), this.name), holder);
	}

	@Override
	public Class<?> getType() {
		Class<?> instanceC = this.origin.getType();
		IFieldAccess field = resolveField(instanceC, this.name);
		return field.getType();
	}
}
