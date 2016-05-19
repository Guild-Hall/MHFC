package mhfc.net.common.util.reflection;

import java.lang.invoke.MethodHandle;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.exceptions.MethodNotFoundException;
import mhfc.net.common.util.parsing.valueholders.Arguments;

/**
 * Represents a set of methods that are found by {@link MethodHelper}.
 *
 * @author WorldSEnder
 *
 */
public class OverloadedMethod {

	private List<MethodInfo> allMethods;

	public OverloadedMethod(List<MethodHandle> methods) {
		this.allMethods = methods.stream().map(MethodInfo::new).collect(Collectors.toList());
	}

	/**
	 * Restrict this set of methods to only methods that will get called if the arguments are of the classes
	 * expressionClasses. Use void.class to specify a yet unused argument.
	 *
	 * @param expressionClasses
	 * @return empty: no method matches, filled: one most specific method or ambiguous call
	 * @throws IllegalArgumentException
	 *             if multiple ambiguous methods remain.
	 */
	public Optional<MethodHandle> disambiguate(Class<?>... argClasses) {
		Optional<MethodHandle> handle = new Disambiguator(argClasses).considerAll(allMethods).getCurrent();
		return handle;
	}

	/**
	 * Binds this overloadedMethod to an argument (normally the instance this is called on). All methods where instance
	 * can not be converted to the respective first argument type are disposed of.
	 *
	 * @param instance
	 */
	public OverloadedMethod bindTo(Object instance) {
		return new OverloadedMethod(allMethods.stream() //
				.filter(i -> i.method.type().parameterType(0).isInstance(instance)) //
				.map(i -> i.method.bindTo(instance)) //
				.collect(Collectors.toList()));
	}

	public Holder call(Arguments arguments) throws Throwable {
		int argCount = arguments.getArgCount();
		Holder[] snapshots = new Holder[argCount];
		Class<?>[] types = new Class<?>[argCount];
		for (int i = 0; i < argCount; i++) {
			snapshots[i] = arguments.getArgument(i).snapshot();
			types[i] = snapshots[i].getType();
		}

		Optional<MethodHandle> method = this.disambiguate(types);
		if (!method.isPresent()) {
			throw new MethodNotFoundException(
					"No overload for method " + this + " and arg-classes " + Arrays.toString(types));
		}
		MethodHandle m = method.get();
		Object[] args = new Object[argCount];
		IntStream.range(0, argCount).forEach(i -> args[i] = snapshots[i].boxed());
		Object ret = m.invokeWithArguments(args);
		Class<?> clazz = m.type().returnType();
		return Holder.makeUnboxer(clazz).apply(ret);
	}

	@Override
	public String toString() {
		return "OverloadedMethod" + this.allMethods;
	}
}
