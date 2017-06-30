package mhfc.net.common.util.parsing.syntax.operators;

import java.util.Objects;
import java.util.function.Function;

public class BinaryOPWrapper<V, W, R, T extends IBinaryOperator<V, W, R>> implements IOperator<V, IOperator<W, R>> {
	private T original;

	public BinaryOPWrapper(T operator) {
		this.original = operator;
	}

	public static class BinaryWrapperResult<W, R> implements IOperator<W, R> {
		private Function<W, R> apply;

		public <V, T extends IBinaryOperator<V, W, R>> BinaryWrapperResult(T op, V value) {
			apply = w -> op.with(value, w);
		}

		@Override
		public R with(W value) {
			return apply.apply(value);
		}

	}

	@Override
	public IOperator<W, R> with(V value) {
		return new BinaryWrapperResult<>(original, value);
	}

	public static <V, W, R, T extends IBinaryOperator<V, W, R>> BinaryOPWrapper<V, W, R, T> wrap(T operator) {
		return new BinaryOPWrapper<>(operator);
	}

	@Override
	public String toString() {
		return Objects.toString(original);
	}
}
