package mhfc.net.common.util;

import java.util.Objects;
import java.util.function.ToIntFunction;

/**
 * Simplifies dealing with {@link java.util.Comparator} by giving useful names to the comparison methods. Possible use
 * case: <code>Comparation.compare("asd").isGreaterThan("ase")</code>
 *
 * @author WorldSEnder
 *
 * @param <T>
 */
public final class Comparation<T> {
	@FunctionalInterface
	public static interface Comparator<U, Q> {
		int compare(U left, Q right);
	}

	public static class ComparationResult {
		private final int wrapped;

		public ComparationResult(int actual) {
			this.wrapped = actual;
		}

		public boolean favorsLeft() {
			return wrapped > 0;
		}

		public boolean favorsLeftOrEq() {
			return wrapped >= 0;
		}

		public boolean favorsRight() {
			return wrapped < 0;
		}

		public boolean favorsRightOrEq() {
			return wrapped <= 0;
		}

		public boolean meansEquals() {
			return wrapped == 0;
		}

		public boolean meansNotEquals() {
			return wrapped != 0;
		}
	}

	private final ToIntFunction<T> uncompleteComparator;

	private <U> Comparation(U left, Comparator<U, T> comparator) {
		Objects.requireNonNull(comparator);
		this.uncompleteComparator = r -> comparator.compare(left, r);
	}

	public boolean isGreaterThan(T rightSide) {
		return applyTo(rightSide) > 0;
	}

	public boolean isGreaterEqualsThan(T rightSide) {
		return applyTo(rightSide) >= 0;
	}

	public boolean isLessThan(T rightSide) {
		return applyTo(rightSide) < 0;
	}

	public boolean isLessEqualsThan(T rightSide) {
		return applyTo(rightSide) <= 0;
	}

	public boolean isEqualsThan(T rightSide) {
		return applyTo(rightSide) == 0;
	}

	public boolean isNotEqualsThan(T rightSide) {
		return applyTo(rightSide) != 0;
	}

	private int applyTo(T rightSide) {
		return uncompleteComparator.applyAsInt(rightSide);
	}

	public ComparationResult to(T rightSide) {
		return new ComparationResult(applyTo(rightSide));
	}

	public static <U, Q> Comparation<Q> comparing(U leftSide, Comparator<U, Q> comparator) {
		return new Comparation<>(leftSide, comparator);
	}

	public static <U> Comparation<U> comparing(U leftSide, java.util.Comparator<U> comparator) {
		return new Comparation<>(leftSide, comparator::compare);
	}

	public static <O, U extends Comparable<? super O>> Comparation<O> comparing(U leftSide) {
		Objects.requireNonNull(leftSide);
		return comparing(leftSide, (Comparator<U, O>) (l, r) -> l.compareTo(r));
	}

}
