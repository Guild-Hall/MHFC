package mhfc.net.common.util.parsing.expressions;

import java.util.Objects;

import mhfc.net.common.util.parsing.Context;
import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.IExpression;
import mhfc.net.common.util.parsing.IValueHolder;
import mhfc.net.common.util.parsing.expressions.UnresolvedContextVariableException.UnresolvedVariableException;

public class Variable implements IExpression {
	private final String name;

	public Variable(String name) {
		this.name = Objects.requireNonNull(name);
	}

	@Override
	public IValueHolder resolveAgainst(Context context) {
		return new IValueHolder() {
			private IValueHolder resolveNow() {
				IValueHolder var = context.getVar(name);
				if (var == null) {
					return Holder.failedComputation(new UnresolvedVariableException(name, context));
				}
				return var;
			}

			@Override
			public Holder snapshot() {
				return resolveNow().snapshot();
			}

			@Override
			public IValueHolder snapshotClass() {
				return resolveNow().snapshotClass();
			}

			@Override
			public Class<?> getType() {
				return resolveNow().getType();
			}
		};
	}
}
