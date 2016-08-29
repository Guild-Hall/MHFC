package mhfc.net.common.util.parsing.valueholders;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Array;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import mhfc.net.common.util.ExceptionLessFunctions;
import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.IValueHolder;
import mhfc.net.common.util.parsing.exceptions.FieldNotFoundException;
import mhfc.net.common.util.parsing.proxies.MemberMethodProxy;
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
		Holder get(Object instance) throws Throwable;
	}

	private static class FieldArrayLength implements IFieldAccess {
		@Override
		public Holder get(Object instance) {
			Objects.requireNonNull(instance);
			return Holder.valueOf(Array.getLength(instance));
		}
	}

	private static class FieldArrayClone<T> implements IFieldAccess {
		private final Class<? extends T[]> arrayClazz;

		public FieldArrayClone(Class<? extends T[]> arrayClazz) {
			assert arrayClazz.isArray();
			this.arrayClazz = arrayClazz;
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
	}

	private static class FieldProxy<T> implements IFieldAccess {
		private final MethodHandle field;
		private final Class<?> fieldType;

		private final Function<Object, Holder> rawToHolder;

		public FieldProxy(MethodHandle f) {
			this.field = Objects.requireNonNull(f);
			this.fieldType = f.type().returnType();
			this.rawToHolder = Holder.makeUnboxer(fieldType);
		}

		@Override
		public Holder get(Object instance) {
			return Holder.catching(Throwable.class, () -> {
				Object fieldValue = this.field.invokeWithArguments(instance);
				return rawToHolder.apply(fieldValue);
			});
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
			throw new FieldNotFoundException(
					"Can't get " + instance + "." + member + ": neither field '" + clazz.getName() + "." + member
							+ "' nor special method 'Holder " + clazz.getName() + ".__getattr__(String)' found");
		}
	}

	private static class MethodProxy implements IFieldAccess {
		private final OverloadedMethod method;

		public MethodProxy(OverloadedMethod methods) {
			this.method = methods;
		}

		@Override
		public Holder get(Object instance) {
			return Holder.valueOf(new MemberMethodProxy(method.bindTo(instance)));
		}
	}

	private static class SpecialAccessProxy implements IFieldAccess {
		private final MethodHandle getattr;
		private final String name;
		private final Supplier<RuntimeException> error;

		public SpecialAccessProxy(MethodHandle getter, String memberName) {
			if (!getter.type().returnType().equals(Holder.class)) {
				error = () -> new IllegalArgumentException("__getattr__ must return Holder");
			} else {
				error = null;
			}
			this.getattr = getter;
			this.name = memberName;
		}

		@Override
		public Holder get(Object instance) throws Throwable {
			if (error != null) {
				throw error.get();
			}
			return Holder.class.cast(getattr.invokeWithArguments(instance, name));
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
		Optional<MethodHandle> f = FieldHelper.find(clazz, member);
		if (f.isPresent()) {
			return new FieldProxy<>(f.get());
		}
		Optional<OverloadedMethod> m = MethodHelper.find(clazz, member);
		if (m.isPresent()) {
			return new MethodProxy(m.get());
		}
		Optional<OverloadedMethod> getattr = MethodHelper.find(clazz, "__getattr__");
		Optional<MethodHandle> specialgetter = getattr.flatMap(o -> o.disambiguate(clazz, String.class));
		if (specialgetter.isPresent()) {
			return new SpecialAccessProxy(specialgetter.get(), member);
		}
		return new FieldNotFound(clazz, member);
	}

	private static Holder accessField(IFieldAccess field, Holder instance) throws Throwable {
		return instance.ifValid(ExceptionLessFunctions.uncheckedFunction(inst -> {
			return field.get(inst.boxed());
		}));
	}

	public static IValueHolder makeMemberAccess(IValueHolder holder, String memberName) {
		return new MemberAccess(holder, memberName);
	}

	private IValueHolder origin;
	private String name;

	private MemberAccess(IValueHolder object, String memberName) {
		this.origin = object;
		this.name = memberName;
	}

	@Override
	public Holder snapshot() throws Throwable {
		Holder holder = this.origin.snapshot();
		return accessField(resolveField(holder.getType(), this.name), holder);
	}

	@Override
	public String toString() {
		return origin.toString() + "." + name;
	}

}
