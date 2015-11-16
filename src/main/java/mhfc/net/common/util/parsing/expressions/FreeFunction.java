package mhfc.net.common.util.parsing.expressions;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

import mhfc.net.common.util.parsing.Context;
import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.IExpression;
import mhfc.net.common.util.parsing.IValueHolder;

public class FreeFunction implements IExpression {
	private final IExpression[] args;
	private final String name;

	public FreeFunction(String name, IExpression... expressions) {
		this.name = Objects.requireNonNull(name);
		this.args = mhfc.net.common.util.Objects.requireNonNullDeep(Arrays.copyOf(expressions, expressions.length));
	}

	@Override
	public IValueHolder resolveAgainst(Context context) {
		Function<Arguments, IValueHolder> func = context.getFilter(name);
		if (func == null) {
			throw new UnresolvedContextVariableException(name, context);
		}
		return new IValueHolder() {

			@Override
			public IValueHolder snapshotClass() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Holder snapshot() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Class<?> getContainedClass() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

}
