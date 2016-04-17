package mhfc.net.common.util.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.exceptions.MethodNotFoundException;
import mhfc.net.common.util.parsing.valueholders.Arguments;
import scala.actors.threadpool.Arrays;

/**
 * Represents a set of methods that are found by {@link MethodHelper}.
 *
 * @author WorldSEnder
 *
 */
public class OverloadedMethod {
	private static boolean isConvertible(Class<?> parameter, Class<?> arg) {
		// FIXME: just not true...
		return true;
	}

	private static boolean matches(Class<?>[] parameter, Class<?>[] args) {
		if (parameter.length != args.length) {
			return false;
		}
		for (int i = 0; i < parameter.length; i++) {
			Class<?> paramClass = parameter[i];
			Class<?> argClass = args[i];
			if (!isConvertible(paramClass, argClass)) {
				return false;
			}
		}
		return true;
	}

	private List<Method> allMethods;

	public OverloadedMethod(List<Method> methods) {
		this.allMethods = methods;
	}

	/**
	 * Restrict this set of methods to only methods that will get called if the arguments are of the classes argClasses.
	 * Use void.class to specify a yet unused argument.
	 *
	 * @param argClasses
	 * @return
	 * @throws IllegalArgumentException
	 *             if multiple ambiguous methods remain.
	 */
	public Optional<Method> disambiguate(Class<?>... argClasses) {
		// https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.12.2.1
		// TODO: fix, not findFirst, also doesn't find varargs methods correctly
		return allMethods.stream().filter(m -> matches(m.getParameterTypes(), argClasses)).findFirst();
	}

	public Holder call(Object instance, Arguments arguments) {
		int argCount = arguments.getArgCount();
		Holder[] snapshots = new Holder[argCount];
		Class<?>[] types = new Class<?>[argCount];
		IntStream.range(0, argCount).forEach(i -> snapshots[i] = arguments.getArgument(i).snapshot());
		IntStream.range(0, argCount).forEach(i -> types[i] = snapshots[i].getType());

		Optional<Method> method = this.disambiguate(types);
		if (!method.isPresent()) {
			throw new MethodNotFoundException(
					"No overload for method " + this + " and arg-classes " + Arrays.toString(types));
		}
		Method m = method.get();
		Object[] args = new Object[argCount];
		IntStream.range(0, argCount).forEach(i -> args[i] = snapshots[i].boxed());
		try {
			Object ret;
			if (m.isVarArgs()) {
				int normalArgsCount = m.getParameterCount() - 1;
				int varArgsCount = argCount - normalArgsCount;
				Object[] allArgs = new Object[normalArgsCount + 1];
				Object[] varArgs = new Object[varArgsCount];
				System.arraycopy(args, 0, allArgs, 0, normalArgsCount);
				System.arraycopy(args, normalArgsCount, varArgs, 0, varArgsCount);
				allArgs[normalArgsCount] = varArgs;
				ret = m.invoke(instance, allArgs);
			} else {
				ret = m.invoke(instance, args);
			}
			Class<?> clazz = m.getReturnType();
			return Holder.makeUnboxer(clazz).apply(ret);
		} catch (IllegalAccessException | InvocationTargetException iae) {
			return Holder.failedComputation(iae);
		}
	}

}
