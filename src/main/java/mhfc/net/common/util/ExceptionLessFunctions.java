package mhfc.net.common.util;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * The functional interfaces from Java are disallowing to throw exceptions. We have to wrap them ourselves...
 *
 * @author WorldSEnder
 *
 * @see https://stackoverflow.com/questions/27644361/
 *
 */
public class ExceptionLessFunctions {
	public interface ThrowingRunnable<E extends Throwable> {
		void accept() throws E;
	}

	public interface ThrowingConsumer<T, E extends Throwable> {
		void accept(T t) throws E;
	}

	public interface ThrowingSupplier<T, E extends Throwable> {
		T get() throws E;
	}

	public interface ThrowingFunction<T, R, E extends Throwable> {
		R apply(T t) throws E;
	}

	public static <E extends Throwable> Runnable uncheckedRunnable(ThrowingRunnable<E> t) throws E {
		return () -> {
			try {
				t.accept();
			} catch (Throwable exception) {
				ExceptionLessFunctions.throwUnchecked(exception);
			}
		};
	}

	public static <T, E extends Throwable> Consumer<T> uncheckedConsumer(ThrowingConsumer<T, E> consumer) throws E {
		return t -> {
			try {
				consumer.accept(t);
			} catch (Throwable exception) {
				ExceptionLessFunctions.throwUnchecked(exception);
			}
		};
	}

	public static <T, E extends Throwable> Supplier<T> uncheckedSupplier(ThrowingSupplier<T, E> supplier) throws E {
		return () -> {
			try {
				return supplier.get();
			} catch (Throwable exception) {
				return ExceptionLessFunctions.throwUnchecked(exception);
			}
		};
	}

	public static <T, R, E extends Throwable> Function<T, R> uncheckedFunction(ThrowingFunction<T, R, E> function)
			throws E {
		return t -> {
			try {
				return function.apply(t);
			} catch (Throwable exception) {
				return ExceptionLessFunctions.throwUnchecked(exception);
			}
		};
	}

	/**
	 * Utility function. Use with great care and only when you know what you're doing. This will throw it's argument but
	 * without enforcing a compile-time check. So it can be used to throw checked exceptions without declaring them.<br>
	 * Most of the time <b>this is not what you want to do<b>.
	 *
	 * @param exception
	 * @return nothing, this function will always throw. Enables you to write <code>return throwUnchecked(...)</code>
	 * @throws E
	 *             instead of throwing E, this will always throw the given Throwable. But E will be deduced to
	 *             RuntimeException if you don't declare it yourself
	 */
	@SuppressWarnings("unchecked")
	public static <T, E extends Throwable> T throwUnchecked(Throwable exception) throws E {
		throw (E) exception;
	}
}
