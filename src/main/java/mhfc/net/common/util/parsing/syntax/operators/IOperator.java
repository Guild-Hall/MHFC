package mhfc.net.common.util.parsing.syntax.operators;

@FunctionalInterface
public interface IOperator<V, R> {
	R with(V value);
}
