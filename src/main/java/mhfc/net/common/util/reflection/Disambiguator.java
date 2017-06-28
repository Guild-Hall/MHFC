package mhfc.net.common.util.reflection;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import mhfc.net.common.util.Comparation;
import mhfc.net.common.util.Comparation.ComparationResult;
import mhfc.net.common.util.parsing.exceptions.AmbiguousCallException;

public class Disambiguator {
	private static enum MethodApplicability implements Comparable<MethodApplicability> {
		/**
		 * §15.12.2.2 without permitting boxing or unboxing conversion, or the use of variable arity method invocation
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
	protected static final MethodHandle throwingHandle;

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

	/**
	 * §15.12.2.5 Determine most specific method<br>
	 * Determines if candidate is more specific that precedent.<br>
	 *
	 * @param candidate
	 * @param precedent
	 * @return
	 */
	protected static boolean isMoreSpecific(MethodInfo candidate, MethodInfo precedent) {
		if (!candidate.isVarArgs) {
			if (precedent.argTypes.length != candidate.argTypes.length) {
				return false;
			}
			return IntStream.range(0, candidate.argTypes.length).allMatch(j -> {
				Class<?> clazzT = candidate.argTypes[j];
				Class<?> clazzU = precedent.argTypes[j];
				return ClassHelper.isSubtype(clazzU, clazzT);
			});
		}
		if (!precedent.isVarArgs) {
			return false;
		}
		int argLength = Math.max(candidate.argTypes.length, precedent.argTypes.length);
		return IntStream.range(0, argLength).allMatch(j -> {
			Class<?> clazzT = candidate.getVarArgType(j);
			Class<?> clazzU = precedent.getVarArgType(j);
			return ClassHelper.isSubtype(clazzU, clazzT);
		});
	}

	protected static int compareMostSpecific(MethodInfo left, MethodInfo right) {
		return isMoreSpecific(left, right)
				? (isMoreSpecific(right, left) ? 0 : 1)
				: (isMoreSpecific(right, left) ? -1 : 0);
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
		Comparation<MethodInfo> candidateComp = Comparation
				.comparing(candidate, (Comparator<MethodInfo>) Disambiguator::compareMostSpecific);
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

	protected MethodApplicability determineApplicability(MethodInfo info) {
		if (expressionClasses.length == info.argTypes.length) {
			// Possibly applicable by subtyping
			MethodApplicability potentialApplicability = MethodApplicability.SUBTYPING;
			for (int i = 0; i < expressionClasses.length; i++) {
				Class<?> eClazz = expressionClasses[i], aClazz = info.argTypes[i];
				if (ClassHelper.isSubtype(eClazz, aClazz)) {
					continue;
				}
				if (ClassHelper.isMethodInvocationConvertible(eClazz, aClazz)) {
					potentialApplicability = MethodApplicability.METHOD_INVOKATION_CONVERSION;
					continue;
				}
				if (info.isVarArgs && i == expressionClasses.length - 1) {
					if (ClassHelper.isMethodInvocationConvertible(eClazz, info.varArgCompType)) {
						return MethodApplicability.VARARG;
					}
				}
				return MethodApplicability.NOT_APPLICABLE;
			}
			return potentialApplicability;
		}
		if (!info.isVarArgs || expressionClasses.length < info.argTypes.length - 1) {
			// Not enough parameters for either
			return MethodApplicability.NOT_APPLICABLE;
		}
		// Last parameters must be variable arity
		for (int i = 0; i < expressionClasses.length; i++) {
			Class<?> exClass = expressionClasses[i];
			Class<?> paClass = i >= info.argTypes.length - 1 ? info.varArgCompType : info.argTypes[i];
			if (!ClassHelper.isMethodInvocationConvertible(exClass, paClass)) {
				return MethodApplicability.NOT_APPLICABLE;
			}
		}
		return MethodApplicability.VARARG;
	}

	protected MethodHandle getAmbiguityHandle() {
		MethodHandle ret = throwingHandle.bindTo(expressionClasses).bindTo(new ArrayList<>(allCurrentBest))
				.asVarargsCollector(Object[].class);
		assert ret.isVarargsCollector();
		return ret;
	}

	public Optional<MethodHandle> getCurrent() {
		if (allCurrentBest.isEmpty() || bestStrategy == MethodApplicability.NOT_APPLICABLE) {
			return Optional.empty();
		}
		if (allCurrentBest.size() == 1) {
			return Optional.of(allCurrentBest.get(0).method);
		}
		return Optional.of(getAmbiguityHandle());
	}
}
