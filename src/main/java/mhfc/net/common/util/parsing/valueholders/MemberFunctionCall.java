package mhfc.net.common.util.parsing.valueholders;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

import org.apache.commons.lang3.reflect.MethodUtils;

import com.google.common.collect.ComputationException;

import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.IValueHolder;

public class MemberFunctionCall implements ICallableValueHolder {
	private static Method resolveMethod(Class<?> clazz, String name, Class<?>... args) {
		if (clazz.isArray() && name.equals("clone")) {
			// Ofc there's an exception....
			if (args.length != 0)
				throw new ComputationException(new IllegalArgumentException(
						clazz.getName() + " has no method that takes the paramters " + args));
			return null; // Marker...
		}
		Method m = MethodUtils.getMatchingAccessibleMethod(clazz, name, args);
		if (m == null) {
			throw new ComputationException(
					new IllegalArgumentException(clazz.getName() + " has no method that takes the paramters " + args));
		}
		return m;
	}

	private static Holder invokeMethod(Method m, Object instance, Object... arguments) {
		if (m == null) {
			if (instance == null)
				throw new ComputationException(new NullPointerException("Can't copy a null array"));
			Class<?> componentType = instance.getClass().getComponentType();
			int length = Array.getLength(instance);
			Object newArray = Array.newInstance(componentType, length);
			System.arraycopy(instance, 0, newArray, 0, length);
			return Holder.valueOf(newArray);
		}
		if (instance == null)
			throw new ComputationException(new NullPointerException("Can't invoke member method on null instance"));
		try {
			Class<?> typeOfRet = classOfMethod(null, m); // Not an array, safe
			Object ret = m.invoke(instance, arguments);
			if (void.class.isAssignableFrom(typeOfRet)) {
				return Holder.empty();
			}
			if (boolean.class.isAssignableFrom(typeOfRet)) {
				Boolean wrapper = (Boolean) ret;
				return Holder.valueOf(wrapper.booleanValue());
			}
			if (char.class.isAssignableFrom(typeOfRet)) {
				Character wrapper = (Character) ret;
				return Holder.valueOf(wrapper.charValue());
			}
			if (byte.class.isAssignableFrom(typeOfRet)) {
				Byte wrapper = (Byte) ret;
				return Holder.valueOf(wrapper.byteValue());
			}
			if (short.class.isAssignableFrom(typeOfRet)) {
				Short wrapper = (Short) ret;
				return Holder.valueOf(wrapper.shortValue());
			}
			if (int.class.isAssignableFrom(typeOfRet)) {
				Integer wrapper = (Integer) ret;
				return Holder.valueOf(wrapper.intValue());
			}
			if (long.class.isAssignableFrom(typeOfRet)) {
				Long wrapper = (Long) ret;
				return Holder.valueOf(wrapper.longValue());
			}
			if (float.class.isAssignableFrom(typeOfRet)) {
				Float wrapper = (Float) ret;
				return Holder.valueOf(wrapper.floatValue());
			}
			if (double.class.isAssignableFrom(typeOfRet)) {
				Double wrapper = (Double) ret;
				return Holder.valueOf(wrapper.doubleValue());
			}
			return Holder.valueOfUnsafe(ret, typeOfRet);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new ComputationException(e);
		}
	}

	private static Class<?> classOfMethod(Class<?> clazz, Method m) {
		return m == null ? clazz // array.clone()
				: m.getReturnType();
	}

	@SuppressWarnings("unchecked")
	private static <T> T[] mapAll(Function<? super IValueHolder, T> func, IValueHolder... holders) {
		return (T[]) Arrays.stream(holders).sequential().map(func).toArray();

	}

	private static Class<?>[] getContainedClassAll(IValueHolder... holders) {
		return mapAll(IValueHolder::getContainedClass, holders);
	}

	private static Object[] wrapped(Holder... holders) {
		return mapAll(h -> h.getAs(Object.class), holders);
	}

	private static Holder[] snapshotAll(IValueHolder... holders) {
		return mapAll(IValueHolder::snapshot, holders);
	}

	private static IValueHolder[] snapshotClassAll(IValueHolder... holders) {
		return mapAll(IValueHolder::snapshotClass, holders);
	}

	private static boolean isClassSnapshotAll(IValueHolder... holders) {
		return Arrays.stream(holders).unordered().allMatch(IValueHolder::isClassSnapshot);
	}

	public static class BoundMemberFunctionCall implements ICallableValueHolder {
		private final IValueHolder object;
		private final IValueHolder[] args;
		private final Class<?> returnClass;
		private final Method method;

		private BoundMemberFunctionCall(IValueHolder object, String name, IValueHolder... arguments) {
			this.object = object.snapshotClass();
			this.args = snapshotClassAll(arguments);
			this.method = resolveMethod(this.object.getContainedClass(), name, getContainedClassAll(arguments));
			this.returnClass = classOfMethod(this.object.getContainedClass(), method);
		}

		@Override
		public BoundMemberFunctionCall snapshotClass() {
			return this;
		}

		@Override
		public boolean isClassSnapshot() {
			return true;
		}

		@Override
		public Holder snapshot() {
			Holder instance = this.object.snapshot();
			Holder[] arguments = snapshotAll(args);
			return invokeMethod(method, instance.getAs(Object.class), wrapped(arguments));
		}

		@Override
		public Class<?> getContainedClass() {
			return this.returnClass;
		}

		@Override
		public Holder call() throws Exception {
			return snapshot();
		}
	}

	public static ICallableValueHolder makeFunctionCall(IValueHolder object, String methodName,
			IValueHolder... arguments) {
		if (object.isClassSnapshot() && isClassSnapshotAll(arguments)) {
			return new BoundMemberFunctionCall(object, methodName, arguments);
		}
		return new MemberFunctionCall(object, methodName, arguments);
	}

	private IValueHolder object;
	private IValueHolder[] arguments;
	private String name;

	private MemberFunctionCall(IValueHolder object, String methodName, IValueHolder... arguments) {
		this.object = Objects.requireNonNull(object, "Object can't be null");
		this.name = Objects.requireNonNull(methodName, "The method name can't be null");
		mhfc.net.common.util.Objects.requireNonNullDeep(arguments);
		this.arguments = Arrays.copyOf(arguments, arguments.length);
	}

	@Override
	public Holder snapshot() {
		Holder base = this.object.snapshot();
		Class<?> clazz = base.getContainedClass();
		Holder[] arguments = snapshotAll(this.arguments);
		Class<?>[] argClasses = getContainedClassAll(arguments);
		Method m = resolveMethod(clazz, name, argClasses);
		assert (!clazz.isPrimitive());
		return invokeMethod(m, base.getAs(Object.class), wrapped(arguments));
	}

	@Override
	public BoundMemberFunctionCall snapshotClass() {
		return new BoundMemberFunctionCall(this.object, name, this.arguments);
	}

	@Override
	public Class<?> getContainedClass() {
		Class<?> invokedOn = this.object.getContainedClass();
		Class<?>[] argClasses = getContainedClassAll(arguments);
		Method m = resolveMethod(invokedOn, name, argClasses);
		return classOfMethod(invokedOn, m);
	}

	@Override
	public Holder call() throws Exception {
		return snapshot();
	}

}
