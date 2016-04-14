package mhfc.net.common.util.parsing.syntax.operators;

@FunctionalInterface
public interface IBinaryOperator<V, W, R> {
	R with(V valueV, W valueW);
}
