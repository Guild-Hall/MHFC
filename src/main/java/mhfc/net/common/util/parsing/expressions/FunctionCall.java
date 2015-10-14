package mhfc.net.common.util.parsing.expressions;

import java.util.Objects;

import mhfc.net.common.util.parsing.Context;
import mhfc.net.common.util.parsing.IExpression;
import mhfc.net.common.util.parsing.IValueHolder;
import mhfc.net.common.util.parsing.valueholders.MemberFunctionCall;

public class FunctionCall implements IExpression {
	private static IValueHolder[] resolveAll(Context ctx, IExpression... exprs) {
		IValueHolder[] ar = new IValueHolder[exprs.length];
		for (int i = 0; i < exprs.length; i++) {
			ar[i] = exprs[i].resolveAgainst(ctx);
		}
		return ar;
	}

	private final IExpression instance;
	private final String name;
	private final IExpression[] arguments;

	public FunctionCall(IExpression instance, String name,
			IExpression... arguments) {
		this.instance = Objects.requireNonNull(instance);
		this.name = Objects.requireNonNull(name);
		this.arguments = new IExpression[arguments.length];
		for (int i = 0; i < arguments.length; i++) {
			this.arguments[i] = Objects.requireNonNull(arguments[i]);
		}
	}

	@Override
	public IValueHolder resolveAgainst(Context context) {
		return new MemberFunctionCall(instance.resolveAgainst(context), name,
				resolveAll(null, arguments));
	}

}
