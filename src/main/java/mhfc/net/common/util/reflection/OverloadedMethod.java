package mhfc.net.common.util.reflection;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import mhfc.net.common.util.Comparation;
import mhfc.net.common.util.Comparation.ComparationResult;
import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.exceptions.AmbiguousCallException;
import mhfc.net.common.util.parsing.exceptions.MethodNotFoundException;
import mhfc.net.common.util.parsing.valueholders.Arguments;

/**
 * Represents a set of methods that are found by {@link MethodHelper}.
 *
 * @author WorldSEnder
 *
 */
public class OverloadedMethod {
	/**
	 * Helper struct to determine applicability
	 */
	private static class MethodInfo implements Comparable<MethodInfo> {
		public final MethodHandle method;
		public final Class<?>[] argTypes;
		public final boolean isVarArgs;

		public MethodInfo(MethodHandle method) {
			this.method = Objects.requireNonNull(method);
			this.argTypes = method.type().parameterArray();
			this.isVarArgs = method.isVarargsCollector();
		}

		@Override
		public int compareTo(MethodInfo o) {
			// TODO: §15.12.2.5 Determine most specific method
			return 0;
		}

		@Override
		public String toString() {
			return method.toString();
		}
	}

	private static class Disambiguator {
		private static enum MethodApplicability implements Comparable<MethodApplicability> {
			/**
			 * §15.12.2.2 without permitting boxing or unboxing conversion, or the use of variable arity method
			 * invocation
			 */
			SUBTYPING,
			/**
			 * §15.12.2.3 allowing boxing and unboxing, but still precludes the use of variable arity method invocation
			 */
			METHOD_INVOKATION_CONVERSION,
			/**
			 * §15.12.2.4 combined with variable arity methods, boxing, and unboxing
			 */
			VARARG,
			/**
			 * None of the above
			 */
			NOT_APPLICABLE;
		}

		private static final MethodHandles.Lookup lookup = MethodHandles.lookup();
		private static final MethodHandle throwingHandle;

		static {
			try {
				throwingHandle = lookup.findStatic(
						Disambiguator.class,
						"throwAmbiguousCall",
						MethodType.methodType(void.class, Class[].class, List.class, Object[].class));
			} catch (NoSuchMethodException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}

		@SuppressWarnings("unused")
		private static void throwAmbiguousCall(Class<?>[] expressions, List<MethodInfo> methods, Object... objs) {
			throw new AmbiguousCallException(
					"Arguments: " + Arrays.toString(expressions) + " Possiblities: " + methods.toString());
		}

		private MethodApplicability bestStrategy;
		// A list of all currently applicable methods, ambiguous if more than one.
		private List<MethodInfo> allCurrentBest;
		// The classes of expressions used as to call
		private final Class<?>[] expressionClasses;

		public Disambiguator(Class<?>... args) {
			this.expressionClasses = mhfc.net.common.util.Objects.requireNonNullDeep(args);
			this.bestStrategy = MethodApplicability.NOT_APPLICABLE;
			this.allCurrentBest = new ArrayList<>();
		}

		public Disambiguator considerAll(Iterable<MethodInfo> methods) {
			methods.forEach(this::consider);
			return this;
		}

		public Disambiguator consider(MethodInfo candidate) {
			// §15.12.2
			MethodApplicability applicability = determineApplicability(candidate);
			ComparationResult apResult = Comparation.comparing(bestStrategy).to(applicability);
			if (apResult.favorsRight()) {
				return this;
			}
			if (apResult.favorsLeft()) {
				this.bestStrategy = applicability;
				this.allCurrentBest.clear();
				this.allCurrentBest.add(candidate);
				return this;
			}
			if (bestStrategy == MethodApplicability.NOT_APPLICABLE) {
				return this;
			}
			Comparation<MethodInfo> candidateComp = Comparation.comparing(candidate);
			boolean isMostSpecific = true;
			boolean isLeastSpecific = true;
			for (MethodInfo info : allCurrentBest) {
				ComparationResult specifityResult = candidateComp.to(info);
				if (specifityResult.favorsRight()) {
					isMostSpecific = false;
				} else if (specifityResult.meansEquals()) {
					isMostSpecific = false;
					isLeastSpecific = false;
				} else {
					isLeastSpecific = false;
				}
			}
			if (isMostSpecific) {
				this.allCurrentBest.clear();
				this.allCurrentBest.add(candidate);
				return this;
			}
			if (isLeastSpecific) {
				return this;
			}
			// Ambiguous
			allCurrentBest.add(candidate);
			return this;
		}

		private MethodApplicability determineApplicability(MethodInfo info) {
			// TODO: §15.12.2.2-4 Determine applicability
			return MethodApplicability.NOT_APPLICABLE;
		}

		public Optional<MethodHandle> getCurrent() {
			if (allCurrentBest.isEmpty() || bestStrategy == MethodApplicability.NOT_APPLICABLE) {
				return Optional.empty();
			}
			if (allCurrentBest.size() == 1) {
				return Optional.of(allCurrentBest.get(0).method);
			}
			MethodHandle handle = getAmbiguityHandle();
			return Optional.of(handle);
		}

		public MethodHandle getAmbiguityHandle() {
			MethodHandle ret = throwingHandle.bindTo(expressionClasses).bindTo(new ArrayList<>(allCurrentBest))
					.asVarargsCollector(Object[].class);
			assert ret.isVarargsCollector();
			return ret;
		}
	}

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
	 * Binds this overloadedMethod to an argument (normally the instance this is called on).
	 *
	 * @param instance
	 */
	public OverloadedMethod bindTo(Object instance) {
		return new OverloadedMethod(allMethods.stream() //
				.filter(i -> i.method.type().parameterType(0).isInstance(instance)) //
				.map(i -> i.method.bindTo(instance)) //
				.collect(Collectors.toList()));
	}

	public Holder call(Arguments arguments) {
		int argCount = arguments.getArgCount();
		Holder[] snapshots = new Holder[argCount];
		Class<?>[] types = new Class<?>[argCount];
		IntStream.range(0, argCount).forEach(i -> {
			snapshots[i] = arguments.getArgument(i).snapshot();
			types[i] = snapshots[i].getType();
		});

		Optional<MethodHandle> method = this.disambiguate(types);
		if (!method.isPresent()) {
			throw new MethodNotFoundException(
					"No overload for method " + this + " and arg-classes " + Arrays.toString(types));
		}
		MethodHandle m = method.get();
		Object[] args = new Object[argCount];
		IntStream.range(0, argCount).forEach(i -> args[i] = snapshots[i].boxed());
		Object ret;
		try {
			ret = m.invokeWithArguments(args);
			Class<?> clazz = m.type().returnType();
			return Holder.makeUnboxer(clazz).apply(ret);
		} catch (Throwable e) {
			return Holder.failedComputation(e);
		}
	}

	@Override
	public String toString() {
		return "OverloadedMethod" + this.allMethods;
	}
}
