package mhfc.net.common.util.parsing.valueholders;

import static mhfc.net.common.util.Utilities.mapAll;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

import org.apache.commons.lang3.reflect.MethodUtils;

import com.google.common.base.Joiner;

import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.IValueHolder;

public class MemberFunctionCall implements ICallableValueHolder {
	private static final Joiner argList = Joiner.on(", ");

	private static String signatureFor(Class<?> instanceC, String methodName, Class<?>... argsC) {
		return "public " + instanceC.getName() + "." + methodName + "("
				+ argList.join(mapAll(Class::getName, argsC, String[]::new)) + ")";
	}

	private static Holder invokeMethod(Method m, Holder instance, Holder[] args) {
		if (!instance.isValid()) {
			return Holder.failedComputation(new IllegalArgumentException(
					"The instance to invoke the method on could not be computed", instance.getFailCause()));
		}
		for (Holder h : args) {
			if (h.isValid())
				continue;
			return Holder.failedComputation(new IllegalArgumentException(
					"An argument to the function could not be computed", h.getFailCause()));
		}
		Object inst = instance.boxed();
		Class<?> retClazz = m.getReturnType();
		Object ret;
		try {
			m.setAccessible(true);
			ret = m.invoke(inst, mapAll(Holder::boxed, args, Object[]::new));
		} catch (IllegalAccessException | IllegalArgumentException | NullPointerException | SecurityException e) {
			return Holder.failedComputation(e);
		} catch (InvocationTargetException e) {
			return Holder.failedComputation(e.getCause());
		}
		if (void.class.isAssignableFrom(retClazz)) {
			return Holder.empty();
		}
		if (boolean.class.isAssignableFrom(retClazz)) {
			return Holder.valueOf(((Boolean) ret).booleanValue());
		}
		if (char.class.isAssignableFrom(retClazz)) {
			return Holder.valueOf(((Character) ret).charValue());
		}
		if (byte.class.isAssignableFrom(retClazz)) {
			return Holder.valueOf(((Byte) ret).byteValue());
		}
		if (short.class.isAssignableFrom(retClazz)) {
			return Holder.valueOf(((Short) ret).shortValue());
		}
		if (int.class.isAssignableFrom(retClazz)) {
			return Holder.valueOf(((Integer) ret).intValue());
		}
		if (long.class.isAssignableFrom(retClazz)) {
			return Holder.valueOf(((Long) ret).longValue());
		}
		if (float.class.isAssignableFrom(retClazz)) {
			return Holder.valueOf(((Float) ret).floatValue());
		}
		if (double.class.isAssignableFrom(retClazz)) {
			return Holder.valueOf(((Double) ret).doubleValue());
		}
		// FIXME: does this lead to wrong classes on non-null values?
		return Holder.valueOfUnsafe(ret, retClazz);
	}

	private static Method findMethod(Class<?> instanceClazz, String name, Class<?>... argumentClasses) {
		try {
			if (instanceClazz.isArray() && name.equals("clone")) {
				Method clone = instanceClazz.getSuperclass().getDeclaredMethod("clone", argumentClasses);
				clone.setAccessible(true);
				return clone;
			}
			// FIXME: this finds static methods I think, should not
			return MethodUtils.getMatchingAccessibleMethod(instanceClazz, name, argumentClasses);
		} catch (NoSuchMethodException e) {
			return null;
		} catch (SecurityException e) {
			return null;
		}
	}

	public static class BoundMemberFunctionCall implements ICallableValueHolder {
		private final IValueHolder object;
		private final Class<?> instC;
		private final IValueHolder[] args;
		private final Class<?>[] argsClasses;
		private final String methodName;
		private final Method method;
		private final Throwable notResolvedExc;
		private final Class<?> returnClass;

		private BoundMemberFunctionCall(IValueHolder object, String name, IValueHolder... arguments) {
			this.object = object.snapshotClass();
			this.instC = this.object.getType();
			this.args = mapAll(IValueHolder::snapshot, arguments, IValueHolder[]::new);
			this.argsClasses = mapAll(IValueHolder::getType, this.args, Class<?>[]::new);
			this.methodName = name;
			this.method = findMethod(this.instC, name, this.argsClasses);
			this.notResolvedExc = this.method == null
					? new IllegalArgumentException(String.format("Couldn't find a method matching %s",
							signatureFor(this.instC, this.methodName, this.argsClasses)))
					: null;
			this.returnClass = this.method == null ? Holder.EMPTY_CLASS : this.method.getReturnType();
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
			// Snapshot first, to keep contract
			Holder instance = this.object.snapshot();
			Holder[] arguments = mapAll(e -> e.snapshot(), this.args, Holder[]::new);
			if (this.method == null) {
				return Holder.failedComputation(this.notResolvedExc);
			}
			return invokeMethod(this.method, instance, arguments);
		}

		@Override
		public Class<?> getType() {
			return this.returnClass;
		}

		@Override
		public Holder call() throws Exception {
			return snapshot();
		}
	}

	public static ICallableValueHolder makeFunctionCall(IValueHolder object, String methodName,
			IValueHolder... arguments) {
		if (object.isClassSnapshot() && Stream.of(arguments).allMatch(IValueHolder::isClassSnapshot)) {
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
		Holder instance = this.object.snapshot();
		Holder[] arguments = mapAll(IValueHolder::snapshot, this.arguments, Holder[]::new);
		Class<?> instC = instance.getType();
		Class<?>[] argsC = mapAll(Holder::getType, arguments, Class<?>[]::new);
		Method m = findMethod(instC, name, argsC);
		if (m == null) {
			return Holder.failedComputation(new IllegalArgumentException(
					String.format("Couldn't find a method matching %s", signatureFor(instC, name, argsC))));
		}
		return invokeMethod(m, instance, arguments);
	}

	@Override
	public BoundMemberFunctionCall snapshotClass() {
		return new BoundMemberFunctionCall(this.object, name, this.arguments);
	}

	@Override
	public Class<?> getType() {
		Class<?> instC = this.object.getType();
		Class<?>[] argsC = mapAll(IValueHolder::getType, arguments, Class<?>[]::new);
		Method m = findMethod(instC, name, argsC);
		if (m == null)
			return Holder.EMPTY_CLASS;
		return m.getReturnType();
	}

	@Override
	public Holder call() {
		return snapshot();
	}

}
